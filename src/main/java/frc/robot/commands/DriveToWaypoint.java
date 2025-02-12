package frc.robot.commands;

import com.pathplanner.lib.commands.PathPlannerAuto;
import com.pathplanner.lib.config.RobotConfig;
import com.pathplanner.lib.controllers.PathFollowingController;
import com.pathplanner.lib.events.EventScheduler;
import com.pathplanner.lib.path.*;
import com.pathplanner.lib.trajectory.PathPlannerTrajectory;
import com.pathplanner.lib.trajectory.PathPlannerTrajectoryState;
import com.pathplanner.lib.util.DriveFeedforwards;
import com.pathplanner.lib.util.PPLibTelemetry;
import com.pathplanner.lib.util.PathPlannerLogging;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

/**
 * Base command for following a path
 */
public class DriveToWaypoint extends Command {
    private final Timer timer = new Timer();
    private static Supplier<Pose2d> poseSupplier;
    private static Supplier<ChassisSpeeds> speedsSupplier;
    private static BiConsumer<ChassisSpeeds, DriveFeedforwards> output;
    private static PathFollowingController controller;
    private static RobotConfig robotConfig;
    private static BooleanSupplier shouldFlipPath;
    private static Subsystem[] requirements;
    private final EventScheduler eventScheduler;

    private PathPlannerPath path;
    private final Pose2d endingPose;
    private final PathConstraints constraints;
    private final IdealStartingState idealStartingState;
    private final GoalEndState goalEndState;
    private PathPlannerTrajectory trajectory;

    /**
     * Construct a base path following command
     *
     * @param endingPose         The pose to go to
     * @param constraints        The constraints of the path
     * @param idealStartingState The ideal starting state of the path
     * @param goalEndState       The ending state of the path
     */
    public DriveToWaypoint(
            Pose2d endingPose,
            PathConstraints constraints,
            IdealStartingState idealStartingState,
            GoalEndState goalEndState) {
        this.endingPose = endingPose;
        this.constraints = constraints;
        this.idealStartingState = idealStartingState;
        this.goalEndState = goalEndState;
        this.eventScheduler = new EventScheduler();

        addRequirements(requirements);

    }

    @Override
    public void initialize() {
        Pose2d currentPose = poseSupplier.get();
        List<Pose2d> poses = Arrays.asList(currentPose, this.endingPose);
        this.path = new PathPlannerPath(PathPlannerPath.waypointsFromPoses(poses), this.constraints,
                this.idealStartingState,
                this.goalEndState);

        Optional<PathPlannerTrajectory> idealTrajectory = this.path.getIdealTrajectory(DriveToWaypoint.robotConfig);
        idealTrajectory.ifPresent(traj -> this.trajectory = traj);

        if (shouldFlipPath.getAsBoolean() && !path.preventFlipping) {
            path = path.flipPath();
        }
        ChassisSpeeds currentSpeeds = speedsSupplier.get();

        controller.reset(currentPose, currentSpeeds);

        double linearVel = Math.hypot(currentSpeeds.vxMetersPerSecond, currentSpeeds.vyMetersPerSecond);

        if (path.getIdealStartingState() != null) {
            // Check if we match the ideal starting state
            boolean idealVelocity = Math.abs(linearVel - path.getIdealStartingState().velocityMPS()) <= 0.25;
            boolean idealRotation = !robotConfig.isHolonomic
                    || Math.abs(
                    currentPose
                            .getRotation()
                            .minus(path.getIdealStartingState().rotation())
                            .getDegrees()) <= 30.0;
            if (idealVelocity && idealRotation) {
                // We can use the ideal trajectory
                trajectory = path.getIdealTrajectory(robotConfig).orElseThrow();
            } else {
                // We need to regenerate
                trajectory = path.generateTrajectory(currentSpeeds, currentPose.getRotation(), robotConfig);
            }
        } else {
            // No ideal starting state, generate the trajectory
            trajectory = path.generateTrajectory(currentSpeeds, currentPose.getRotation(), robotConfig);
        }

        PathPlannerAuto.setCurrentTrajectory(trajectory);
        PathPlannerAuto.currentPathName = path.name;

        PathPlannerLogging.logActivePath(path);
        PPLibTelemetry.setCurrentPath(path);

        eventScheduler.initialize(trajectory);

        timer.reset();
        timer.start();
    }

    @Override
    public void execute() {
        double currentTime = timer.get();
        PathPlannerTrajectoryState targetState = trajectory.sample(currentTime);
        if (!controller.isHolonomic() && path.isReversed()) {
            targetState = targetState.reverse();
        }

        Pose2d currentPose = poseSupplier.get();
        ChassisSpeeds currentSpeeds = speedsSupplier.get();

        ChassisSpeeds targetSpeeds = controller.calculateRobotRelativeSpeeds(currentPose, targetState);

        double currentVel = Math.hypot(currentSpeeds.vxMetersPerSecond, currentSpeeds.vyMetersPerSecond);

        PPLibTelemetry.setCurrentPose(currentPose);
        PathPlannerLogging.logCurrentPose(currentPose);

        PPLibTelemetry.setTargetPose(targetState.pose);
        PathPlannerLogging.logTargetPose(targetState.pose);

        PPLibTelemetry.setVelocities(
                currentVel,
                targetState.linearVelocity,
                currentSpeeds.omegaRadiansPerSecond,
                targetSpeeds.omegaRadiansPerSecond);

        output.accept(targetSpeeds, targetState.feedforwards);

        eventScheduler.execute(currentTime);
    }

    @Override
    public boolean isFinished() {
        return timer.hasElapsed(trajectory.getTotalTimeSeconds());
    }

    @Override
    public void end(boolean interrupted) {
        timer.stop();
        PathPlannerAuto.currentPathName = "";
        PathPlannerAuto.setCurrentTrajectory(null);

        // Only output 0 speeds when ending a path that is supposed to stop, this allows
        // interrupting
        // the command to smoothly transition into some auto-alignment routine
        if (!interrupted && path.getGoalEndState().velocityMPS() < 0.1) {
            output.accept(new ChassisSpeeds(), DriveFeedforwards.zeros(robotConfig.numModules));
        }

        PathPlannerLogging.logActivePath(null);

        eventScheduler.end();
    }

    public static void configure(Supplier<Pose2d> poseSupplier,
                          Supplier<ChassisSpeeds> speedsSupplier,
                          BiConsumer<ChassisSpeeds, DriveFeedforwards> output,
                          PathFollowingController controller,
                          RobotConfig robotConfig,
                          BooleanSupplier shouldFlipPath,
                          Subsystem... requirements) {
        DriveToWaypoint.poseSupplier = poseSupplier;
        DriveToWaypoint.speedsSupplier = speedsSupplier;
        DriveToWaypoint.output = output;
        DriveToWaypoint.controller = controller;
        DriveToWaypoint.robotConfig = robotConfig;
        DriveToWaypoint.shouldFlipPath = shouldFlipPath;
        DriveToWaypoint.requirements = requirements;
    }
}