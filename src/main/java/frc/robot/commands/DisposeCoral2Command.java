package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Acquisition;

public class DisposeCoral2Command extends Command {
    private Acquisition acquisition = Acquisition.getInstance();
    private double secondsToDispose;
    private Timer timer = new Timer();

    public DisposeCoral2Command() {
        this(0.0);
    }

    public DisposeCoral2Command(double secondsToDispose) {
        this.secondsToDispose = secondsToDispose;
        super.addRequirements(acquisition);
    }

    @Override
    public void execute() {
        acquisition.disposeCoral();
    }

    @Override
    public boolean isFinished() {
        // if neither acquisition laser returns true command is finished

        if (!(acquisition.getTopLaser() || acquisition.getBottomLaser())) {
            return secondsToDispose != 0.0 && timer.get() >= secondsToDispose;
        }

        return false;
    }

    @Override
    public void end(boolean interrupted) {
        timer.restart();
        acquisition.stopAcquisition();
    }
}
