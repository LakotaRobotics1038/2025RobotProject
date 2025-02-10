package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.commands.AcquireCommand;
import frc.robot.constants.IOConstants;
import frc.robot.libraries.ControlPanel1038;

public class OperatorPanel extends ControlPanel1038 {
    // Singleton Setup
    private static OperatorPanel instance;
    private ControlPanelSetpoint armSystemTarget;

    private OperatorPanel() {
        super(IOConstants.kOperatorPanelPort);
        this.armSystemTarget = ControlPanelSetpoint.getInstance();

        this.acquireButton.whileTrue(new AcquireCommand());
        // this.disposeButton.whileTrue(new DisposeCommand());
        // this.storageButton.onTrue(new
        // AcquisitionPositionCommand(AcquisitionPositionSetpoint.Storage));
        this.coralL1Button.onTrue(this.setControlPanelSetpoint(AcquisitionPositionSetpoint.L1Coral));
        this.coralL2Button.onTrue(this.setControlPanelSetpoint(AcquisitionPositionSetpoint.L2Coral));
        this.coralL3Button.onTrue(this.setControlPanelSetpoint(AcquisitionPositionSetpoint.L3Coral));
        this.coralL4Button.onTrue(this.setControlPanelSetpoint(AcquisitionPositionSetpoint.L4Coral));
        this.algaeL23Button.onTrue(this.setControlPanelSetpoint(AcquisitionPositionSetpoint.L23Algae));
        this.algaeL34Button.onTrue(this.setControlPanelSetpoint(AcquisitionPositionSetpoint.L34Algae));
        this.processorButton.onTrue(this.setControlPanelSetpoint(AcquisitionPositionSetpoint.Processor));
        this.feederButton.onTrue(this.setControlPanelSetpoint(AcquisitionPositionSetpoint.FeederStation));

        this.coralPosScoringSwitch.onChange(this.updateIsLeft());

    }

    public Command setControlPanelSetpoint(AcquisitionPositionSetpoint acqPosSetpoint) {
        return Commands.runOnce(new Runnable() {
            @Override

            public void run() {
                armSystemTarget.setLastInput(acqPosSetpoint);
            }
        });
    }

    public Command updateIsLeft() {
        return Commands.runOnce(new Runnable() {
            @Override

            public void run() {
                armSystemTarget.setIsLeft(coralPosScoringSwitch.getAsBoolean());
            }
        });
    }

    public static OperatorPanel getInstance() {
        if (instance == null) {
            System.out.println("Creating a new Operator");
            instance = new OperatorPanel();
        }
        return instance;
    }
}
