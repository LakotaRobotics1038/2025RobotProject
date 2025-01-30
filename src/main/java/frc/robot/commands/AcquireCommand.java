package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Acquisition;
import frc.robot.subsystems.Shoulder;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Wrist;

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
        if (acquisition.getAlgaeSwitch()) {
            return true;
        } else if (acquisition.getTopLaser && acquisition.getBottomLaser) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void end() {
        acquisition.stopAcquisition();
    }

}
