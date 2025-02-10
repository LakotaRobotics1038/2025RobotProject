package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Acquisition;

public class DisposeCommand extends Command {
    private Acquisition acquisition = Acquisition.getInstance();
    private double secondsToDispose;
    private Timer timer = new Timer();

    public DisposeCommand() {
        this(0.0);
    }

    public DisposeCommand(double secondsToDispose) {
        this.secondsToDispose = secondsToDispose;
        super.addRequirements(acquisition);
    }

    @Override
    public void execute() {
        timer.restart();
        acquisition.dispose();
    }

    @Override
    public boolean isFinished() {
        // if neither acquisition laser returns true, move on to checking the algae
        // switch. command is finished if all three sensors return false.

        if (!(acquisition.getTopLaser() || acquisition.getBottomLaser())) {
            if (!(acquisition.getAlgaeSwitch())) {
                return secondsToDispose == 0.0 ? false : timer.get() >= secondsToDispose;
            }
        }

        return false;
    }

    @Override
    public void end(boolean interrupted) {
        acquisition.stopAcquisition();
    }
}
