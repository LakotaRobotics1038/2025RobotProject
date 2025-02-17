package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Acquisition;

public class AcquireCoralCommand extends Command {

    private Acquisition acquisition = Acquisition.getInstance();

    public AcquireCoralCommand() {
        this.addRequirements(acquisition);
    }

    @Override
    public void execute() {
        acquisition.acquireCoral();
    }

    @Override
    public boolean isFinished() {
        return acquisition.getBottomLaser();
    }

    @Override
    public void end(boolean interrupted) {
        acquisition.stopAcquisition();
    }

}
