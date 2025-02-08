package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
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
        // this.storageButton.onTrue(new
        // AcquisitionPositionCommand(AcquisitionPositionSetpoint.Storage));
        this.coralL1Button.onTrue(setControlPanelSetpoint(AcquisitionPositionSetpoint.L1Coral));
    }

    public Command setControlPanelSetpoint(AcquisitionPositionSetpoint acqPosSetpoint) {
        return Commands.runOnce(new Runnable() {
            @Override

            public void run() {
                armSystemTarget.setLastInput(acqPosSetpoint);
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
