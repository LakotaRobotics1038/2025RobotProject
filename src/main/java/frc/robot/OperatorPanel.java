package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.AcquireAlgaeCommand;
import frc.robot.commands.DisposeAlgaeCommand;
import frc.robot.commands.SetAcquisitionPositionCommand;
import frc.robot.commands.SetAcquisitionPositionEscapeCommand;
import frc.robot.commands.SetAcquisitionPositionCommand.FinishActions;
import frc.robot.commands.SetAcquisitionPositionStartingConfigCommand;
import frc.robot.commands.ShootAlgaeCommand;
import frc.robot.constants.IOConstants;
import frc.robot.subsystems.Shoulder;
import frc.robot.subsystems.Wrist;
import frc.robot.utils.AcquisitionPositionSetpoint;

public class OperatorPanel extends GenericHID {
    private OperatorState operatorState;
    private boolean isDefaultEnabled;

    private final Shoulder shoulder = Shoulder.getInstance();
    private final Wrist wrist = Wrist.getInstance();

    public final JoystickButton acquireButton = new JoystickButton(this, IOConstants.kAcquireButtonNumber);
    public final JoystickButton disposeButton = new JoystickButton(this, IOConstants.kDisposeButtonNumber);
    public final JoystickButton storageButton = new JoystickButton(this, IOConstants.kStorageButtonNumber);
    public final JoystickButton bargeButton = new JoystickButton(this, IOConstants.kBargeButtonNumber);
    public final JoystickButton groundAlgaeButton = new JoystickButton(this, IOConstants.kCoralL1ButtonNumber);
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
        this.storageButton
                .and(wrist::isNegative)
                .toggleOnTrue(
                        new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.Storage, FinishActions.NoFinish)
                                .alongWith(new InstantCommand(
                                        () -> operatorState.setLastInput(AcquisitionPositionSetpoint.Storage))));
        this.storageButton
                .and(wrist::isNegative)
                .and(this::getDefaultsDisabled)
                .onTrue(new InstantCommand(() -> enableDefaults()));

        this.feederButton
                .and(wrist::isNegative)
                .onTrue(new SetAcquisitionPositionStartingConfigCommand(
                        SetAcquisitionPositionStartingConfigCommand.FinishActions.NoFinish)
                        .alongWith(new InstantCommand(
                                () -> operatorState.setLastInput(AcquisitionPositionSetpoint.StartingConfig))));
        this.feederButton
                .and(new Trigger(wrist::isNegative).negate())
                .onTrue(new SetAcquisitionPositionEscapeCommand(
                        SetAcquisitionPositionEscapeCommand.FinishActions.NoFinish)
                        .alongWith(new InstantCommand(
                                () -> operatorState.setLastInput(AcquisitionPositionSetpoint.Storage))));

        this.bargeButton
                .and(wrist::isNegative)
                .toggleOnTrue(
                        new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.Barge, FinishActions.NoFinish)
                                .alongWith(new InstantCommand(
                                        () -> operatorState.setLastInput(AcquisitionPositionSetpoint.Barge))));

        this.groundAlgaeButton
                .and(wrist::isNegative)
                .onTrue(new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.GroundAlgae,
                        FinishActions.NoFinish)
                        .alongWith(new InstantCommand(
                                () -> operatorState.setLastInput(AcquisitionPositionSetpoint.GroundAlgae))));
        this.groundAlgaeButton
                .and(wrist::isNegative)
                .and(this::getDefaultsDisabled)
                .onTrue(new InstantCommand(() -> enableDefaults()));

        this.algaeL23Button
                .and(wrist::isNegative)
                .onTrue(new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.L23Algae,
                        FinishActions.NoFinish)
                        .alongWith(new InstantCommand(
                                () -> operatorState.setLastInput(AcquisitionPositionSetpoint.L23Algae))));
        this.algaeL23Button
                .and(wrist::isNegative)
                .and(this::getDefaultsDisabled)
                .onTrue(new InstantCommand(() -> enableDefaults()));

        this.algaeL34Button
                .and(wrist::isNegative)
                .onTrue(new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.L34Algae,
                        FinishActions.NoFinish)
                        .alongWith(new InstantCommand(
                                () -> operatorState.setLastInput(AcquisitionPositionSetpoint.L34Algae))));
        this.algaeL34Button
                .and(wrist::isNegative)
                .and(this::getDefaultsDisabled)
                .onTrue(new InstantCommand(() -> enableDefaults()));

        this.processorButton
                .and(wrist::isNegative)
                .onTrue(new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.Processor,
                        FinishActions.NoFinish)
                        .alongWith(new InstantCommand(
                                () -> operatorState.setLastInput(AcquisitionPositionSetpoint.Processor))));
        this.processorButton
                .and(wrist::isNegative)
                .and(this::getDefaultsDisabled)
                .onTrue(new InstantCommand(() -> enableDefaults()));
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
