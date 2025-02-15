package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.constants.WristConstants.WristSetpoints;
import frc.robot.subsystems.Wrist;

public class SetWristPositionCommand extends Command {
    private Wrist wrist = Wrist.getInstance();
    private WristSetpoints setpoint;

    public SetWristPositionCommand(WristSetpoints setpoint) {
        super.addRequirements(wrist);
        this.setpoint = setpoint;
    }

    @Override
    public void initialize() {
        wrist.setDefaultCommand(new InstantCommand(() -> wrist.setSetpoint(setpoint), wrist));
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
