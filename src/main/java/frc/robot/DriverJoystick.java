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
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.AlignToAlgaeCommand;
import frc.robot.commands.DetermineWaypointCommand;
import frc.robot.commands.SetAcquisitionPositionCommand;
import frc.robot.commands.SetAcquisitionPositionCommand.FinishActions;
import frc.robot.constants.AutoConstants;
import frc.robot.constants.DriveConstants;
import frc.robot.constants.IOConstants;
import frc.robot.libraries.XboxController1038;
import frc.robot.subsystems.DriveTrain;

public class DriverJoystick extends XboxController1038 {
    // Subsystem Dependencies
    private final DriveTrain driveTrain = DriveTrain.getInstance();
    private final OperatorState operatorState = OperatorState.getInstance();

    // Commands
    private final DetermineWaypointCommand determineWaypointCommand = new DetermineWaypointCommand();

    // Instance Variables
    private PathPlannerPath path;
    private Pose2d targetPose;

    // Previous Status
    private double prevX = 0;
    private double prevY = 0;
    private double prevZ = 0;

    private final Telemetry logger = new Telemetry(DriveConstants.MaxSpeed);

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
            double x = this.getCubedLeftX();
            double y = this.getCubedLeftY();
            double z = this.getRightX();

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

        // Lock the wheels into an X formation
        this.xButton.whileTrue(this.driveTrain.setX());

        this.bButton.whileTrue(new AlignToAlgaeCommand(this::getCubedLeftY, this::getCubedLeftX));

        // TODO: "we need a comment to run this command?"
        this.aButton.onTrue(
                new PrintCommand("Running SetAcquisitionPositionCommand")
                        .andThen(new SetAcquisitionPositionCommand(operatorState::getLastInput,
                                FinishActions.NoFinish)));
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
     * Gets the value of the left X axis and cubes it
     *
     * @return
     */
    private double getCubedLeftX() {
        double x = this.getLeftX();

        return Math.copySign(Math.pow(x, 3), x);
    }

    /**
     * Gets the value of the left Y axis and cubes it
     *
     * @return
     */
    private double getCubedLeftY() {
        double y = this.getLeftY();

        return Math.copySign(Math.pow(y, 3), y);
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
