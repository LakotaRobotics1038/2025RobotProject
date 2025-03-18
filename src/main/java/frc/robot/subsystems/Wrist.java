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
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.NeoMotorConstants;
import frc.robot.constants.WristConstants;
import frc.robot.constants.WristConstants.WristSetpoints;

public class Wrist extends SubsystemBase {

    private SparkFlex wristMotor = new SparkFlex(WristConstants.kWristCanId, MotorType.kBrushless);
    private AbsoluteEncoder wristEncoder = wristMotor.getAbsoluteEncoder();
    private PIDController wristController = new PIDController(
            WristConstants.kWristControllerP,
            WristConstants.kWristControllerI,
            WristConstants.kWristControllerD);
    private boolean enabled;
    private double lastPosition;
    private double wristOffset = 0.0;
    private WristSetpoints wristSetpoints;

    private static Wrist instance;

    private Wrist() {
        SparkFlexConfig wristConfig = new SparkFlexConfig();
        wristConfig
                .idleMode(IdleMode.kBrake)
                .smartCurrentLimit(NeoMotorConstants.kMaxVortexCurrent)
                .inverted(false);
        wristConfig.absoluteEncoder
                .positionConversionFactor(WristConstants.kEncoderConversion);
        wristMotor.configure(wristConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        wristController.disableContinuousInput();
        wristController.setTolerance(WristConstants.kTolerance);

        Shuffleboard.getTab("Controls").add("WristPID", wristController)
                .withWidget(BuiltInWidgets.kPIDController);
        Shuffleboard.getTab("Controls")
                .addNumber("WristCurrent", this::getPosition);
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
        double position = this.wristEncoder.getPosition();
        double normalizedPosition = position < 180 ? position : position - 360;
        double delta = normalizedPosition - lastPosition;

        if (delta > 180) {
            normalizedPosition -= 360;
        } else if (delta < -180) {
            normalizedPosition += 360;
        }

        this.lastPosition = normalizedPosition;

        return normalizedPosition;
    }

    public boolean onTarget() {
        return this.wristController.atSetpoint();
    }

    private void setSetpoint(double setpoint) {
        double clampedPoint = MathUtil.clamp(setpoint, WristConstants.kMinDistance, WristConstants.kMaxDistance);
        this.wristController.setSetpoint(clampedPoint + this.wristOffset);
    }

    public void setSetpoint(WristSetpoints setPoints) {
        this.setSetpoint(setPoints.getSetpoint());
        this.wristSetpoints = setPoints;
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

    public void setOffset(double wristOffset) {
        this.wristOffset = wristOffset;
    }

    public WristSetpoints getSetpoint() {
        return this.wristSetpoints;
    }
}
