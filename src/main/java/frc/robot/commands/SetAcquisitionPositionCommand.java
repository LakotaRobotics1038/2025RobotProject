package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.ExtensionConstants.ExtensionSetpoints;
import frc.robot.constants.ShoulderConstants.ShoulderSetpoints;
import frc.robot.constants.WristConstants.WristSetpoints;
import frc.robot.subsystems.Extension;
import frc.robot.subsystems.Shoulder;
import frc.robot.subsystems.Wrist;
import frc.robot.utils.AcquisitionPositionSetpoint;

public class SetAcquisitionPositionCommand extends Command {
    private Shoulder shoulder = Shoulder.getInstance();
    private Wrist wrist = Wrist.getInstance();
    private Extension extension = Extension.getInstance();
    private AcquisitionPositionSetpoint acquisitionPositionSetpoint;
    private Supplier<AcquisitionPositionSetpoint> acquisitionPositionSetpointSupplier;
    private FinishActions finishAction;
    private boolean negativeWrist;
    private WristSetpoints wristSetpoint;
    private ExtensionSetpoints extensionSetpoint;
    private boolean isExtensionGood;

    public enum FinishActions {
        NoFinish,
        NoDisable,
        Default
    }

    public SetAcquisitionPositionCommand(Supplier<AcquisitionPositionSetpoint> acquisitionPositionSetpointSupplier,
            FinishActions finishAction) {
        this(finishAction);
        this.acquisitionPositionSetpointSupplier = acquisitionPositionSetpointSupplier;
    }

    public SetAcquisitionPositionCommand(AcquisitionPositionSetpoint acquisitionPositionSetpoint) {
        this(FinishActions.Default);
        this.acquisitionPositionSetpoint = acquisitionPositionSetpoint;
    }

    public SetAcquisitionPositionCommand(AcquisitionPositionSetpoint acquisitionPositionSetpoint,
            FinishActions finishAction) {
        this(finishAction);
        this.acquisitionPositionSetpoint = acquisitionPositionSetpoint;
    }

    private SetAcquisitionPositionCommand(FinishActions finishAction) {
        addRequirements(shoulder, wrist, extension);
        this.finishAction = finishAction;
    }

    public void initialize() {
        if (this.acquisitionPositionSetpoint == null) {
            this.acquisitionPositionSetpoint = this.acquisitionPositionSetpointSupplier.get();
        }
        this.wristSetpoint = acquisitionPositionSetpoint.getWristSetpoint();
        this.extensionSetpoint = acquisitionPositionSetpoint.getExtensionSetpoint();
        wrist.enable();
        shoulder.enable();
        extension.enable();

        if (wrist.getPosition() < 0) {
            shoulder.setSetpoint(ShoulderSetpoints.Vertical);
            negativeWrist = true;
        }
    }

    @Override
    public void execute() {
        if (negativeWrist) {
            if (shoulder.onTarget()) {
                extension.setSetpoint(ExtensionSetpoints.UpForWristEscape);
            }
            if (extension.onTarget()) {
                shoulder.setSetpoint(ShoulderSetpoints.BackOfBot);
                if (shoulder.onTarget()) {
                    negativeWrist = false;
                }
            }
        }

        if (shoulder.isSafe(wristSetpoint)) {
            if (extension.isSafe(wristSetpoint)) {
                wrist.setSetpoint(wristSetpoint);
            } else if (extension.getPosition() > wristSetpoint.getExtMax()) {
                if (shoulder.getPosition() > extensionSetpoint.getShoulderMin()
                        && shoulder.getPosition() < extensionSetpoint.getShoulderMax()) {
                    extension.setSetpoint(wristSetpoint.getExtMax());
                    this.isExtensionGood = true;
                } else if (shoulder.getPosition() > extensionSetpoint.getShoulderMax()) {
                    shoulder.setSetpoint(extensionSetpoint.getShoulderMax());
                } else {
                    shoulder.setSetpoint(extensionSetpoint.getShoulderMin());
                }
            } else {
                extension.setSetpoint(wristSetpoint.getExtMin());
            }
        } else if (shoulder.getPosition() > wristSetpoint.getShoulderMax() && isExtensionGood) {
            shoulder.setSetpoint(wristSetpoint.getShoulderMax());
        } else if (shoulder.getPosition() < wristSetpoint.getShoulderMin() && isExtensionGood) {
            shoulder.setSetpoint(wristSetpoint.getShoulderMin());
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
        this.isExtensionGood = false;
    }
}