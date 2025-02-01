package frc.robot.subsystems;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.NeoMotorConstants;
import frc.robot.constants.WristConstants;
import frc.robot.constants.WristConstants.WristSetPoints;

public class Wrist extends SubsystemBase {

    private SparkFlex wristMotor = new SparkFlex(WristConstants.kWristCanId, MotorType.kBrushless);
    private AbsoluteEncoder wristEncoder = wristMotor.getAbsoluteEncoder();
    private PIDController wristController = new PIDController(
            WristConstants.kWristControllerP,
            WristConstants.kWristControllerI,
            WristConstants.kWristControllerD);
    private boolean enabled;
    private static Wrist instance;

    private Wrist() {
        SparkFlexConfig wristConfig = new SparkFlexConfig();
        wristConfig
                .idleMode(IdleMode.kBrake)
                .smartCurrentLimit(NeoMotorConstants.kMaxVortexCurrent)
                .inverted(false);
        wristMotor.configure(wristConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    public static Wrist getInstance() {
        if (instance == null) {
            instance = new Wrist();
        }
        return instance;
    }

    @Override
    public void periodic() {
        if (enabled) {
            this.useOutput(this.wristController.calculate(this.getPosition()));
        }
    }

    protected void useOutput(double output) {
        double power = MathUtil.clamp(output, WristConstants.kMinPower, WristConstants.kMaxPower);
        this.wristMotor.set(power);
    }

    public double getPosition() {
        return this.wristEncoder.getPosition();
    }

    public boolean onTarget() {
        return this.wristController.atSetpoint();
    }

    public void setSetpoint(double setpoint) {
        double clampedPoint = MathUtil.clamp(setpoint, 0, WristConstants.kMaxDistance);
        this.wristController.setSetpoint(clampedPoint);
    }

    public void setSetpoint(WristSetPoints setPoints) {
        this.setSetpoint(setPoints.getSetpoint());
    }

    public void setP(double p) {
        this.wristController.setP(p);
    }

    public void setI(double i) {
        this.wristController.setI(i);
    }

    public void setD(double d) {
        this.wristController.setD(d);
    }

    public void enable() {
        this.enabled = true;
        this.wristController.reset();
    }

    public void disable() {
        this.enabled = false;
        this.useOutput(0);
    }

    public boolean isEnabled() {
        return enabled;
    }

}
