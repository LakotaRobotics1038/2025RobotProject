package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.ExtensionConstants.ExtensionSetpoints;
import frc.robot.subsystems.Extension;

public class SetExtensionPositionCommand extends Command {
    private Extension extension = Extension.getInstance();
    private ExtensionSetpoints setpoint;

    public SetExtensionPositionCommand(ExtensionSetpoints setpoint) {
        super.addRequirements(extension);
        this.setpoint = setpoint;
    }

    @Override
    public void initialize() {
        this.extension.setSetpoint(setpoint);
        this.extension.enable();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean isInterrupted) {
        this.extension.disable();
    }

}
