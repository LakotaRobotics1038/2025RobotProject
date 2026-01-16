package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Acquisition;

public class ShootAlgaeCommand extends Command {
    private final Acquisition acquisition = Acquisition.getInstance();

    public ShootAlgaeCommand() {
        super.addRequirements(acquisition);
    }

    @Override
    public void execute() {
        acquisition.shootAlgae();
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
