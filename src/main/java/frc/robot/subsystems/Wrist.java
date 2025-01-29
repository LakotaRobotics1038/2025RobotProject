package frc.robot.subsystems;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.WristConstants;

public class Wrist extends SubsystemBase {

    private SparkFlex wrist;
    private AbsoluteEncoder wristEncoder;
    private PIDController wristController;

    private Wrist instance;

    private Wrist() {
        wrist = new SparkFlex(WristConstants.kWristCanId, MotorType.kBrushless);
        // wristEncoder = new wristEncoder();

    }

    public Wrist getInstance() {
        if (instance == null) {
            instance = new Wrist();
        }
        return instance;
    }

    public void useOutput(double output, double setpoint) {

    }

}
