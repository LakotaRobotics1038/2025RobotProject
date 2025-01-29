package frc.robot.autons;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.commands.FollowPathCommand;
import com.pathplanner.lib.commands.PathfindThenFollowPath;
import com.pathplanner.lib.commands.PathfindingCommand;
import com.pathplanner.lib.config.PIDConstants;
import com.pathplanner.lib.controllers.PPHolonomicDriveController;
import com.pathplanner.lib.path.GoalEndState;
import com.pathplanner.lib.path.IdealStartingState;
import com.pathplanner.lib.path.PathConstraints;
import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.path.Waypoint;
import com.pathplanner.lib.pathfinding.Pathfinding;
import com.pathplanner.lib.util.PathPlannerLogging;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.constants.AutoConstants;
import frc.robot.constants.DriveConstants;
import frc.robot.subsystems.Dashboard;
import frc.robot.subsystems.DriveTrain;

public class FollowPath extends Auton {
    private DriveTrain driveTrain = DriveTrain.getInstance();
    private Dashboard dashboard = Dashboard.getInstance();

    private Alliance alliance;
    // startingPose probably going to be replaced with this.driveTrain::getPose soon
    // but this is just for testing
    private Pose2d startingPose = new Pose2d(2, 2, Rotation2d.kZero);
    private Pose2d endingPose = new Pose2d(6, 6, Rotation2d.k180deg);
    private PathConstraints constraints = new PathConstraints(
            DriveConstants.kMaxSpeedMetersPerSecond,
            AutoConstants.kMaxAccelerationMetersPerSecondSquared,
            AutoConstants.kMaxAngularSpeedRadiansPerSecond,
            AutoConstants.kMaxAngularSpeedRadiansPerSecondSquared);
    private PPHolonomicDriveController driveController = new PPHolonomicDriveController(
            new PIDConstants(AutoConstants.kPXController, AutoConstants.kIXController, 0.0),
            new PIDConstants(AutoConstants.kPThetaController, AutoConstants.kIThetaController, 0.0));
    private IdealStartingState idealStartingState = new IdealStartingState(
            driveTrain.getChassisSpeeds().vxMetersPerSecond,
            Rotation2d.fromDegrees(driveTrain.getHeading()));
    private GoalEndState goalEndState = new GoalEndState(0, Rotation2d.kZero);
    private List<Waypoint> waypoints = PathPlannerPath.waypointsFromPoses(
            new Pose2d(1.0, 1.0, Rotation2d.fromDegrees(0)),
            new Pose2d(3.0, 1.0, Rotation2d.fromDegrees(0)),
            new Pose2d(5.0, 3.0, Rotation2d.fromDegrees(90)));
    private PathPlannerPath path = new PathPlannerPath(waypoints, constraints, idealStartingState, goalEndState);

    public FollowPath(Optional<Alliance> alliance) {
        super(alliance);
        this.alliance = alliance.get();
        PathPlannerLogging.setLogActivePathCallback((activePath) -> {
            if (activePath.size() >= 2) {
                this.path = new PathPlannerPath(PathPlannerPath.waypointsFromPoses(activePath), constraints,
                        idealStartingState, goalEndState);
                this.followPathCommand(path).schedule();
            }
        });
        super.addCommands(
                Commands.runOnce(() -> this.driveTrain.resetOdometry(this.startingPose), this.driveTrain),
                new PathfindingCommand(
                        this.endingPose,
                        this.constraints,
                        this.driveTrain::getPose,
                        this.driveTrain::getChassisSpeeds,
                        (speeds, feedForwards) -> this.driveTrain.applyChassisSpeeds(speeds),
                        this.driveController,
                        AutoConstants.kRobotConfig.get(),
                        this.driveTrain));
    }
}
