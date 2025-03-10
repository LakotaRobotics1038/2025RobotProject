package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Acquisition;

public class DisposeCoral134Command extends Command {
    private Acquisition acquisition = Acquisition.getInstance();

    public DisposeCoral134Command() {
        super.addRequirements(acquisition);
    }

    @Override
    public void execute() {
        acquisition.disposeCoral134();
    }

    @Override
    public boolean isFinished() {
        // if neither acquisition laser returns true command is finished

        return !(acquisition.getTopLaser() || acquisition.getBottomLaser());
    }

    @Override
    public void end(boolean interrupted) {
        acquisition.stopAcquisition();
    }
}
