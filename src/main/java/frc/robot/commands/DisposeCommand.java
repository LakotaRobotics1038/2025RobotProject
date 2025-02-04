package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Acquisition;

public class DisposeCommand extends Command {
    private Acquisition acquisition = Acquisition.getInstance();
    private double secondsToDispose = 0.0;
    private Timer timer = new Timer();

    public DisposeCommand() {
        super.addRequirements(acquisition);
    }

    public DisposeCommand(double secondsToDispose) {
        this.secondsToDispose = secondsToDispose;

    }

    @Override
    public void execute() {
        timer.restart();
        acquisition.dispose();
    }

    @Override
    public boolean isFinished() {
        if (!(acquisition.getTopLaser() || acquisition.getBottomLaser())) {
            if (!(acquisition.getAlgaeSwitch())) {
                return secondsToDispose == 0.0 ? false : timer.get() >= secondsToDispose;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public void end(boolean interrupted) {
        acquisition.stopAcquisition();
    }
}
