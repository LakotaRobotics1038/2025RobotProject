package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.constants.ExtensionConstants.ExtensionSetpoints;
import frc.robot.subsystems.Extension;

public class SetDefaultExtensionCommand extends Command {
    private Extension extension = Extension.getInstance();

    public SetDefaultExtensionCommand() {
        super.addRequirements(extension);
    }

    @Override
    public void initialize() {
        extension.setDefaultCommand(
                new InstantCommand(() -> extension.setSetpoint(ExtensionSetpoints.Storage), extension));
    }

    @Override
    public void execute() {

    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void end(boolean isInterrupted) {

    }

}
