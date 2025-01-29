package frc.robot.subsystems;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLimitSwitch;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import au.grapplerobotics.ConfigurationFailedException;
import au.grapplerobotics.LaserCan;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.ArmConstants;
import frc.robot.constants.NeoMotorConstants;

public class Arm extends SubsystemBase {
    public enum ArmSetpoint {
        L1Coral(0.0),
        L2Coral(0.0),
        L3Coral(0.0),
        L4Coral(0.0),
        L23Algae(0.0),
        L34Algae(0.0),
        Processor(0.0),
        GroundAlgae(0.0),
        Storage(0.0),
        FeederStation(0.0);

        public final double position;

        ArmSetpoint(double position) {
            this.position = position;
        }

        public double getPosition() {
            return position;
        }
    }

    private SparkFlex armMotor = new SparkFlex(ArmConstants.kArmMotorPort, MotorType.kBrushless);
    private PIDController armController = new PIDController(ArmConstants.kP, ArmConstants.kI, ArmConstants.kD);
    private SparkLimitSwitch lowerLimitSwitch = armMotor.getForwardLimitSwitch();
    private LaserCan laser = new LaserCan(ArmConstants.kArmLaserMotorPort);
    private static Arm instance;

    public static Arm getInstance() {
        if (instance == null) {
            instance = new Arm();
        }
        return instance;
    }

    private Arm() {
        SparkMaxConfig armConfig = new SparkMaxConfig();
        armConfig.idleMode(IdleMode.kBrake)
                .smartCurrentLimit(NeoMotorConstants.kMaxNeoCurrent);

        armMotor.configure(armConfig, ResetMode.kResetSafeParameters,
                PersistMode.kPersistParameters);

        armController.setTolerance(ArmConstants.kTolerance);
        armController.enableContinuousInput(0, ArmConstants.kEncoderConversion);
    }

    protected void useOutput(double output, double setpoint) {
        double power = MathUtil.clamp(output, -ArmConstants.kMaxArmPower, ArmConstants.kMaxArmPower);
        armMotor.set(power);
    }

    public double getPosition() {
        LaserCan.Measurement measurement = laser.getMeasurement();
        if (measurement != null && measurement.status == LaserCan.LASERCAN_STATUS_VALID_MEASUREMENT) {
            return laser.getMeasurement().distance_mm;
        }
        return 0.0;
    }

    public boolean onTarget() {
        return armController.atSetpoint();
    }

    public boolean isPressed() {
        return lowerLimitSwitch.isPressed();
    }

    public void setSetpoint(ArmSetpoint setpoint) {
        double value = setpoint.position;
        value = MathUtil.clamp(value, 0, ArmConstants.kMaxArmPower);
        setSetpoint(value);
    }

    /**
     * Sets the setpoint for the subsystem.
     *
     * @param setpoint the setpoint for the subsystem
     */

    private final void setSetpoint(double setpoint) {
        armController.setSetpoint(setpoint);
    }

    public void setP(double P) {
        armController.setP(P);
    }

    public void setI(double I) {
        armController.setP(I);
    }

    public void setD(double D) {
        armController.setP(D);
    }
}
