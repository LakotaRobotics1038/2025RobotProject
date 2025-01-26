package frc.robot.commands;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.commands.PathfindThenFollowPath;
import com.pathplanner.lib.commands.PathfindingCommand;
import com.pathplanner.lib.path.GoalEndState;
import com.pathplanner.lib.path.IdealStartingState;
import com.pathplanner.lib.path.PathConstraints;
import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.path.Waypoint;
import com.pathplanner.lib.pathfinding.LocalADStar;
import com.pathplanner.lib.pathfinding.Pathfinder;
import com.pathplanner.lib.pathfinding.Pathfinding;
import com.pathplanner.lib.trajectory.PathPlannerTrajectory;
import com.pathplanner.lib.trajectory.PathPlannerTrajectoryState;
import com.pathplanner.lib.util.PathPlannerLogging;

import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.AutoConstants;
import frc.robot.constants.DriveConstants;
import frc.robot.subsystems.Dashboard;
import frc.robot.subsystems.DriveTrain;

public class PathOnTheFly extends Command {
    private Timer timer = new Timer();
    private PathPlannerPath path;
    private List<Pose2d> poses;
    private List<Waypoint> waypoints;
    private PathConstraints constraints;
    private IdealStartingState idealStartingState;
    private GoalEndState goalEndState;
    private PathPlannerTrajectory trajectory;
    private Dashboard dashboard;
    private DriveTrain driveTrain;
    private LocalADStar localADStar;

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
        Pathfinding.setGoalPosition(new Translation2d(5, 5));
        this.constraints = new PathConstraints(DriveConstants.kMaxSpeedMetersPerSecond,
                AutoConstants.kMaxAccelerationMetersPerSecondSquared,
                AutoConstants.kMaxAngularSpeedRadiansPerSecond,
                AutoConstants.kMaxAngularSpeedRadiansPerSecondSquared);
        this.idealStartingState = new IdealStartingState(driveTrain.getChassisSpeeds().vxMetersPerSecond,
                Rotation2d.fromDegrees(driveTrain.getHeading()));
        this.goalEndState = new GoalEndState(0, new Rotation2d(180));
        PathPlannerLogging.setLogActivePathCallback((activePath) -> {
            // log("Odometry/Trajectory", activePath.toArray(new
            // Pose2d[activePath.size()]));
            if (activePath.size() >= 2) {
                this.path = new PathPlannerPath(PathPlannerPath.waypointsFromPoses(activePath), constraints,
                        idealStartingState, goalEndState);
                // dashboard.addPath(path);
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
            this.dashboard.addPose(targetState.pose);
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
