package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLimitSwitch;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.LimitSwitchConfig.Type;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkFlexConfig;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.ExtensionConstants;
import frc.robot.constants.ExtensionConstants.ExtensionSetpoints;
import frc.robot.constants.NeoMotorConstants;

public class Extension extends SubsystemBase {
    private SparkFlex extensionMotor = new SparkFlex(ExtensionConstants.kExtensionMotorPort, MotorType.kBrushless);
    private PIDController extensionController = new PIDController(ExtensionConstants.kP, ExtensionConstants.kI,
            ExtensionConstants.kD);
    private SparkLimitSwitch limitSwitch = extensionMotor.getReverseLimitSwitch();
    private RelativeEncoder extensionEncoder = extensionMotor.getEncoder();
    private static Extension instance;
    private boolean enabled;
    private double extensionOffset = 0.0;

    /**
     * Creates an instance of the Arm subsystem if it does not exist.
     *
     * @return An instance of the arm subsystem
     */
    public static Extension getInstance() {
        if (instance == null) {
            instance = new Extension();
        }
        return instance;
    }

    private Extension() {
        SparkFlexConfig extensionConfig = new SparkFlexConfig();
        extensionConfig.idleMode(IdleMode.kBrake)
                .smartCurrentLimit(NeoMotorConstants.kMaxVortexCurrent);
        extensionConfig.encoder
                .positionConversionFactor(ExtensionConstants.kEncoderConversion);

        extensionMotor.configure(extensionConfig, ResetMode.kResetSafeParameters,
                PersistMode.kPersistParameters);

        extensionConfig.limitSwitch
                .reverseLimitSwitchType(Type.kNormallyOpen)
                .reverseLimitSwitchEnabled(true);

        extensionController.setTolerance(ExtensionConstants.kTolerance);
        extensionController.disableContinuousInput();

        Shuffleboard.getTab("Controls").add("ExtPID", extensionController)
                .withWidget(BuiltInWidgets.kPIDController);
        Shuffleboard.getTab("Controls")
                .addNumber("ExtCurrent", this::getPosition)
                .withWidget(BuiltInWidgets.kTextView);
    }

    @Override
    public void periodic() {
        if (enabled) {
            useOutput(extensionController.calculate(getPosition()));
        }
        if (limitSwitch.isPressed() && extensionEncoder.getPosition() != 0.0) {
            extensionEncoder.setPosition(0.0);
        }
    }

    protected void useOutput(double output) {
        double power = MathUtil.clamp(output, -ExtensionConstants.kMaxExtensionPower,
                ExtensionConstants.kMaxExtensionPower);
        extensionMotor.set(power);
    }

    /**
     * Returns current distance received by the Arm laser as a double in millimeters
     *
     * @return laser distance is millimeters
     */
    public double getPosition() {
        return extensionEncoder.getPosition();
    }

    /**
     * Returns the current setpoint of the subsystem.
     *
     * @return The current setpoint
     */
    public double getSetpoint() {
        return extensionController.getSetpoint();
    }

    /**
     * Returns whether or not the PID controller is on target as a boolean
     *
     * @return boolean whether or not the PID controller is at setpoint
     */
    public boolean onTarget() {
        return extensionController.atSetpoint();
    }

    /**
     * Returns whether or not the limit switch is pressed as a boolean
     *
     * @return boolean whether or not the limit switch is pressed
     */
    public boolean isLimitSwitchPressed() {
        return limitSwitch.isPressed();
    }

    /**
     * Sets the setpoint for the subsystem.
     *
     * @param setpoint the setpoint for the subsystem
     */
    public void setSetpoint(ExtensionSetpoints setpoint) {
        setSetpoint(setpoint.position);
    }

    /**
     * Sets the setpoint for the subsystem.
     *
     * @param setpoint the setpoint for the subsystem
     */
    public final void setSetpoint(double setpoint) {
        setpoint = MathUtil.clamp(setpoint + extensionOffset, 0, ExtensionConstants.kExtensionMaximum);
        extensionController.setSetpoint(setpoint);
    }

    /**
     * Sets the P for the PID controller.
     *
     * @param P the P to set to the PID controller
     */
    public void setP(double P) {
        extensionController.setP(P);
    }

    /**
     * Sets the I for the PID controller.
     *
     * @param I the I to set to the PID controller
     */
    public void setI(double I) {
        extensionController.setP(I);
    }

    /**
     * Sets the D for the PID controller.
     *
     * @param D the D to set to the PID controller
     */
    public void setD(double D) {
        extensionController.setP(D);
    }

    /** Enables the PID control. Resets the controller. */
    public void enable() {
        enabled = true;
        extensionController.reset();
    }

    /** Disables the PID control. Sets output to zero. */
    public void disable() {
        enabled = false;
        useOutput(0);
    }

    public void setOffset(double extensionOffset) {
        this.extensionOffset = extensionOffset;
    }
}
