package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.AcquireAlgaeCommand;
import frc.robot.commands.AcquireCoralCommand;
import frc.robot.commands.AcquireForL4Command;
import frc.robot.commands.DisposeAlgaeCommand;
import frc.robot.commands.DisposeCoral134Command;
import frc.robot.commands.DisposeCoral2Command;
import frc.robot.commands.SetAcquisitionPositionCommand;
import frc.robot.commands.SetAcquisitionPositionCommand.FinishActions;
import frc.robot.commands.SetExtensionPositionCommand;
import frc.robot.commands.SetShoulderPositionCommand;
import frc.robot.commands.SetWristPositionCommand;
import frc.robot.commands.ShootAlgaeCommand;
import frc.robot.constants.ExtensionConstants.ExtensionSetpoints;
import frc.robot.constants.IOConstants;
import frc.robot.constants.ShoulderConstants.ShoulderSetpoints;
import frc.robot.constants.WristConstants.WristSetpoints;
import frc.robot.subsystems.Extension;
import frc.robot.subsystems.Shoulder;
import frc.robot.subsystems.Wrist;
import frc.robot.utils.AcquisitionPositionSetpoint;

public class OperatorPanel extends GenericHID {
    private OperatorState operatorState;
    private boolean isDefaultEnabled;

    private final Extension extension = Extension.getInstance();
    private final Wrist wrist = Wrist.getInstance();
    private final Shoulder shoulder = Shoulder.getInstance();

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
    public final JoystickButton processorButton = new JoystickButton(this, IOConstants.kProcessorButtonNumber);
    public final JoystickButton coralPosScoringSwitch = new JoystickButton(this,
            IOConstants.kCoralPosScoringSwitchNumber);

    private OperatorPanel() {
        super(IOConstants.kOperatorPanelPort);
        this.operatorState = OperatorState.getInstance();
        operatorState.setLastInput(AcquisitionPositionSetpoint.Storage);
        operatorState.setScoringFlipped(coralPosScoringSwitch.getAsBoolean());

        // Acquire
        this.acquireButton.and(operatorState::isCoral134).whileTrue(new AcquireCoralCommand());
        this.acquireButton.and(operatorState::isAlgae).onTrue(new AcquireAlgaeCommand());
        this.acquireButton.and(operatorState::isCoral4).whileTrue(new AcquireForL4Command());

        // Dispose
        this.disposeButton.and(operatorState::isCoral134).whileTrue(new DisposeCoral134Command());
        this.disposeButton.and(operatorState::isCoral2).whileTrue(new DisposeCoral2Command());
        this.disposeButton.and(operatorState::isAlgae).whileTrue(new DisposeAlgaeCommand());
        this.disposeButton.and(operatorState::isBarge).whileTrue(new ShootAlgaeCommand());

        // Setpoints
        this.storageButton.toggleOnTrue(
                new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.Storage, FinishActions.NoFinish));
        this.storageButton.and(this::getIsDefaultsEnabled).onTrue(new InstantCommand(() -> enableDefaults()));
        this.bargeButton.toggleOnTrue(
                new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.Barge, FinishActions.NoFinish));

        // Operator State Updates
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
        this.algaeL23Button.and(this::getIsDefaultsEnabled).onTrue(new InstantCommand(() -> enableDefaults()));
        this.algaeL34Button
                .onTrue(new InstantCommand(() -> operatorState.setLastInput(AcquisitionPositionSetpoint.L34Algae)));
        this.algaeL34Button.and(this::getIsDefaultsEnabled).onTrue(new InstantCommand(() -> enableDefaults()));
        this.processorButton
                .onTrue(new InstantCommand(() -> operatorState.setLastInput(AcquisitionPositionSetpoint.Processor)));
        this.feederButton.onTrue(
                new InstantCommand(() -> operatorState.setLastInput(AcquisitionPositionSetpoint.FeederStation)));
        this.algaeL23Button.and(this::getIsDefaultsEnabled).onTrue(new InstantCommand(() -> enableDefaults()));
        this.coralPosScoringSwitch
                .onTrue(new InstantCommand(() -> operatorState.setScoringFlipped(true)))
                .onFalse(new InstantCommand(() -> operatorState.setScoringFlipped(false)));

        // Manual Control
        this.coralL1Button
                .and(operatorState::getIsManual)
                .onTrue(new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.L1Coral, FinishActions.NoFinish));
        this.coralL2Button
                .and(operatorState::getIsManual)
                .onTrue(new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.L2Coral, FinishActions.NoFinish));
        this.coralL3Button
                .and(operatorState::getIsManual)
                .onTrue(new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.L3Coral, FinishActions.NoFinish));
        this.coralL4Button
                .and(operatorState::getIsManual)
                .onTrue(new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.L4Coral, FinishActions.NoFinish));
        this.coralL4Button
                .and(operatorState::getIsManual)
                .onTrue(new PrintCommand("Running L4Command")
                        .andThen(new AcquireForL4Command()));
        this.algaeL23Button
                .and(operatorState::getIsManual)
                .onTrue(new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.L23Algae,
                        FinishActions.NoFinish));
        this.algaeL34Button
                .and(operatorState::getIsManual)
                .onTrue(new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.L34Algae,
                        FinishActions.NoFinish));
        this.processorButton
                .and(operatorState::getIsManual)
                .onTrue(new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.Processor,
                        FinishActions.NoFinish));
        this.feederButton
                .and(operatorState::getIsManual)
                .onTrue(new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.FeederStation,
                        FinishActions.NoFinish));
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
        isDefaultEnabled = true;
    }

    public void clearDefaults() {
        extension.removeDefaultCommand();
        wrist.removeDefaultCommand();
        shoulder.removeDefaultCommand();
    }

    public boolean getIsDefaultsEnabled() {
        return isDefaultEnabled;
    }
}
