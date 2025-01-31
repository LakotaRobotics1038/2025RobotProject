package frc.robot.subsystems;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLimitSwitch;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.AcquisitionConstants;
import frc.robot.constants.NeoMotorConstants;

public class Acquisition extends SubsystemBase {
    private final DigitalInput topLaser = new DigitalInput(AcquisitionConstants.kTopLaserPort);
    private final DigitalInput bottomLaser = new DigitalInput(AcquisitionConstants.kBottomLaserPort);
    private final SparkFlex acquisitionMotor = new SparkFlex(AcquisitionConstants.kAcquisitionMotorPort,
            MotorType.kBrushless);
    private final SparkLimitSwitch algaeSwitch = acquisitionMotor.getForwardLimitSwitch();
    public static Acquisition instance;

    private Acquisition() {
        SparkFlexConfig acquisitionConfig = new SparkFlexConfig();
        acquisitionConfig
                .idleMode(IdleMode.kBrake)
                .smartCurrentLimit(NeoMotorConstants.kMaxVortexCurrent)
                .inverted(false);
        this.acquisitionMotor.configure(acquisitionConfig, ResetMode.kResetSafeParameters,
                PersistMode.kPersistParameters);
    }

    public static Acquisition getInstance() {
        if (instance == null) {
            instance = new Acquisition();
        }
        return instance;
    }

    public void acquire() {
        this.acquisitionMotor.set(AcquisitionConstants.kAcquireSpeed);
    }

    public void dispose() {
        this.acquisitionMotor.set(AcquisitionConstants.kDisposeSpeed);
    }

    public void stopAcquisition() {
        this.acquisitionMotor.stopMotor();
    }

    public boolean getTopLaser() {
        return this.topLaser.get();
    }

    public boolean getBottomLaser() {
        return this.bottomLaser.get();
    }

    public boolean getAlgaeSwitch() {
        return this.algaeSwitch.isPressed();
    }
}
