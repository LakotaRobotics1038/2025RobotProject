package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Acquisition;

public class AcquireForL4Command extends Command {

    private Acquisition acquisition = Acquisition.getInstance();

    public AcquireForL4Command() {
        this.addRequirements(acquisition);
    }

    @Override
    public void execute() {
        acquisition.acquireCoralL4();
    }

    @Override
    public boolean isFinished() {
        return !acquisition.getTopLaser();
    }

    @Override
    public void end(boolean interrupted) {
        acquisition.stopAcquisition();
    }

}
