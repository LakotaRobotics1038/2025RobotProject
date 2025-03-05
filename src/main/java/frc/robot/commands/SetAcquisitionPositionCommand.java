package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.ExtensionConstants.ExtensionSetpoints;
import frc.robot.constants.ExtensionConstants;
import frc.robot.constants.ShoulderConstants;
import frc.robot.subsystems.Extension;
import frc.robot.subsystems.Shoulder;
import frc.robot.subsystems.Wrist;
import frc.robot.utils.AcquisitionPositionSetpoint;

public class SetAcquisitionPositionCommand extends Command {
    private Shoulder shoulder = Shoulder.getInstance();
    private Wrist wrist = Wrist.getInstance();
    private Extension extension = Extension.getInstance();
    private AcquisitionPositionSetpoint acquisitionPositionSetpoint;
    private boolean retractExtension;

    public SetAcquisitionPositionCommand(AcquisitionPositionSetpoint acquisitionPositionSetpoint) {
        addRequirements(shoulder, wrist, extension);
        this.acquisitionPositionSetpoint = acquisitionPositionSetpoint;
    }

    public void initialize() {
        wrist.enable();
        shoulder.enable();
        extension.enable();

        if (extension.getPosition() >= ExtensionConstants.kExtensionMaxMove) {
            retractExtension = true;
            extension.setSetpoint(ExtensionSetpoints.Storage);
        }
    }

    public void execute() {
        if (this.retractExtension) {
            if (extension.getPosition() <= ExtensionConstants.kExtensionMaxMove) {
                this.retractExtension = false;
            }
        } else {
            extension.setSetpoint(this.acquisitionPositionSetpoint.getExtensionSetpoint());
            shoulder.setSetpoint(this.acquisitionPositionSetpoint.getShoulderSetpoint());
            wrist.setSetpoint(this.acquisitionPositionSetpoint.getWristSetpoint());
        }
    }

    public boolean isFinished() {
        return false;
    }

    public void end(boolean interrupted) {

    }
}
