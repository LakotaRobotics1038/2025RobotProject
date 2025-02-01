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
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.constants.AutoConstants;
import frc.robot.constants.DriveConstants;
import frc.robot.subsystems.Dashboard;
import frc.robot.subsystems.DriveTrain;

public class FollowPath extends Auton {
    private DriveTrain driveTrain = DriveTrain.getInstance();
    private Dashboard dashboard = Dashboard.getInstance();

    // startingPose probably going to be replaced with this.driveTrain::getPose soon
    // but this is just for testing
    private Pose2d startingPose = new Pose2d(2, 2, Rotation2d.kZero);
    private Pose2d endingPose = new Pose2d(6, 6, Rotation2d.k180deg);
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

    public FollowPath(Optional<Alliance> alliance) {
        super(alliance);

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
