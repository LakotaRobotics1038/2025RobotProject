package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.constants.ShoulderConstants.ShoulderSetpoints;
import frc.robot.subsystems.Shoulder;

public class SetShoulderPositionCommand extends Command {
    private Shoulder shoulder = Shoulder.getInstance();
    private ShoulderSetpoints setpoint;

    public SetShoulderPositionCommand(ShoulderSetpoints setpoint) {
        super.addRequirements(shoulder);
        this.setpoint = setpoint;
    }

    @Override
    public void initialize() {
        shoulder.setDefaultCommand(new InstantCommand(() -> shoulder.setSetpoint(setpoint), shoulder));
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void end(boolean isInterrupted) {

    }

}
