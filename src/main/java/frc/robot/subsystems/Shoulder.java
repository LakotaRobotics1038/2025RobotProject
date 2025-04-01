package frc.robot.subsystems;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkLimitSwitch;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.LimitSwitchConfig.Type;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.NeoMotorConstants;
import frc.robot.constants.ShoulderConstants;
import frc.robot.constants.ShoulderConstants.ShoulderSetpoints;

public class Shoulder extends SubsystemBase {
    private SparkMax leftShoulderMotor = new SparkMax(ShoulderConstants.kLeftMotorPort, MotorType.kBrushless);
    private SparkMax rightShoulderMotor = new SparkMax(ShoulderConstants.kRightMotorPort, MotorType.kBrushless);
    private AbsoluteEncoder shoulderEncoder = rightShoulderMotor.getAbsoluteEncoder();
    private SparkLimitSwitch limitSwitch = rightShoulderMotor.getReverseLimitSwitch();
    private boolean enabled = false;
    private double shoulderOffset = 0.0;
    private ShoulderSetpoints enumSetpoint;
    private double setpoint;

    private Shoulder() {

        SparkMaxConfig leftShoulderConfig = new SparkMaxConfig();
        leftShoulderConfig.idleMode(IdleMode.kBrake)
                .smartCurrentLimit(NeoMotorConstants.kMaxNeoCurrent)
                .follow(rightShoulderMotor, true);

        SparkMaxConfig rightShoulderConfig = new SparkMaxConfig();
        rightShoulderConfig.idleMode(IdleMode.kBrake)
                .inverted(true)
                .smartCurrentLimit(NeoMotorConstants.kMaxNeoCurrent);
        rightShoulderConfig.absoluteEncoder
                .positionConversionFactor(ShoulderConstants.kEncoderConversion);
        rightShoulderConfig.limitSwitch
                .reverseLimitSwitchEnabled(true)
                .reverseLimitSwitchType(Type.kNormallyOpen);
        rightShoulderConfig.closedLoop
                .pidf(ShoulderConstants.kP, ShoulderConstants.kI, ShoulderConstants.kD, 1)
                .feedbackSensor(FeedbackSensor.kAbsoluteEncoder)
                .outputRange(-ShoulderConstants.kMaxPower, -ShoulderConstants.kMaxPower)
                .positionWrappingEnabled(true)
                .positionWrappingInputRange(0, 360);

        leftShoulderMotor.configure(leftShoulderConfig, ResetMode.kResetSafeParameters,
                PersistMode.kPersistParameters);
        rightShoulderMotor.configure(rightShoulderConfig, ResetMode.kResetSafeParameters,
                PersistMode.kPersistParameters);

        Shuffleboard.getTab("Controls")
                .addNumber("ShoulderCurrent", this::getPosition)
                .withWidget(BuiltInWidgets.kTextView);
    }

    private static Shoulder instance;

    public static Shoulder getInstance() {
        if (instance == null) {
            instance = new Shoulder();
        }

        return instance;
    }

    private void useOutput(double output) {
        double power = MathUtil.clamp(output, -ShoulderConstants.kMaxPower, ShoulderConstants.kMaxPower);
        rightShoulderMotor.set(power);
    }

    public double getPosition() {
        double position = this.shoulderEncoder.getPosition();

        if (limitSwitch.isPressed()) {
            position = 0;
        }
        return position;
    }

    public boolean onTarget() {
        return MathUtil.isNear(this.setpoint, this.getPosition(), ShoulderConstants.kTolerance);
    }

    private void setSetpoint(double setpoint) {
        setpoint = MathUtil.clamp(setpoint + this.shoulderOffset, ShoulderConstants.kMaxDistance, 360);
        this.setpoint = setpoint;
        rightShoulderMotor.getClosedLoopController().setReference(setpoint, ControlType.kPosition);
    }

    public void setSetpoint(ShoulderSetpoints setpoint) {
        this.setSetpoint(setpoint.setpoint);
        this.enumSetpoint = setpoint;
    }

    public void enable() {
        this.enabled = true;
    }

    public void disable() {
        this.enabled = false;
        this.useOutput(0);
    }

    public void setOffset(double shoulderOffset) {
        this.shoulderOffset = shoulderOffset;
    }

    public ShoulderSetpoints getSetpoint() {
        return this.enumSetpoint;
    }
}
