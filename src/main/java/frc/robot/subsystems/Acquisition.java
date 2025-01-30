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
    private static DigitalInput topLaser = new DigitalInput(AcquisitionConstants.TOP_LASER_CHANNEL);
    private static DigitalInput bottomLaser = new DigitalInput(AcquisitionConstants.BOTTOM_LASER_CHANNEL);
    private static SparkFlex acquisitionMotor = new SparkFlex(AcquisitionConstants.MOTOR_DEVICE_ID,
            MotorType.kBrushless);
    private static SparkLimitSwitch algaeSwitch = acquisitionMotor.getReverseLimitSwitch();
    public static Acquisition instance;

    private Acquisition() {
        SparkFlexConfig acquisitionConfig = new SparkFlexConfig();
        acquisitionConfig.idleMode(IdleMode.kBrake).smartCurrentLimit(NeoMotorConstants.kMaxNeoCurrent);
        acquisitionMotor.configure(acquisitionConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    public static Acquisition getInstance() {
        if (instance == null) {
            instance = new Acquisition();
        }
        return instance;
    }

    public void acquire() {
        acquisitionMotor.set(AcquisitionConstants.ACQUIRE_SPEED);
    }

    public void dispose() {
        acquisitionMotor.set(AcquisitionConstants.DISPOSE_SPEED);
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
