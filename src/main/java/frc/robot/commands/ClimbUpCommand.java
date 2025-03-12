package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Climb;

public class ClimbUpCommand extends Command {
    private Climb climb = Climb.getInstance();

    public ClimbUpCommand() {
        super.addRequirements(climb);
    }

    @Override
    public void execute() {
        climb.runClimbUp();
    }

    @Override
    public boolean isFinished() {
        // return climb.getPosition() <= ClimbConstants.maxClimbDistance;
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        climb.stopClimb();
    }
}
