package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Acquisition;

public class AcquireAlgaeCommand extends Command {

    private Acquisition acquisition = Acquisition.getInstance();

    public AcquireAlgaeCommand() {
        this.addRequirements(acquisition);
    }

    @Override
    public void execute() {
        acquisition.acquireAlgae();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        acquisition.stopAcquisition();
    }

}
