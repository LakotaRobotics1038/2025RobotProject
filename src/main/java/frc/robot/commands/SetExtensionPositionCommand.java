package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
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
        extension.setDefaultCommand(
                new InstantCommand(() -> extension.setSetpoint(setpoint), extension));
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void end(boolean isInterrupted) {

    }

}
