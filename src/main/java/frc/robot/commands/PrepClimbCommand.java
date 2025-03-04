package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.ExtensionConstants.ExtensionSetpoints;
import frc.robot.constants.ShoulderConstants.ShoulderSetpoints;
import frc.robot.constants.WristConstants.WristSetpoints;
import frc.robot.subsystems.Extension;
import frc.robot.subsystems.Climb;
import frc.robot.subsystems.Shoulder;
import frc.robot.subsystems.Wrist;

public class PrepClimbCommand extends Command {
    private Climb climb = Climb.getInstance();

    public PrepClimbCommand() {
        addRequirements(climb);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        if (!climb.getSwitch()) {
            climb.runClimbDown();
        } else {
            climb.stopClimb();
        }
    }

    @Override
    public boolean isFinished() {
        return climb.getSwitch();
    }

    @Override
    public void end(boolean interrupted) {
        climb.stopClimb();
    }
}
