package frc.robot.subsystems;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLimitSwitch;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkFlexConfig;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.AcquisitionConstants;
import frc.robot.constants.NeoMotorConstants;

public class Acquisition extends SubsystemBase {
    private final SparkFlex acquisitionMotor = new SparkFlex(AcquisitionConstants.kAcquisitionMotorPort,
            MotorType.kBrushless);
    private final SparkLimitSwitch algaeSwitch = acquisitionMotor.getForwardLimitSwitch();
    public static Acquisition instance;

    private Acquisition() {
        SparkFlexConfig acquisitionConfig = new SparkFlexConfig();
        acquisitionConfig
                .idleMode(IdleMode.kBrake)
                .smartCurrentLimit(NeoMotorConstants.kMaxVortexCurrent)
                .inverted(true);
        this.acquisitionMotor.configure(acquisitionConfig, ResetMode.kResetSafeParameters,
                PersistMode.kPersistParameters);
    }

    public static Acquisition getInstance() {
        if (instance == null) {
            instance = new Acquisition();
        }
        return instance;
    }

    public void acquireAlgae() {
        this.acquisitionMotor.set(AcquisitionConstants.kAcquireAlgaeSpeed);
    }

    public void disposeAlgae() {
        this.acquisitionMotor.set(AcquisitionConstants.kDisposeAlgaeSpeed);
    }

    public void shootAlgae() {
        this.acquisitionMotor.set(AcquisitionConstants.kShootAlgaeSpeed);
    }

    public void stopAcquisition() {
        this.acquisitionMotor.stopMotor();
    }

    public boolean getAlgaeSwitch() {
        return this.algaeSwitch.isPressed();
    }
}
