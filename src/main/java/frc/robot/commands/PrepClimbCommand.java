package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.ArmConstants.ArmSetpoints;
import frc.robot.constants.ShoulderConstants.ShoulderSetpoints;
import frc.robot.constants.WristConstants.WristSetPoints;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Climb;
import frc.robot.subsystems.Shoulder;
import frc.robot.subsystems.Wrist;

public class PrepClimbCommand extends Command {
    private Climb climb = Climb.getInstance();
    private Arm arm = Arm.getInstance();
    private Shoulder shoulder = Shoulder.getInstance();
    private Wrist wrist = Wrist.getInstance();

    public PrepClimbCommand() {
        addRequirements(climb, arm, shoulder, wrist);
    }

    @Override
    public void initialize() {
        arm.enable();
        shoulder.enable();
        wrist.enable();
    }

    @Override
    public void execute() {
        arm.setSetpoint(ArmSetpoints.Climb);
        wrist.setSetpoint(WristSetPoints.Climb);
        shoulder.setSetpoint(ShoulderSetpoints.Climb);
        climb.runClimbDown();
    }

    @Override
    public boolean isFinished() {
        return arm.onTarget() && wrist.onTarget() && shoulder.onTarget();
    }

    @Override
    public void end(boolean interrupted) {
        arm.disable();
        shoulder.disable();
        wrist.disable();
        climb.stopClimb();
    }
}
