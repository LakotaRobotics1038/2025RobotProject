package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Extension;
import frc.robot.subsystems.Shoulder;
import frc.robot.subsystems.Wrist;
import frc.robot.utils.AcquisitionPositionSetpoint;

public class SetAcquisitionPositionCommand extends Command {
    private Shoulder shoulder = Shoulder.getInstance();
    private Wrist wrist = Wrist.getInstance();
    private Extension extension = Extension.getInstance();
    private AcquisitionPositionSetpoint acquisitionPositionSetpoint;

    public SetAcquisitionPositionCommand(AcquisitionPositionSetpoint acquisitionPositionSetpoint) {
        addRequirements(shoulder, wrist, extension);
        this.acquisitionPositionSetpoint = acquisitionPositionSetpoint;
    }

    public void initialize() {
        wrist.enable();
        shoulder.enable();
        extension.enable();
        shoulder.setSetpoint(this.acquisitionPositionSetpoint.getShoulderSetpoint());
        wrist.setSetpoint(this.acquisitionPositionSetpoint.getWristSetpoint());
        extension.setSetpoint(this.acquisitionPositionSetpoint.getExtensionSetpoint());
    }

    public boolean isFinished() {
        return wrist.onTarget() && shoulder.onTarget() && extension.onTarget();
    }

    public void end(boolean interrupted) {
        wrist.disable();
        shoulder.disable();
        extension.disable();
    }
}
