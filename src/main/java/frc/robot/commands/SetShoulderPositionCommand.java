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
        this.shoulder.setSetpoint(setpoint);
        this.shoulder.enable();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean isInterrupted) {
        this.shoulder.disable();
    }

}
