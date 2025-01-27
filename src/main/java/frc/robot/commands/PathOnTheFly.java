package frc.robot.commands;

import java.util.List;
import com.pathplanner.lib.commands.PathfindingCommand;
import com.pathplanner.lib.config.PIDConstants;
import com.pathplanner.lib.controllers.PPHolonomicDriveController;
import com.pathplanner.lib.path.GoalEndState;
import com.pathplanner.lib.path.IdealStartingState;
import com.pathplanner.lib.path.PathConstraints;
import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.path.Waypoint;
import com.pathplanner.lib.pathfinding.LocalADStar;
import com.pathplanner.lib.pathfinding.Pathfinding;
import com.pathplanner.lib.trajectory.PathPlannerTrajectory;
import com.pathplanner.lib.trajectory.PathPlannerTrajectoryState;
import com.pathplanner.lib.util.PathPlannerLogging;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.AutoConstants;
import frc.robot.constants.DriveConstants;
import frc.robot.subsystems.Dashboard;
import frc.robot.subsystems.DriveTrain;
import com.pathplanner.lib.commands.FollowPathCommand;

public class PathOnTheFly extends Command {
    private Timer timer = new Timer();
    private PathPlannerPath path;
    private PathConstraints constraints;
    private IdealStartingState idealStartingState;
    private GoalEndState goalEndState;
    private Dashboard dashboard;
    private DriveTrain driveTrain;
    private Pose2d currentPose;

    public enum EndPoints {
        TestWaypoint(new Pose2d(5, 2, Rotation2d.fromDegrees(0))),
        TestWaypoint2(new Pose2d(3, 6, Rotation2d.fromDegrees(0)));

        private Pose2d endpoint;

        private EndPoints(Pose2d endpoint) {
            this.endpoint = endpoint;
        }

        public Pose2d getEndpoint() {
            return this.endpoint;
        }
    }

    public PathOnTheFly() {
        this.driveTrain = DriveTrain.getInstance();
        this.dashboard = Dashboard.getInstance();
        super.addRequirements(this.driveTrain, this.dashboard);
    }

    @Override
    public void initialize() {
        PathfindingCommand.warmupCommand().schedule();
        Pathfinding.setPathfinder(new LocalADStar());
        Pathfinding.setStartPosition(new Translation2d(2, 2));
        Pathfinding.setGoalPosition(new Translation2d(6, 6));
        this.constraints = new PathConstraints(DriveConstants.kMaxSpeedMetersPerSecond,
                AutoConstants.kMaxAccelerationMetersPerSecondSquared,
                AutoConstants.kMaxAngularSpeedRadiansPerSecond,
                AutoConstants.kMaxAngularSpeedRadiansPerSecondSquared);
        this.idealStartingState = new IdealStartingState(driveTrain.getChassisSpeeds().vxMetersPerSecond,
                Rotation2d.fromDegrees(driveTrain.getHeading()));
        this.goalEndState = new GoalEndState(0, new Rotation2d(0));
        PathPlannerLogging.setLogActivePathCallback((activePath) -> {
            // log("Odometry/Trajectory", activePath.toArray(new
            // Pose2d[activePath.size()]));
            if (activePath.size() >= 2) {
                this.path = new PathPlannerPath(PathPlannerPath.waypointsFromPoses(activePath), constraints,
                        idealStartingState, goalEndState);
                // dashboard.addPath(path);
                new FollowPathCommand(
                        this.path,
                        this.driveTrain::getPose,
                        this.driveTrain::getChassisSpeeds,
                        (speeds, feedforwards) -> this.driveTrain.applyChassisSpeeds(speeds),
                        new PPHolonomicDriveController(
                                new PIDConstants(AutoConstants.kPXController, AutoConstants.kIXController, 0.0),
                                // Rotation PID constants
                                new PIDConstants(AutoConstants.kPThetaController, AutoConstants.kIThetaController,
                                        0.0)),
                        AutoConstants.kRobotConfig.get(),
                        () -> {
                            return false;
                        },
                        this.driveTrain);
                // new FollowPathCommand(
                // this.path,
                // this.driveTrain::getChassisSpeeds,
                // (speeds, feedForwards) -> this.driveTrain.applyChassisSpeeds(speeds),
                // new PPHolonomicDriveController(
                // new PIDConstants(AutoConstants.kPXController, AutoConstants.kIXController,
                // 0.0),
                // new PIDConstants(AutoConstants.kPThetaController,
                // AutoConstants.kIThetaController,
                // 0.0)),
                // AutoConstants.kRobotConfig.get(),
                // () -> {
                // return false;
                // },
                // this.driveTrain);
            }
        });
        PathPlannerLogging.setLogTargetPoseCallback((targetPose) -> {
            // log("Odometry/TrajectorySetpoint", targetPose);
        });
        timer.reset();
        timer.start();
    }

    @Override
    public void execute() {
        double currentTime = timer.get();
        if (this.path != null) {
            PathPlannerTrajectoryState targetState = this.path
                    .generateTrajectory(driveTrain.getChassisSpeeds(), Rotation2d.fromDegrees(
                            driveTrain.getHeading()), AutoConstants.kRobotConfig.get())
                    .sample(currentTime);
            this.currentPose = targetState.pose;
            this.dashboard.addPose(this.currentPose);
        }
        if (timer.get() > 5) {
            timer.reset();
            timer.start();
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
    }
}
