package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.ExtensionConstants.ExtensionSetpoints;
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
        if (!extension.isLimitSwitchPressed()
                && this.shoulder.getPosition() > ShoulderConstants.kMaxExtendedShoulderAngle) {
            extension.setSetpoint(ExtensionSetpoints.Zero);
        }
        this.retractExtension = true;
    }

    public void execute() {
        if (this.retractExtension && (extension.isLimitSwitchPressed()
                || this.shoulder.getPosition() > ShoulderConstants.kMaxExtendedShoulderAngle)) {
            shoulder.setSetpoint(this.acquisitionPositionSetpoint.getShoulderSetpoint());
            wrist.setSetpoint(this.acquisitionPositionSetpoint.getWristSetpoint());
            extension.setSetpoint(this.acquisitionPositionSetpoint.getExtensionSetpoint());
            this.retractExtension = false;
        }
    }

    public boolean isFinished() {
        return false;
    }

    public void end(boolean interrupted) {

    }
}
