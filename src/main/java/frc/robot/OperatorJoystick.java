package frc.robot;

import frc.robot.libraries.XboxController1038;
import frc.robot.commands.AcquireCommand;
import frc.robot.commands.DisposeCommand;
import frc.robot.constants.IOConstants;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class OperatorJoystick extends XboxController1038 {
    // Singleton Setup
    private static OperatorJoystick instance;

    public static OperatorJoystick getInstance() {
        if (instance == null) {
            System.out.println("Creating a new Operator");
            instance = new OperatorJoystick();
        }
        return instance;
    }

    private OperatorJoystick() {
        super(IOConstants.kOperatorControllerPort);
        aButton.whileTrue(new AcquireCommand());
        bButton.whileTrue(new DisposeCommand(3));
    }
}
