package frc.robot.subsystems;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.LimitSwitchConfig.Type;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
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
    private PIDController shoulderController = new PIDController(ShoulderConstants.kP, ShoulderConstants.kI,
            ShoulderConstants.kD);
    private boolean enabled = false;
    private double shoulderOffset = 0.0;
    private double lastPosition;
    private ShoulderSetpoints shoulderSetpoints;

    private Shoulder() {

        SparkMaxConfig leftShoulderConfig = new SparkMaxConfig();
        leftShoulderConfig.idleMode(IdleMode.kBrake)
                .inverted(true)
                .smartCurrentLimit(NeoMotorConstants.kMaxNeoCurrent)
                .follow(rightShoulderMotor, true);

        SparkMaxConfig rightShoulderConfig = new SparkMaxConfig();
        rightShoulderConfig.idleMode(IdleMode.kBrake)
                .smartCurrentLimit(NeoMotorConstants.kMaxNeoCurrent);
        rightShoulderConfig.absoluteEncoder
                .positionConversionFactor(ShoulderConstants.kEncoderConversion);
        rightShoulderConfig.limitSwitch
                .reverseLimitSwitchEnabled(true)
                .reverseLimitSwitchType(Type.kNormallyOpen);

        leftShoulderMotor.configure(leftShoulderConfig, ResetMode.kResetSafeParameters,
                PersistMode.kPersistParameters);
        rightShoulderMotor.configure(rightShoulderConfig, ResetMode.kResetSafeParameters,
                PersistMode.kPersistParameters);

        shoulderController.setTolerance(ShoulderConstants.kTolerance);
        shoulderController.enableContinuousInput(0, 360);

        Shuffleboard.getTab("Controls").add("ShoulderPID", shoulderController)
                .withWidget(BuiltInWidgets.kPIDController);
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

    @Override
    public void periodic() {
        if (this.enabled) {
            this.useOutput(this.shoulderController.calculate(this.getPosition()));
        }
    }

    private void useOutput(double output) {
        double power = MathUtil.clamp(output, -ShoulderConstants.kMaxPower, ShoulderConstants.kMaxPower);
        rightShoulderMotor.set(power);
    }

    public double getPosition() {
        double position = this.shoulderEncoder.getPosition();

        if (lastPosition < 5 && position > 5) {
            position = 0;
        }

        this.lastPosition = position;

        return position;
    }

    public boolean onTarget() {
        return shoulderController.atSetpoint();
    }

    private void setSetpoint(double setpoint) {
        setpoint = MathUtil.clamp(setpoint + this.shoulderOffset, 0, ShoulderConstants.kMaxDistance);
        shoulderController.setSetpoint(setpoint);
    }

    public void setSetpoint(ShoulderSetpoints setpoint) {
        this.setSetpoint(setpoint.setpoint);
        this.shoulderSetpoints = setpoint;
    }

    public void setP(double p) {
        shoulderController.setP(p);
    }

    public void setI(double i) {
        shoulderController.setI(i);
    }

    public void setD(double d) {
        shoulderController.setD(d);
    }

    public void enable() {
        this.enabled = true;
        this.shoulderController.reset();
    }

    public void disable() {
        this.enabled = false;
        this.useOutput(0);
    }

    public void setOffset(double shoulderOffset) {
        this.shoulderOffset = shoulderOffset;
    }

    public ShoulderSetpoints getSetpoint() {
        return this.shoulderSetpoints;
    }
}
