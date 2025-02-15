package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.OperatorState.ScoringSide;
import frc.robot.commands.AcquireCommand;
import frc.robot.commands.DisposeCommand;
import frc.robot.commands.SetAcquisitionPositionCommand;
import frc.robot.commands.SetExtensionPositionCommand;
import frc.robot.commands.SetShoulderPositionCommand;
import frc.robot.commands.SetWristPositionCommand;
import frc.robot.constants.IOConstants;
import frc.robot.constants.ExtensionConstants.ExtensionSetpoints;
import frc.robot.constants.ShoulderConstants.ShoulderSetpoints;
import frc.robot.constants.WristConstants.WristSetpoints;
import frc.robot.subsystems.Extension;
import frc.robot.subsystems.Shoulder;
import frc.robot.subsystems.Wrist;
import frc.robot.utils.AcquisitionPositionSetpoint;

public class OperatorPanel extends GenericHID {
    private OperatorState operatorState;

    private Extension extension;
    private Wrist wrist;
    private Shoulder shoulder;

    public final JoystickButton acquireButton = new JoystickButton(this, IOConstants.kAcquireButtonNumber);
    public final JoystickButton disposeButton = new JoystickButton(this, IOConstants.kDisposeButtonNumber);
    public final JoystickButton storageButton = new JoystickButton(this, IOConstants.kStorageButtonNumber);
    public final JoystickButton bargeButton = new JoystickButton(this, IOConstants.kBargeButtonNumber);
    public final JoystickButton coralL1Button = new JoystickButton(this, IOConstants.kCoralL1ButtonNumber);
    public final JoystickButton coralL2Button = new JoystickButton(this, IOConstants.kCoralL2ButtonNumber);
    public final JoystickButton coralL3Button = new JoystickButton(this, IOConstants.kCoralL3ButtonNumber);
    public final JoystickButton coralL4Button = new JoystickButton(this, IOConstants.kCoralL4ButtonNumber);
    public final JoystickButton algaeL23Button = new JoystickButton(this, IOConstants.kAlgaeL23ButtonNumber);
    public final JoystickButton algaeL34Button = new JoystickButton(this, IOConstants.kAlgaeL34ButtonNumber);
    public final JoystickButton feederButton = new JoystickButton(this, IOConstants.kFeederButtonNumber);
    public final JoystickButton processorButton = new JoystickButton(this, IOConstants.kProcesssorButtonNumber);
    public final JoystickButton coralPosScoringSwitch = new JoystickButton(this,
            IOConstants.kCoralPosScoringSwitchNumber);

    private OperatorPanel() {
        super(IOConstants.kOperatorPanelPort);
        this.operatorState = OperatorState.getInstance();
        operatorState.setLastInput(AcquisitionPositionSetpoint.Storage);
        operatorState.setScoringSide(coralPosScoringSwitch.getAsBoolean() ? ScoringSide.LEFT : ScoringSide.RIGHT);

        this.acquireButton.whileTrue(new AcquireCommand());
        this.disposeButton.whileTrue(new DisposeCommand());
        this.storageButton.toggleOnTrue(new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.Storage));
        this.bargeButton.toggleOnTrue(new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.Barge));
        this.coralL1Button.onTrue(
                new InstantCommand(() -> operatorState.setLastInput(AcquisitionPositionSetpoint.L1Coral)));
        this.coralL2Button
                .onTrue(new InstantCommand(() -> operatorState.setLastInput(AcquisitionPositionSetpoint.L2Coral)));
        this.coralL3Button
                .onTrue(new InstantCommand(() -> operatorState.setLastInput(AcquisitionPositionSetpoint.L3Coral)));
        this.coralL4Button
                .onTrue(new InstantCommand(() -> operatorState.setLastInput(AcquisitionPositionSetpoint.L4Coral)));
        this.algaeL23Button
                .onTrue(new InstantCommand(() -> operatorState.setLastInput(AcquisitionPositionSetpoint.L23Algae)));
        this.algaeL34Button
                .onTrue(new InstantCommand(() -> operatorState.setLastInput(AcquisitionPositionSetpoint.L34Algae)));
        this.processorButton
                .onTrue(new InstantCommand(() -> operatorState.setLastInput(AcquisitionPositionSetpoint.Processor)));
        this.feederButton.onTrue(
                new InstantCommand(() -> operatorState.setLastInput(AcquisitionPositionSetpoint.FeederStation)));
        this.coralPosScoringSwitch
                .onTrue(new InstantCommand(() -> operatorState.setScoringSide(ScoringSide.LEFT)))
                .onFalse(new InstantCommand(() -> operatorState.setScoringSide(ScoringSide.RIGHT)));
    }

    // Singleton Setup
    private static OperatorPanel instance;

    public static OperatorPanel getInstance() {
        if (instance == null) {
            System.out.println("Creating a new Operator");
            instance = new OperatorPanel();
        }
        return instance;
    }

    public void enableDefaults() {
        extension.setDefaultCommand(new SetExtensionPositionCommand(ExtensionSetpoints.Storage));
        wrist.setDefaultCommand(new SetWristPositionCommand(WristSetpoints.Storage));
        shoulder.setDefaultCommand(new SetShoulderPositionCommand(ShoulderSetpoints.Storage));
    }

    public void clearDefaults() {
        extension.removeDefaultCommand();
        wrist.removeDefaultCommand();
        shoulder.removeDefaultCommand();
    }
}
