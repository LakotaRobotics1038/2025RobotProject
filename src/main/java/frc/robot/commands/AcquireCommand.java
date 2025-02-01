package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Acquisition;

public class AcquireCommand extends Command {

    private Acquisition acquisition = Acquisition.getInstance();

    public AcquireCommand() {
        this.addRequirements(acquisition);
    }

    @Override
    public void execute() {
        acquisition.acquire();
    }

    @Override
    public boolean isFinished() {
        if (acquisition.getAlgaeSwitch() || acquisition.getBottomLaser()) {
            return true;
        }
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        acquisition.stopAcquisition();
    }

}
