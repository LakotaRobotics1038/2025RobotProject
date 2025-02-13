package frc.robot.autons;

import java.util.Optional;

import com.ctre.phoenix6.swerve.SwerveRequest;
import com.pathplanner.lib.commands.FollowPathCommand;
import com.pathplanner.lib.config.PIDConstants;
import com.pathplanner.lib.controllers.PPHolonomicDriveController;
import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.trajectory.PathPlannerTrajectory;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.constants.AutoConstants;
import frc.robot.constants.FieldConstants;
import frc.robot.subsystems.DriveTrain;

public abstract class Auton extends SequentialCommandGroup {
    private Pose2d initialPose;
    protected DriveTrain driveTrain = DriveTrain.getInstance();
    protected Alliance alliance;

    public Auton(Optional<Alliance> alliance) {
        if (alliance.isPresent()) {
            this.alliance = alliance.get();
        }
        this.addCommands(new WaitCommand(AutonSelector.getInstance().chooseDelay()));
    }

    private void setInitialPose(Pose2d initialPose) {
        this.initialPose = initialPose;

        // We need to invert the starting pose for the red alliance.
        if (alliance == Alliance.Red) {
            Translation2d transformedTranslation = new Translation2d(
                    FieldConstants.kFieldLength - this.initialPose.getX(),
                    this.initialPose.getY());
            Rotation2d transformedHeading = this.initialPose.getRotation().plus(new Rotation2d(Math.PI));

            this.initialPose = new Pose2d(transformedTranslation, transformedHeading);
        }
    }

    protected void setInitialPose(PathPlannerTrajectory initialTrajectory) {
        this.setInitialPose(initialTrajectory.getInitialPose());
    }

    protected void setInitialPose(PathPlannerTrajectory initialTrajectory, Rotation2d rotationOffset) {
        Pose2d initialPose = initialTrajectory.getInitialPose();
        this.setInitialPose(new Pose2d(initialPose.getTranslation(), initialPose.getRotation().plus(rotationOffset)));
    }

    public Pose2d getInitialPose() {
        return initialPose;
    }

    public Command followPathCommand(PathPlannerPath path) {
        try {
            if (!AutoConstants.kRobotConfig.isPresent()) {
                throw new Error("PP Robot Config is Missing");
            }
            return new FollowPathCommand(
                    path,
                    // Robot pose supplier
                    () -> this.driveTrain.getState().Pose,
                    // ChassisSpeeds supplier. MUST BE ROBOT RELATIVE
                    () -> this.driveTrain.getState().Speeds,
                    // Method that will drive the robot given ROBOT RELATIVE ChassisSpeeds
                    (speeds, feedforwards) -> this.driveTrain.setControl(
                            new SwerveRequest.ApplyRobotSpeeds().withSpeeds(speeds)
                                    .withWheelForceFeedforwardsX(feedforwards.robotRelativeForcesXNewtons())
                                    .withWheelForceFeedforwardsY(feedforwards.robotRelativeForcesYNewtons())),
                    new PPHolonomicDriveController(
                            // Translation PIDconstants
                            new PIDConstants(AutoConstants.kPXController, AutoConstants.kIXController, 0.0),
                            // Rotation PID constants
                            new PIDConstants(AutoConstants.kPThetaController, AutoConstants.kIThetaController, 0.0)),
                    AutoConstants.kRobotConfig.get(),
                    () -> DriverStation.getAlliance().orElse(Alliance.Blue) == Alliance.Red,
                    this.driveTrain // Reference to this subsystem to set requirements
            );
        } catch (Exception e) {
            DriverStation.reportError("Failed to create auton: " + e.getMessage(), e.getStackTrace());
            return Commands.none();
        }
    }
}