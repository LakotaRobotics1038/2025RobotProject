package frc.robot;

import java.util.Set;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.GoalEndState;
import com.pathplanner.lib.path.IdealStartingState;
import com.pathplanner.lib.path.PathConstraints;
import com.pathplanner.lib.path.PathPlannerPath;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.filter.LinearFilter;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.DeferredCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.AlignToAlgaeCommand;
import frc.robot.commands.AlignWithBargeCommand;
import frc.robot.commands.DetermineWaypointCommand;
import frc.robot.commands.SetAcquisitionPositionCommand;
import frc.robot.commands.SetAcquisitionPositionCommand.FinishActions;
import frc.robot.constants.AutoConstants;
import frc.robot.constants.DriveConstants;
import frc.robot.constants.IOConstants;
import frc.robot.libraries.XboxController1038;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Extension;
import frc.robot.utils.AcquisitionPositionSetpoint;

public class DriverJoystick extends XboxController1038 {
    // Subsystem Dependencies
    private final DriveTrain driveTrain = DriveTrain.getInstance();
    private final OperatorState operatorState = OperatorState.getInstance();
    private final Extension extension = Extension.getInstance();

    // Commands
    private final DetermineWaypointCommand determineWaypointCommand = new DetermineWaypointCommand();

    // Instance Variables
    private PathPlannerPath path;
    private Pose2d targetPose;
    private double maxPower = DriveConstants.defaultMaxPower;

    // Previous Status
    private double prevSideways = 0;
    private double prevForward = 0;
    private double prevRotate = 0;

    // Limiters
    SlewRateLimiter forwardLimiter = new SlewRateLimiter(2.0);
    SlewRateLimiter sidewaysLimiter = new SlewRateLimiter(2.0);
    SlewRateLimiter rotateLimiter = new SlewRateLimiter(2.0);

    LinearFilter forwardFilter = LinearFilter.movingAverage(5);
    LinearFilter sidewaysFilter = LinearFilter.movingAverage(5);

    private final Telemetry logger = new Telemetry(DriveConstants.MaxSpeed);

    // High Gain Near Center
    private final double a = 1;

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

        driveTrain.setDefaultCommand(this.driveTrain.applyRequest(() -> {
            double sideways = this.getSidewaysValue();
            double forward = this.getForwardValue();
            double rotate = this.getRotateValue();

            if (extension.getPosition() > 15) {
                sideways = MathUtil.clamp(sideways, -0.25, 0.25);
                forward = MathUtil.clamp(forward, -0.25, 0.25);
                rotate = MathUtil.clamp(rotate, -0.25, 0.25);
            }

            return driveTrain.drive(forward, -sideways, -rotate, true);
        }));

        this.driveTrain.registerTelemetry(logger::telemeterize);

        // Re-orient robot to the field
        this.startButton.whileTrue(new InstantCommand(driveTrain::seedFieldCentric, driveTrain));

        new Trigger(() -> this.getPOVPosition().equals(PovPositions.Up))
                .whileTrue(this.driveTrain
                        .applyRequest(() -> driveTrain.drive(DriveConstants.kFineAdjustmentPercent, 0, 0, false)));

        new Trigger(() -> this.getPOVPosition().equals(PovPositions.Down))
                .whileTrue(this.driveTrain
                        .applyRequest(() -> driveTrain.drive(-DriveConstants.kFineAdjustmentPercent, 0, 0, false)));

        new Trigger(() -> this.getPOVPosition().equals(PovPositions.Left))
                .whileTrue(this.driveTrain
                        .applyRequest(() -> driveTrain.drive(0, DriveConstants.kFineAdjustmentPercent, 0, false)));

        new Trigger(() -> this.getPOVPosition().equals(PovPositions.Right))
                .whileTrue(this.driveTrain
                        .applyRequest(() -> driveTrain.drive(0, -DriveConstants.kFineAdjustmentPercent, 0, false)));

        this.rightBumper
                .onTrue(new InstantCommand(() -> this.maxPower = DriveConstants.overdrivePower))
                .onFalse(new InstantCommand(() -> this.maxPower = DriveConstants.defaultMaxPower));

        this.yButton.whileTrue(new AlignWithBargeCommand(this::getSidewaysValue));

        this.leftBumper.onTrue(
                new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.LatchClimb, FinishActions.NoFinish));
        this.leftTrigger
                .onTrue(new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.Climb, FinishActions.NoFinish));

        // Lock the wheels into an X formation
        this.xButton.whileTrue(this.driveTrain.setX());

        this.bButton
                .and(operatorState::isGroundAlgae)
                .whileTrue(new AlignToAlgaeCommand(this::getForwardValue, this::getSidewaysValue));

        this.aButton.whileTrue(determineWaypointCommand.andThen(
                new InstantCommand(() -> {
                    Pose2d currentPose = this.driveTrain.getState().Pose;
                    this.targetPose = determineWaypointCommand.getPose2d().orElse(null);

                    if (this.targetPose != null) {
                        this.path = new PathPlannerPath(
                                PathPlannerPath.waypointsFromPoses(currentPose,
                                        targetPose),
                                new PathConstraints(
                                        AutoConstants.maxSpeed,
                                        AutoConstants.kMaxAccelerationMetersPerSecondSquared,
                                        AutoConstants.kMaxAngularSpeedRadiansPerSecond,
                                        AutoConstants.kMaxAngularSpeedRadiansPerSecondSquared),
                                new IdealStartingState(
                                        driveTrain.getState().Speeds.vxMetersPerSecond,
                                        driveTrain.getState().Pose.getRotation()),
                                new GoalEndState(0, targetPose.getRotation()));
                    }
                })
                        .andThen(
                                new DeferredCommand(() -> AutoBuilder.followPath(this.path),
                                        Set.of(this.driveTrain)).onlyIf(() -> this.targetPose != null))));
    }

    /**
     * Gets the value of the left X axis, filters it, and applies an acceleration
     * limit
     *
     * @return sideways value
     */
    private double getSidewaysValue() {
        prevSideways = limitRate(sidewaysFilter.calculate(this.getLeftX() * maxPower), prevSideways, sidewaysLimiter);

        return prevSideways;
    }

    /**
     * Gets the value of the left Y axis, filters it, and applies an acceleration
     * limit
     *
     * @return forward value
     */
    private double getForwardValue() {
        prevForward = limitRate(forwardFilter.calculate(this.getLeftY() * maxPower), prevForward, forwardLimiter);

        return prevForward;
    }

    /**
     * Gets the value of the Right X axis and applies an acceleration
     * limit
     *
     * @return rotate value
     */
    private double getRotateValue() {
        prevRotate = limitRate(this.getRightX() * maxPower, prevRotate, rotateLimiter);

        return prevRotate;
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
