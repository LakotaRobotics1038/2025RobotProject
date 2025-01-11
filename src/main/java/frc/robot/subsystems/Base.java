package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.BaseConstants;
import frc.robot.constants.NeoMotorConstants;

public class Base extends SubsystemBase {
    private static SparkMax basePivotMotor = new SparkMax(BaseConstants.kBaseCanId, MotorType.kBrushless);

    private static Base instance;

    public static Base getInstance() {
        if (instance == null) {
            instance = new Base();
        }
        return instance;
    }

    private Base() {
        SparkMaxConfig baseConfig = new SparkMaxConfig();
        baseConfig.idleMode(IdleMode.kBrake)
                .smartCurrentLimit(NeoMotorConstants.kMaxNeo550Current);

        basePivotMotor.configure(baseConfig, ResetMode.kResetSafeParameters,
                PersistMode.kPersistParameters);
    }

    public void movePivotForwards() {
        basePivotMotor.set(.25);
    }

    public void movePivotBackwards() {
        basePivotMotor.set(-.25);
    }

    public void stopPivot() {
        basePivotMotor.stopMotor();
    }
}
