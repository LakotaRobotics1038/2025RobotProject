package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Base;

public class movePivotBackwardsCommand extends Command {
    public Base base = Base.getInstance();

    public movePivotBackwardsCommand() {
        addRequirements(base);
    }

    @Override
    public void execute() {
        base.movePivotBackwards();
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
