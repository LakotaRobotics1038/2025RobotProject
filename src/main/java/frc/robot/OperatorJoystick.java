package frc.robot;

import frc.robot.commands.MovePivotForwardsCommand;
import frc.robot.commands.movePivotBackwardsCommand;
import frc.robot.constants.IOConstants;
import frc.robot.libraries.XboxController1038;

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
        bButton.whileTrue(new MovePivotForwardsCommand());
        xButton.whileTrue(new movePivotBackwardsCommand());
    }
}
