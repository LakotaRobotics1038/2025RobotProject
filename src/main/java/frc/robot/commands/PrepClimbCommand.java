package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.ExtensionConstants.ExtensionSetpoints;
import frc.robot.constants.ShoulderConstants.ShoulderSetpoints;
import frc.robot.constants.WristConstants.WristSetPoints;
import frc.robot.subsystems.Extension;
import frc.robot.subsystems.Climb;
import frc.robot.subsystems.Shoulder;
import frc.robot.subsystems.Wrist;

public class PrepClimbCommand extends Command {
    private Climb climb = Climb.getInstance();
    private Extension extension = Extension.getInstance();
    private Shoulder shoulder = Shoulder.getInstance();
    private Wrist wrist = Wrist.getInstance();

    public PrepClimbCommand() {
        addRequirements(climb, extension, shoulder, wrist);
    }

    @Override
    public void initialize() {
        extension.enable();
        shoulder.enable();
        wrist.enable();
    }

    @Override
    public void execute() {
        extension.setSetpoint(ExtensionSetpoints.Climb);
        wrist.setSetpoint(WristSetPoints.Climb);
        shoulder.setSetpoint(ShoulderSetpoints.Climb);
        climb.runClimbDown();
    }

    @Override
    public boolean isFinished() {
        return extension.onTarget() && wrist.onTarget() && shoulder.onTarget();
    }

    @Override
    public void end(boolean interrupted) {
        extension.disable();
        shoulder.disable();
        wrist.disable();
        climb.stopClimb();
    }
}
