package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.WristConstants.WristSetpoints;
import frc.robot.subsystems.Wrist;

public class SetWristPositionCommand extends Command {
    private final Wrist wrist = Wrist.getInstance();
    private final WristSetpoints setpoint;

    public SetWristPositionCommand(WristSetpoints setpoint) {
        super.addRequirements(wrist);
        this.setpoint = setpoint;
    }

    @Override
    public void initialize() {
        this.wrist.setSetpoint(setpoint);
        this.wrist.enable();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean isInterrupted) {
        this.wrist.disable();
    }
}
