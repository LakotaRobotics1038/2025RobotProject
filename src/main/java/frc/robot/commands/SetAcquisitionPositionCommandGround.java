package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.ShoulderConstants.ShoulderSetpoints;
import frc.robot.constants.WristConstants.WristSetpoints;
import frc.robot.subsystems.Extension;
import frc.robot.subsystems.Shoulder;
import frc.robot.subsystems.Wrist;

public class SetAcquisitionPositionCommandGround extends Command {
    private Shoulder shoulder = Shoulder.getInstance();
    private Wrist wrist = Wrist.getInstance();
    private Extension extension = Extension.getInstance();
    private FinishActions finishAction;

    public enum FinishActions {
        NoFinish,
        NoDisable,
        Default
    }

    public SetAcquisitionPositionCommandGround(FinishActions finishAction) {
        addRequirements(shoulder, wrist, extension);
        this.finishAction = finishAction;
    }

    public void initialize() {
        wrist.enable();
        shoulder.enable();
        extension.enable();
        wrist.setSetpoint(WristSetpoints.Ground2);
    }

    @Override
    public void execute() {
        if (wrist.onTarget() && wrist.getSetpoint() == WristSetpoints.Ground2) {
            shoulder.setSetpoint(ShoulderSetpoints.L3Coral);
        }
        if (shoulder.onTarget() && shoulder.getSetpoint() == ShoulderSetpoints.L3Coral) {
            wrist.setSetpoint(WristSetpoints.Processor);
            shoulder.setSetpoint(ShoulderSetpoints.Processor);
        }
    }

    public boolean isFinished() {
        return finishAction != FinishActions.NoFinish &&
                extension.onTarget() &&
                wrist.onTarget() &&
                shoulder.onTarget();

    }

    public void end(boolean interrupted) {
        if (finishAction != FinishActions.NoDisable) {
            wrist.disable();
            extension.disable();
            shoulder.disable();
        }
    }
}