package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkLimitSwitch;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import frc.robot.constants.ClimbConstants;

public class Climb {

    private SparkMax climbMotor = new SparkMax(ClimbConstants.climbMotorPort, MotorType.kBrushless);
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

    }

    /**
     * Mostly empty javadoc
     *
     * @return Not implemented yet
     */
    public boolean getSwitch() {
        return false;
    }

    /**
     * Empty javadoc
     */
    public void runClimbUp() {

    }

    /**
     * Empty javadoc
     */
    public void runClimbDown() {

    }

    /**
     * Empty javadoc
     */
    public void stopClimb() {

    }

}
