package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;

public class DisposeCommand extends Command {
    private Acquisition acquisition = Acquisition.getInstance();

    public DisposeCommand() {
        super.addRequirements(acquisition);
    }

    @Override
    public void execute() {
        acquisition.dispose();
    }

    @Override
    public boolean isFinished() {
        return acquisition.getTopLaser() && acquisition.getBottomLaser();
    }

    @Override
    public void end(boolean interrupted) {
        acquisition.stopAcquisition();
    }
}
