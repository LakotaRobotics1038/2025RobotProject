package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkLimitSwitch;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.ClimbConstants;
import frc.robot.constants.NeoMotorConstants;

public class Climb extends SubsystemBase {

    private SparkMax climbMotor = new SparkMax(ClimbConstants.kClimbMotorPort, MotorType.kBrushless);
    private RelativeEncoder climbEncoder = climbMotor.getEncoder();
    private SparkLimitSwitch climbLimitSwitch = climbMotor.getForwardLimitSwitch();

    private static Climb instance;

    /**
     * Creates and instance of the Climb subsystem if it does not already exist.
     *
     * @return An instance of the Climb subsystem
     */
    public static Climb getInstance() {
        if (instance == null) {
            instance = new Climb();
        }
        return instance;
    }

    private Climb() {
        SparkMaxConfig climbConfig = new SparkMaxConfig();

        climbConfig
                .idleMode(IdleMode.kBrake)
                .smartCurrentLimit(NeoMotorConstants.kMaxNeoCurrent)
                .inverted(false);

        climbMotor.configure(climbConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        climbEncoder.setPosition(0);
    }

    /**
     * Gets the position of the climbLimitSwitch
     *
     * @return A boolean representing whether the climbLimitSwitch is pressed or not
     */
    public boolean getSwitch() {
        return climbLimitSwitch.isPressed();
    }

    /**
     * Runs the climbMotor at a constant speed up
     */
    public void runClimbUp() {
        climbMotor.set(ClimbConstants.kClimbUpSpeed);
    }

    /**
     * Runs the climbMotor at a constant speed down
     */
    public void runClimbDown() {
        climbMotor.set(ClimbConstants.kClimbDownSpeed);
    }

    /**
     * Stops the climb motor
     */
    public void stopClimb() {
        climbMotor.stopMotor();
    }

}
