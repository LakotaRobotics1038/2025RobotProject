package frc.robot.autons;

import java.util.Optional;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.GoalEndState;
import com.pathplanner.lib.path.IdealStartingState;
import com.pathplanner.lib.path.PathConstraints;
import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.util.PathPlannerLogging;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.constants.AutoConstants;
import frc.robot.constants.DriveConstants;
import frc.robot.subsystems.Dashboard;
import frc.robot.subsystems.DriveTrain;

public class FollowPath extends Auton {
    private DriveTrain driveTrain = DriveTrain.getInstance();
    private Dashboard dashboard = Dashboard.getInstance();

    public enum Position {
        TEST(5.359, 5.488, new Rotation2d(190));

        private double x;
        private double y;
        private Rotation2d rotation;

        private Position(double x, double y, Rotation2d rotation) {
            this.x = x;
            this.y = y;
            this.rotation = rotation;
        }

        public Pose2d getPose() {
            return new Pose2d(x, y, rotation);
        }
    }

    private Pose2d endingPose;
    private PathConstraints constraints = new PathConstraints(
            DriveConstants.kMaxSpeedMetersPerSecond,
            AutoConstants.kMaxAccelerationMetersPerSecondSquared,
            AutoConstants.kMaxAngularSpeedRadiansPerSecond,
            AutoConstants.kMaxAngularSpeedRadiansPerSecondSquared);
    private IdealStartingState idealStartingState = new IdealStartingState(
            driveTrain.getChassisSpeeds().vxMetersPerSecond,
            Rotation2d.fromDegrees(driveTrain.getHeading()));
    private GoalEndState goalEndState = new GoalEndState(0, Rotation2d.kZero);
    private PathPlannerPath path;

    public FollowPath(Position position) {
        super(Optional.empty());

        endingPose = position.getPose();

        PathPlannerLogging.setLogActivePathCallback((activePath) -> {
            if (activePath.size() >= 2) {
                this.path = new PathPlannerPath(PathPlannerPath.waypointsFromPoses(activePath), constraints,
                        idealStartingState, goalEndState);
                this.dashboard.addPath(path);
            }
        });

        super.addCommands(
                // Commands.runOnce(() -> this.driveTrain.resetOdometry(this.startingPose),
                // this.driveTrain),
                AutoBuilder.pathfindToPose(endingPose, constraints));
    }
}
