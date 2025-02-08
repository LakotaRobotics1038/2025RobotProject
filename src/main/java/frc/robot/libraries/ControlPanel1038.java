package frc.robot.libraries;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.constants.IOConstants;

public class ControlPanel1038 extends GenericHID {

    public final JoystickButton storageButton;
    public final JoystickButton coralL1Button;
    public final JoystickButton acquireButton;

    public ControlPanel1038(int port) {
        super(port);

        this.acquireButton = new JoystickButton(this, IOConstants.kAcquireButtonNumber);
        this.storageButton = new JoystickButton(this, IOConstants.kStorageButtonNumber);
        this.coralL1Button = new JoystickButton(this, IOConstants.kCoralL1ButtonNumber);
    }

}
