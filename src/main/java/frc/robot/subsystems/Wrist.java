package frc.robot.subsystems;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.NeoMotorConstants;
import frc.robot.constants.WristConstants;

public class Wrist extends SubsystemBase {

    private static SparkFlex wristMotor;
    private static AbsoluteEncoder wristEncoder;
    private static PIDController wristController;
    private static Wrist instance;

    private Wrist() {
        wristMotor = new SparkFlex(WristConstants.kWristCanId, MotorType.kBrushless);
        SparkFlexConfig wristConfig = new SparkFlexConfig();
        wristConfig
                .idleMode(IdleMode.kBrake)
                .smartCurrentLimit(NeoMotorConstants.kMaxVortexCurrent)
                .inverted(false);
        wristMotor.configure(wristConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        wristEncoder = wristMotor.getAbsoluteEncoder();
    }

    public static Wrist getInstance() {
        if (instance == null) {
            instance = new Wrist();
        }
        return instance;
    }

    public void useOutput(double output, double setpoint) {

    }

}
