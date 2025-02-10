package frc.robot.libraries;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.constants.IOConstants;

public class ControlPanel1038 extends GenericHID {

    public final JoystickButton acquireButton;
    public final JoystickButton disposeButton;
    public final JoystickButton coralL1Button;
    public final JoystickButton coralL2Button;
    public final JoystickButton coralL3Button;
    public final JoystickButton coralL4Button;
    public final JoystickButton algaeL23Button;
    public final JoystickButton algaeL34Button;
    public final JoystickButton processorButton;
    public final JoystickButton feederButton;
    public final JoystickButton coralPosScoringSwitch;
    public final JoystickButton storageButton;

    public ControlPanel1038(int port) {
        super(port);

        this.acquireButton = new JoystickButton(this, IOConstants.kAcquireButtonNumber);
        this.disposeButton = new JoystickButton(this, IOConstants.kDisposeButtonNumber);
        this.storageButton = new JoystickButton(this, IOConstants.kStorageButtonNumber);
        this.coralL1Button = new JoystickButton(this, IOConstants.kCoralL1ButtonNumber);
        this.coralL2Button = new JoystickButton(this, IOConstants.kCoralL2ButtonNumber);
        this.coralL3Button = new JoystickButton(this, IOConstants.kCoralL3ButtonNumber);
        this.coralL4Button = new JoystickButton(this, IOConstants.kCoralL4ButtonNumber);
        this.algaeL23Button = new JoystickButton(this, IOConstants.kAlgaeL23ButtonNumber);
        this.algaeL34Button = new JoystickButton(this, IOConstants.kAlgaeL34ButtonNumber);
        this.feederButton = new JoystickButton(this, IOConstants.kFeederButtonNumber);
        this.processorButton = new JoystickButton(this, IOConstants.kProcesssorButtonNumber);
        this.coralPosScoringSwitch = new JoystickButton(this, IOConstants.kCoralPosScoringSwitch);
    }

}
