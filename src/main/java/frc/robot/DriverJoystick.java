package frc.robot;

import java.util.Set;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.GoalEndState;
import com.pathplanner.lib.path.IdealStartingState;
import com.pathplanner.lib.path.PathConstraints;
import com.pathplanner.lib.path.PathPlannerPath;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.DeferredCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.AcquireCoralCommand;
import frc.robot.commands.AcquireForL4Command;
import frc.robot.commands.ClimbUpCommand;
import frc.robot.commands.DisposeCoral134Command;
import frc.robot.commands.DisposeCoral2Command;
import frc.robot.commands.PrepClimbCommand;
import frc.robot.commands.SetAcquisitionPositionCommand;
import frc.robot.constants.AutoConstants;
import frc.robot.constants.DriveConstants;
import frc.robot.constants.DriveWaypoints;
import frc.robot.constants.IOConstants;
import frc.robot.libraries.XboxController1038;
import frc.robot.subsystems.DriveTrain;
import frc.robot.utils.AcquisitionPositionSetpoint;

public class DriverJoystick extends XboxController1038 {
    // Subsystem Dependencies
    private final DriveTrain driveTrain = DriveTrain.getInstance();

    private PathPlannerPath path;

    // Previous Status
    private double prevX = 0;
    private double prevY = 0;
    private double prevZ = 0;

    private final Telemetry logger = new Telemetry(DriveConstants.MaxSpeed);

    private Pose2d currentPose;

    // Singleton Setup
    private static DriverJoystick instance;

    public static DriverJoystick getInstance() {
        if (instance == null) {
            System.out.println("Creating a new Driver Xbox Controller");
            instance = new DriverJoystick();
        }
        return instance;
    }

