package frc.robot.commands;

import java.util.Arrays;
import java.util.List;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.GoalEndState;
import com.pathplanner.lib.path.IdealStartingState;
import com.pathplanner.lib.path.PathConstraints;
import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.path.Waypoint;
import com.pathplanner.lib.trajectory.PathPlannerTrajectory;

import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import frc.robot.constants.AutoConstants;
import frc.robot.constants.DriveConstants;
import frc.robot.subsystems.Dashboard;
import frc.robot.subsystems.DriveTrain;

public class PathOnTheFly extends Command {
    private PathPlannerPath path;
    private List<Pose2d> poses;
    private List<Waypoint> waypoints;
    private PathConstraints constraints;
    private IdealStartingState idealStartingState;
    private GoalEndState goalEndState;
    private Dashboard dashboard;
    private DriveTrain driveTrain;

    public enum EndPoints {
        TestWaypoint(new Pose2d(5, 2, Rotation2d.fromDegrees(0))),
        TestWaypoint2(new Pose2d(2, 8, Rotation2d.fromDegrees(0)));

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
        this.poses = Arrays.asList(EndPoints.TestWaypoint.getEndpoint(), EndPoints.TestWaypoint2.getEndpoint());
        this.constraints = new PathConstraints(DriveConstants.kMaxSpeedMetersPerSecond,
                                               AutoConstants.kMaxAccelerationMetersPerSecondSquared,
                                               AutoConstants.kMaxAngularSpeedRadiansPerSecond,
                                               AutoConstants.kMaxAngularSpeedRadiansPerSecondSquared);
        TrajectoryConfig config = new TrajectoryConfig(DriveConstants.kMaxSpeedMetersPerSecond, AutoConstants.kMaxAccelerationMetersPerSecondSquared).setKinematics(DriveConstants.kDriveKinematics);
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(this.poses, config);
        this.dashboard.setTrajectory(trajectory);
        // this.waypoints = PathPlannerPath.waypointsFromPoses(poses);
        // this.idealStartingState = new IdealStartingState(driveTrain.getChassisSpeeds().vxMetersPerSecond, Rotation2d.fromDegrees(driveTrain.getHeading()));
        // this.goalEndState = new GoalEndState(0, poses.get(poses.size() - 1).getRotation());
        // this.path = new PathPlannerPath(waypoints, constraints, idealStartingState, goalEndState);
        // this.path.preventFlipping = true;
        // PathPlannerTrajectory pathPlannerTrajectory = new PathPlannerTrajectory(path, DriveTrain.getInstance().getChassisSpeeds(),
        //         Rotation2d.fromDegrees(DriveTrain.getInstance().getHeading()),
        //         AutoConstants.kRobotConfig.get());
        // driveTrain.resetOdometry(pathPlannerTrajectory.getInitialPose());
        // this.dashboard.addPath(path);
    }

    @Override
    public void execute() {
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
    }
}
