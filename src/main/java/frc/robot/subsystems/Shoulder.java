package frc.robot.subsystems;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkLimitSwitch;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.LimitSwitchConfig.Type;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.DashboardConstants;
import frc.robot.constants.NeoMotorConstants;
import frc.robot.constants.ShoulderConstants;
import frc.robot.constants.ShoulderConstants.ShoulderSetpoints;

public class Shoulder extends SubsystemBase {
    private final SparkMax leftShoulderMotor = new SparkMax(ShoulderConstants.kLeftMotorPort, MotorType.kBrushless);
    private final SparkMax rightShoulderMotor = new SparkMax(ShoulderConstants.kRightMotorPort, MotorType.kBrushless);
    private final AbsoluteEncoder shoulderEncoder = rightShoulderMotor.getAbsoluteEncoder();
    private final PIDController shoulderController = new PIDController(ShoulderConstants.kP, ShoulderConstants.kI,
            ShoulderConstants.kD);
    private final SparkLimitSwitch limitSwitch = rightShoulderMotor.getReverseLimitSwitch();
    private boolean enabled = false;
    private double shoulderOffset = 0.0;
    private ShoulderSetpoints shoulderSetpoints;

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
                .forwardLimitSwitchEnabled(true)
                .forwardLimitSwitchType(Type.kNormallyOpen);

        leftShoulderMotor.configure(leftShoulderConfig, ResetMode.kResetSafeParameters,
                PersistMode.kPersistParameters);
        rightShoulderMotor.configure(rightShoulderConfig, ResetMode.kResetSafeParameters,
                PersistMode.kPersistParameters);

        shoulderController.setTolerance(ShoulderConstants.kTolerance);
        shoulderController.enableContinuousInput(0, 360);

        SmartDashboard.putData(DashboardConstants.kShoulderPID, shoulderController);
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
        double power = this.getSetpoint().equals(ShoulderSetpoints.Climb) && this.getPosition() > 350
                ? MathUtil.clamp(output, -1, 1)
                : MathUtil.clamp(output, -ShoulderConstants.kMaxPower, ShoulderConstants.kMaxPower);
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
        return shoulderController.atSetpoint();
    }

    private void setSetpoint(double setpoint) {
        if (setpoint != 10) {
            setpoint = MathUtil.clamp(setpoint + this.shoulderOffset, ShoulderConstants.kMaxDistance, 360);
        }
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
