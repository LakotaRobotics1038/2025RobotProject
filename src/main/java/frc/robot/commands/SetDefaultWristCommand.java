package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.constants.WristConstants.WristSetpoints;
import frc.robot.subsystems.Wrist;

public class SetDefaultWristCommand extends Command {
    private Wrist wrist = Wrist.getInstance();

    public SetDefaultWristCommand() {
        super.addRequirements(wrist);
    }

    @Override
    public void initialize() {
        wrist.setDefaultCommand(new InstantCommand(() -> wrist.setSetpoint(WristSetpoints.Storage), wrist));
    }

    @Override
    public void execute() {

    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void end(boolean isInterrupted) {

    }
}
