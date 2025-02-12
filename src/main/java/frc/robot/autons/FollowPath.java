package frc.robot.autons;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

import com.ctre.phoenix6.swerve.SwerveRequest;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.config.PIDConstants;
import com.pathplanner.lib.controllers.PPHolonomicDriveController;
import com.pathplanner.lib.controllers.PathFollowingController;
import com.pathplanner.lib.path.GoalEndState;
import com.pathplanner.lib.path.IdealStartingState;
import com.pathplanner.lib.path.PathConstraints;
import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.util.DriveFeedforwards;
import com.pathplanner.lib.util.PathPlannerLogging;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.commands.DriveToWaypoint;
import frc.robot.constants.AutoConstants;
import frc.robot.constants.DriveConstants;
import frc.robot.subsystems.Dashboard;
import frc.robot.subsystems.DriveTrain;

public class FollowPath extends Auton {
    private final DriveTrain driveTrain = DriveTrain.getInstance();
    private final Dashboard dashboard = Dashboard.getInstance();

    public enum Position {
        TEST(3.165, 3.947, new Rotation2d(0));

        private final double x;
        private final double y;
        private final Rotation2d rotation;

        private Position(double x, double y, Rotation2d rotation) {
            this.x = x;
            this.y = y;
            this.rotation = rotation;
        }

        public Pose2d getPose() {
            return new Pose2d(x, y, rotation);
        }
    }

    private Pose2d startingPose;
    private final PathConstraints constraints = new PathConstraints(
            DriveConstants.MaxSpeed,
            AutoConstants.kMaxAccelerationMetersPerSecondSquared,
            AutoConstants.kMaxAngularSpeedRadiansPerSecond,
            AutoConstants.kMaxAngularSpeedRadiansPerSecondSquared);
    private final IdealStartingState idealStartingState = new IdealStartingState(
            driveTrain.getState().Speeds.vxMetersPerSecond,
            driveTrain.getState().Pose.getRotation());
    private final GoalEndState goalEndState = new GoalEndState(0, Rotation2d.kZero);

    public FollowPath(Position position) {
        super(Optional.empty());

        Pose2d endingPose = position.getPose();

        PathPlannerLogging.setLogActivePathCallback((activePath) -> {
            if (activePath.size() >= 2) {
                PathPlannerPath dashboardPath = new PathPlannerPath(PathPlannerPath.waypointsFromPoses(activePath),
                        constraints,
                        idealStartingState, goalEndState);
                this.dashboard.addPath(dashboardPath);
            }
        });

        super.addCommands(
                AutoBuilder.pathfindToPose(endingPose, this.constraints),
                new DriveToWaypoint(
                        endingPose,
                        this.constraints,
                        this.idealStartingState,
                        this.goalEndState));
    }
}