package frc.robot.commands;

import java.util.Arrays;
import java.util.List;

import com.pathplanner.lib.path.GoalEndState;
import com.pathplanner.lib.path.IdealStartingState;
import com.pathplanner.lib.path.PathConstraints;
import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.path.Waypoint;
import com.pathplanner.lib.trajectory.PathPlannerTrajectory;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.AutoConstants;
import frc.robot.constants.DriveConstants;
import frc.robot.subsystems.Dashboard;
import frc.robot.subsystems.DriveTrain;

public class PathOnTheFly extends Command {
    private List<Pose2d> poses;
    private Dashboard dashboard;
    private DriveTrain driveTrain;

    public enum EndPoints {
        TestWaypoint(new Pose2d(5, 2, Rotation2d.fromDegrees(0))),
        TestWaypoint2(new Pose2d(8, 4, Rotation2d.fromDegrees(0)));

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
        this.poses = Arrays.asList(this.driveTrain.getPose(), EndPoints.TestWaypoint.getEndpoint(), EndPoints.TestWaypoint2.getEndpoint());
        TrajectoryConfig config = new TrajectoryConfig(DriveConstants.kMaxSpeedMetersPerSecond, AutoConstants.kMaxAccelerationMetersPerSecondSquared).setKinematics(DriveConstants.kDriveKinematics);
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(this.poses, config);
        this.dashboard.setTrajectory(trajectory);
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
