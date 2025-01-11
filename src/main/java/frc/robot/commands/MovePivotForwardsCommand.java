package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Base;

public class MovePivotForwardsCommand extends Command {
    private Base base = Base.getInstance();

    public MovePivotForwardsCommand() {
        addRequirements(base);
    }

    @Override
    public void execute() {
        base.movePivotForwards();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        base.stopPivot();
    }
}
