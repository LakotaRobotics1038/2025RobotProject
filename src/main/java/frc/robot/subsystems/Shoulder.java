package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.LimitSwitchConfig.Type;
import com.revrobotics.AbsoluteEncoder;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.NeoMotorConstants;
import frc.robot.constants.ShoulderConstants;
import frc.robot.constants.ShoulderConstants.ShoulderSetpoints;

public class Shoulder extends SubsystemBase {
    private SparkMax leftShoulderMotor = new SparkMax(ShoulderConstants.kLeftMotorPort, MotorType.kBrushless);
    private SparkMax rightShoulderMotor = new SparkMax(ShoulderConstants.kRightMotorPort, MotorType.kBrushless);
    private AbsoluteEncoder shoulderEncoder = leftShoulderMotor.getAbsoluteEncoder();
    private PIDController shoulderController = new PIDController(ShoulderConstants.kP, ShoulderConstants.kI,
            ShoulderConstants.kD);
    private boolean enable = false;

    private Shoulder() {

        SparkMaxConfig leftShoulderConfig = new SparkMaxConfig();
        leftShoulderConfig.idleMode(IdleMode.kBrake)
                .smartCurrentLimit(NeoMotorConstants.kMaxNeoCurrent);
        leftShoulderConfig.limitSwitch
                .reverseLimitSwitchEnabled(true)
                .reverseLimitSwitchType(Type.kNormallyOpen);

        SparkMaxConfig rightShoulderConfig = new SparkMaxConfig();
        rightShoulderConfig.idleMode(IdleMode.kBrake)
                .smartCurrentLimit(NeoMotorConstants.kMaxNeoCurrent);

        rightShoulderConfig.follow(leftShoulderMotor, true);

        leftShoulderMotor.configure(leftShoulderConfig, ResetMode.kResetSafeParameters,
                PersistMode.kPersistParameters);
        rightShoulderMotor.configure(rightShoulderConfig, ResetMode.kResetSafeParameters,
                PersistMode.kPersistParameters);

        shoulderController.setTolerance(ShoulderConstants.kTolerance);
        shoulderController.enableContinuousInput(0, 360);
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
        if (this.enable) {
            this.useOutput(this.shoulderController.calculate(this.getPosition()));
        }
    }

    private void useOutput(double output) {
        double power = MathUtil.clamp(output, -ShoulderConstants.kMaxPower, ShoulderConstants.kMaxPower);
        leftShoulderMotor.set(power);
    }

    public double getPosition() {
        return shoulderEncoder.getPosition();
    }

    public boolean onTarget() {
        return shoulderController.atSetpoint();
    }

    private void setSetpoint(double setpoint) {
        setpoint = MathUtil.clamp(setpoint, 0, ShoulderConstants.kMaxDistance);
    }

    public void setSetpoint(ShoulderSetpoints setpoint) {
        shoulderController.setSetpoint(setpoint.setpoint);
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
        this.enable = true;
        this.shoulderController.reset();
    }

    public void disable() {
        this.enable = false;
        this.useOutput(0);
    }
}
