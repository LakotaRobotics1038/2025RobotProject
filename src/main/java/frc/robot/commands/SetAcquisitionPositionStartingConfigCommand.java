package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.ExtensionConstants.ExtensionSetpoints;
import frc.robot.constants.ShoulderConstants.ShoulderSetpoints;
import frc.robot.constants.WristConstants.WristSetpoints;
import frc.robot.subsystems.Extension;
import frc.robot.subsystems.Shoulder;
import frc.robot.subsystems.Wrist;

public class SetAcquisitionPositionStartingConfigCommand extends Command {
    private Shoulder shoulder = Shoulder.getInstance();
    private Wrist wrist = Wrist.getInstance();
    private Extension extension = Extension.getInstance();
    private FinishActions finishAction;
    private boolean negativeWrist;

    public enum FinishActions {
        NoFinish,
        NoDisable,
        Default
    }

    public SetAcquisitionPositionStartingConfigCommand(FinishActions finishAction) {
        addRequirements(shoulder, wrist, extension);
        this.finishAction = finishAction;
    }

    public void initialize() {
        wrist.enable();
        shoulder.enable();
        extension.enable();

        if (wrist.getPosition() < 0) {
            shoulder.setSetpoint(ShoulderSetpoints.StartingPos);
            wrist.setSetpoint(WristSetpoints.StartingPos);
            negativeWrist = true;
        }
    }

    @Override
    public void execute() {
        if (negativeWrist) {
            if (shoulder.onTarget()) {
                extension.setSetpoint(ExtensionSetpoints.Zero);
                wrist.setSetpoint(WristSetpoints.StartingPos);
            }
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
        negativeWrist = false;
    }
}
