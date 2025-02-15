package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.constants.ShoulderConstants.ShoulderSetpoints;
import frc.robot.subsystems.Shoulder;

public class SetDefaultShoulderCommand extends Command {
    private Shoulder shoulder = Shoulder.getInstance();

    public SetDefaultShoulderCommand() {
        super.addRequirements(shoulder);
    }

    @Override
    public void initialize() {
        shoulder.setDefaultCommand(new InstantCommand(() -> shoulder.setSetpoint(ShoulderSetpoints.Storage), shoulder));
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
