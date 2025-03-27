package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Acquisition;

public class DisposeAlgaeCommand extends Command {
    private Acquisition acquisition = Acquisition.getInstance();
    private double secondsToDispose;
    private Timer timer = new Timer();

    public DisposeAlgaeCommand() {
        this(0.0);
    }

    public DisposeAlgaeCommand(double secondsToDispose) {
        this.secondsToDispose = secondsToDispose;
        super.addRequirements(acquisition);
    }

    @Override
    public void execute() {
        acquisition.disposeAlgae();
    }

    @Override
    public boolean isFinished() {
        // if algae limit switch is not pressed and set time has passed stop the command
        if (!acquisition.getAlgaeSwitch()) {
            return secondsToDispose != 0 && timer.get() >= secondsToDispose;
        }
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        timer.restart();
        acquisition.stopAcquisition();
    }
}
