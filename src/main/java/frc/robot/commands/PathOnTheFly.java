package frc.robot.commands;

import java.util.List;
import java.util.NoSuchElementException;

import com.pathplanner.lib.path.GoalEndState;
import com.pathplanner.lib.path.IdealStartingState;
import com.pathplanner.lib.path.PathConstraints;
import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.path.Waypoint;
import com.pathplanner.lib.trajectory.PathPlannerTrajectory;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.AutoConstants;
import frc.robot.constants.DriveConstants;
import frc.robot.subsystems.Dashboard;
import frc.robot.subsystems.DriveTrain;

public class PathOnTheFly extends Command {
    private EndPoints endpoint;
    private PathPlannerPath path;
    private List<Waypoint> waypoints;
    private PathConstraints constraints;
    private IdealStartingState idealStartingState;
    private GoalEndState goalEndState;
    private DriveTrain driveTrain = DriveTrain.getInstance();

    public enum EndPoints {
        TestWaypoint(new Pose2d(5, 5, Rotation2d.fromDegrees(0)));

        private Pose2d endpoint;

        private EndPoints(Pose2d endpoint) {
            this.endpoint = endpoint;
        }

        public Pose2d getEndpoint() {
            return this.endpoint;
        }
    }

    public PathOnTheFly(EndPoints endpoint) {
        super.addRequirements(Dashboard.getInstance());
        System.out.println("WOAHHHH");
        this.endpoint = endpoint;
    }

    @Override
    public void initialize() {
        waypoints = PathPlannerPath.waypointsFromPoses(driveTrain.getPose(), endpoint.getEndpoint());
        constraints = new PathConstraints(DriveConstants.kMaxSpeedMetersPerSecond,
                AutoConstants.kMaxAccelerationMetersPerSecondSquared,
                AutoConstants.kMaxAngularSpeedRadiansPerSecond,
                AutoConstants.kMaxAngularSpeedRadiansPerSecondSquared);
        idealStartingState = new IdealStartingState(driveTrain.getChassisSpeeds().vxMetersPerSecond,
                Rotation2d.fromDegrees(driveTrain.getHeading()));
        goalEndState = new GoalEndState(0.0, endpoint.getEndpoint().getRotation());
        path = new PathPlannerPath(waypoints, constraints, idealStartingState, goalEndState);
        path.preventFlipping = true;
        try {
            new PathPlannerTrajectory(path, DriveTrain.getInstance().getChassisSpeeds(),
                    Rotation2d.fromDegrees(DriveTrain.getInstance().getHeading()),
                    AutoConstants.kRobotConfig.get());

            Dashboard.getInstance().addPath();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }

        // path.generateTrajectory(driveTrain.getChassisSpeeds(),
        // Rotation2d.fromDegrees(driveTrain.getHeading()),
        // AutoConstants.kRobotConfig.get());
    }

    @Override
    public void execute() {
        System.out.println();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
