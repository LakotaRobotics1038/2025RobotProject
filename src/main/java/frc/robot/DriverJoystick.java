package frc.robot;

import frc.robot.constants.DriveConstants;
import frc.robot.constants.IOConstants;
import frc.robot.libraries.XboxController1038;
import frc.robot.subsystems.DriveTrain;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;

public class DriverJoystick extends XboxController1038 {
    // Subsystem Dependencies
    private final DriveTrain driveTrain = DriveTrain.getInstance();

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
            double x = super.getLeftX();
            double y = super.getLeftY();
            double z = super.getRightX();

            double forward = limitRate(y, prevY, forwardFilter);
            double sideways = limitRate(x, prevX, sidewaysFilter);
            double rotate = limitRate(z, prevZ, rotateFilter);

            prevX = x;
            prevY = y;
            prevZ = z;

            switch (this.getPOVPosition()) {
                case Up:
                    forward = DriveConstants.kFineAdjustmentPercent;
                    sideways = 0;
                    break;
                case Down:
                    forward = -DriveConstants.kFineAdjustmentPercent;
                    sideways = 0;
                    break;
                case Left:
                    forward = 0;
                    sideways = -DriveConstants.kFineAdjustmentPercent;
                    break;
                case Right:
                    forward = 0;
                    sideways = DriveConstants.kFineAdjustmentPercent;
                    break;
                default:
                    break;
            }

            return driveTrain.drive(forward, -sideways, -rotate);
        }));

        this.driveTrain.registerTelemetry(logger::telemeterize);

        // Re-orient robot to the field
        super.startButton.whileTrue(new InstantCommand(driveTrain::seedFieldCentric, driveTrain));

        // Lock the wheels into an X formation
        super.xButton.whileTrue(this.driveTrain.setX());
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