    private DriverJoystick() {
        super(IOConstants.kDriverControllerPort);

        SlewRateLimiter forwardFilter = new SlewRateLimiter(1.0);
        SlewRateLimiter sidewaysFilter = new SlewRateLimiter(1.0);
        SlewRateLimiter rotateFilter = new SlewRateLimiter(1.0);

        driveTrain.setDefaultCommand(this.driveTrain.applyRequest(() -> {
            double x = super.getLeftX();
            double y = super.getLeftY();
            double z = super.getRightX();

            double forward = limitRate(y, prevY, forwardFilter);
            double sideways = limitRate(x, prevX, sidewaysFilter);
            double rotate = limitRate(z, prevZ, rotateFilter);

            prevX = x;
            prevY = y;
            prevZ = z;

            return driveTrain.drive(forward, -sideways, -rotate, true);
        }));

        this.driveTrain.registerTelemetry(logger::telemeterize);

        // Re-orient robot to the field
        super.startButton.whileTrue(new InstantCommand(driveTrain::seedFieldCentric, driveTrain));

        new Trigger(() -> this.getPOVPosition().equals(PovPositions.Up))
                .whileTrue(this.driveTrain.applyRequest(() -> {
                    return driveTrain.drive(DriveConstants.kFineAdjustmentPercent, 0, 0, false);
                }));

        new Trigger(() -> this.getPOVPosition().equals(PovPositions.Down))
                .whileTrue(this.driveTrain.applyRequest(() -> {
                    return driveTrain.drive(-DriveConstants.kFineAdjustmentPercent, 0, 0, false);
                }));

        new Trigger(() -> this.getPOVPosition().equals(PovPositions.Left))
                .whileTrue(this.driveTrain.applyRequest(() -> {
                    return driveTrain.drive(0, DriveConstants.kFineAdjustmentPercent, 0, false);
                }));

        new Trigger(() -> this.getPOVPosition().equals(PovPositions.Right))
                .whileTrue(this.driveTrain.applyRequest(() -> {
                    return driveTrain.drive(0, -DriveConstants.kFineAdjustmentPercent, 0, false);
                }));

        // Lock the wheels into an X formation
        super.xButton.whileTrue(this.driveTrain.setX());
        super.leftBumper.whileTrue(new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.L3Coral));
        super.leftTrigger.whileTrue(new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.FeederStation));
        // super.aButton.whileTrue(
        // new InstantCommand(() -> {
        // this.currentPose = this.driveTrain.getState().Pose;
        // }).andThen(AutoBuilder.followPath(new PathPlannerPath(
        // PathPlannerPath.waypointsFromPoses(this.currentPose,
        // DriveWaypoints.Algae23.getEndpoint()),
        // new PathConstraints(
        // DriveConstants.MaxSpeed,
        // AutoConstants.kMaxAccelerationMetersPerSecondSquared,
        // AutoConstants.kMaxAngularSpeedRadiansPerSecond,
        // AutoConstants.kMaxAngularSpeedRadiansPerSecondSquared),
        // new IdealStartingState(
        // driveTrain.getState().Speeds.vxMetersPerSecond,
        // driveTrain.getState().Pose.getRotation()),
        // new GoalEndState(0, Rotation2d.kZero))));

        super.aButton.whileTrue(
                new InstantCommand(() -> {
                    Pose2d currentPose = this.driveTrain.getState().Pose;
                    this.path = new PathPlannerPath(
                            PathPlannerPath.waypointsFromPoses(currentPose,
                                    DriveWaypoints.Level2RightCoral19.getEndpoint()),
                            new PathConstraints(
                                    AutoConstants.maxSpeed,
                                    AutoConstants.kMaxAccelerationMetersPerSecondSquared,
                                    AutoConstants.kMaxAngularSpeedRadiansPerSecond,
                                    AutoConstants.kMaxAngularSpeedRadiansPerSecondSquared),
                            new IdealStartingState(
                                    driveTrain.getState().Speeds.vxMetersPerSecond,
                                    driveTrain.getState().Pose.getRotation()),
                            new GoalEndState(0, DriveWaypoints.Level2RightCoral19.getEndpoint().getRotation()));
                })
                        .andThen(
                                new DeferredCommand(() -> AutoBuilder.followPath(this.path),
                                        Set.of(this.driveTrain))));

        // super.aButton.whileTrue(
        // new DeferredCommand(() -> AutoBuilder.pathfindToPose(
        // DriveWaypoints.LeftCoral2.getEndpoint(),
        // new PathConstraints(
        // DriveConstants.MaxSpeed,
        // AutoConstants.kMaxAccelerationMetersPerSecondSquared,
        // AutoConstants.kMaxAngularSpeedRadiansPerSecond,
        // AutoConstants.kMaxAngularSpeedRadiansPerSecondSquared),
        // 0),
        // Set.of(this.driveTrain)));

        // super.aButton.toggleOnTrue(new AcquireAlgaeCommand());
        // super.bButton.whileTrue(new DisposeAlgaeCommand());

        super.yButton.whileTrue(new DisposeCoral2Command());
        super.leftBumper.whileTrue(new AcquireCoralCommand());
        super.leftTrigger.whileTrue(new DisposeCoral134Command());
        // super.rightBumper.whileTrue(new AcquireForL4Command());
        super.rightBumper.toggleOnTrue(new ParallelCommandGroup(new PrepClimbCommand(),
                new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.Climb)));
        super.rightTrigger.whileTrue(new ClimbUpCommand());
    }

    /**
     *
     * @param value   Current desired value
     * @param prevVal Previously desired value
     * @param filter  SlewRateLimiter instance for calculation
     * @return desired value rate limited and adjusted for sign changes using
     *         {@link #signChange Sign Change Function}
     */
    private double limitRate(double value, double prevVal, SlewRateLimiter filter) {
        if (value == 0 || signChange(value, prevVal)) {
            filter.reset(0);
        }
        return filter.calculate(value);
    }

    /**
     * Determines if the two given values are opposite signs
     * (one positive one negative)
     *
     * @param a first value to check sign
     * @param b second value to check sign
     * @return are the provided values different signs
     */
    private boolean signChange(double a, double b) {
        return a > 0 && b < 0 || b > 0 && a < 0;
    }
}
