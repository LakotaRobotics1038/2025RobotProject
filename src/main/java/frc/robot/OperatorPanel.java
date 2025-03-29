package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.AcquireAlgaeCommand;
import frc.robot.commands.DisposeAlgaeCommand;
import frc.robot.commands.SetAcquisitionPositionCommand;
import frc.robot.commands.SetAcquisitionPositionCommand.FinishActions;
import frc.robot.commands.ShootAlgaeCommand;
import frc.robot.constants.IOConstants;
import frc.robot.subsystems.Shoulder;
import frc.robot.utils.AcquisitionPositionSetpoint;

public class OperatorPanel extends GenericHID {
    private OperatorState operatorState;
    private boolean isDefaultEnabled;

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
        this.acquireButton.onTrue(new AcquireAlgaeCommand());

        // Dispose
        this.disposeButton.and(operatorState::isAlgae).whileTrue(new DisposeAlgaeCommand());
        this.disposeButton.and(operatorState::isBarge).whileTrue(new ShootAlgaeCommand());

        // Setpoints
        this.storageButton.toggleOnTrue(
                new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.Storage, FinishActions.NoFinish));
        this.storageButton.and(this::getDefaultsDisabled).onTrue(new InstantCommand(() -> enableDefaults()));

        this.feederButton
                .onTrue(new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.StartingConfig,
                        FinishActions.NoFinish));

        this.coralL1Button.and(this::getDefaultsDisabled).onTrue(new InstantCommand(() -> enableDefaults()));
        this.bargeButton.toggleOnTrue(
                new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.Barge, FinishActions.NoFinish));
        this.bargeButton
                .onTrue(new InstantCommand(() -> operatorState.setLastInput(AcquisitionPositionSetpoint.Barge)));
        // Operator State Updates
        this.algaeL23Button
                .onTrue(new InstantCommand(() -> operatorState.setLastInput(AcquisitionPositionSetpoint.L23Algae)));
        this.algaeL23Button.and(this::getDefaultsDisabled).onTrue(new InstantCommand(() -> enableDefaults()));
        this.algaeL34Button
                .onTrue(new InstantCommand(() -> operatorState.setLastInput(AcquisitionPositionSetpoint.L34Algae)));
        this.algaeL34Button.and(this::getDefaultsDisabled).onTrue(new InstantCommand(() -> enableDefaults()));
        this.processorButton
                .onTrue(new InstantCommand(() -> operatorState.setLastInput(AcquisitionPositionSetpoint.Processor)));
        this.algaeL23Button.and(this::getDefaultsDisabled).onTrue(new InstantCommand(() -> enableDefaults()));

        // Manual Control
        this.coralL1Button.and(processorButton).onTrue(new InstantCommand(operatorState::toggleIsManual));

        this.coralL1Button
                .and(operatorState::getIsManual)
                .onTrue(new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.GroundAlgae,
                        FinishActions.NoFinish));

        this.coralL2Button
                .and(operatorState::getIsManual)
                .onTrue(new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.ZeroExtend,
                        FinishActions.NoFinish));

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
                .and(operatorState::isNotGroundAlgae)
                .onTrue(new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.Processor,
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
        shoulder.setDefaultCommand(
                new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.Storage, FinishActions.NoFinish));
        isDefaultEnabled = true;
    }

    public void clearDefaults() {
        shoulder.removeDefaultCommand();
        isDefaultEnabled = false;
    }

    public boolean getDefaultsDisabled() {
        return !isDefaultEnabled;
    }
}
