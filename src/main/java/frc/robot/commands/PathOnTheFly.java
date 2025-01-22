package frc.robot.commands;

import java.util.List;

import com.pathplanner.lib.path.PathConstraints;
import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.path.Waypoint;
import com.pathplanner.lib.util.PathPlannerLogging;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.AutoConstants;
import frc.robot.constants.DriveConstants;

public class PathOnTheFly extends Command {
    private EndPoints endpoint;
    private PathPlannerPath path;
    private List<Waypoint> waypoints;
    private PathConstraints constraints;

    private final ShuffleboardTab tempTab = Shuffleboard.getTab("Temp Tab");
    private Field2d field = new Field2d();

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
        this.endpoint = endpoint;

        tempTab.add(field)
                .withPosition(2, 0)
                .withSize(8, 5)
                .withWidget(BuiltInWidgets.kField);
    }

    @Override
    public void initialize() {
        System.out.println("HIIIII");
        constraints = new PathConstraints(DriveConstants.kMaxSpeedMetersPerSecond,
                AutoConstants.kMaxAccelerationMetersPerSecondSquared,
                AutoConstants.kMaxAngularSpeedRadiansPerSecond,
                AutoConstants.kMaxAngularSpeedRadiansPerSecondSquared);
        waypoints = PathPlannerPath.waypointsFromPoses(endpoint.getEndpoint());
        path = new PathPlannerPath(waypoints, constraints, null, null);
        path.preventFlipping = true;
    }

    @Override
    public void execute() {
        PathPlannerLogging.setLogCurrentPoseCallback((pose) -> {
            field.setRobotPose(pose);
        });

        PathPlannerLogging.setLogTargetPoseCallback((pose) -> {
            field.getObject("target pose").setPose(pose);
        });

        PathPlannerLogging.setLogTargetPoseCallback((poses) -> {
            field.getObject("path").setPoses(poses);
        });
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
