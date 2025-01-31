package frc.robot.subsystems;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLimitSwitch;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.AcquisitionConstants;
import frc.robot.constants.NeoMotorConstants;

public class Acquisition extends SubsystemBase {
    private DigitalInput topLaser;
    private DigitalInput bottomLaser;
    private SparkFlex acquisitionMotor;
    private SparkLimitSwitch algaeSwitch;
    public static Acquisition instance;

    private Acquisition() {
        topLaser = new DigitalInput(AcquisitionConstants.kTopLaserPort);
        bottomLaser = new DigitalInput(AcquisitionConstants.kBottomLaserPort);
        acquisitionMotor = new SparkFlex(AcquisitionConstants.kAcquisitionMotorPort, MotorType.kBrushless);
        algaeSwitch = acquisitionMotor.getForwardLimitSwitch();

        SparkFlexConfig acquisitionConfig = new SparkFlexConfig();
        acquisitionConfig
                .idleMode(IdleMode.kBrake)
                .smartCurrentLimit(NeoMotorConstants.kMaxVortexCurrent)
                .inverted(false);
        acquisitionMotor.configure(acquisitionConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    public static Acquisition getInstance() {
        if (instance == null) {
            instance = new Acquisition();
        }
        return instance;
    }

    public void acquire() {
        acquisitionMotor.set(AcquisitionConstants.kAcquireSpeed);
    }

    public void dispose() {
        acquisitionMotor.set(AcquisitionConstants.kDisposeSpeed);
    }

    public void stopAcquisition() {
        acquisitionMotor.stopMotor();
    }

    public boolean getTopLaser() {
        return topLaser.get();
    }

    public boolean getBottomLaser() {
        return bottomLaser.get();
    }

    public boolean getAlgaeSwitch() {
        return algaeSwitch.isPressed();
    }
}
