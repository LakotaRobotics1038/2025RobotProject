package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.ExtensionConstants;
import frc.robot.constants.ExtensionConstants.ExtensionSetpoints;
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
    private boolean retractExtension;
    private FinishActions finishAction;
    private boolean setpointsSet;

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
        wrist.enable();
        shoulder.enable();
        extension.enable();

        if (this.acquisitionPositionSetpoint == null) {
            this.acquisitionPositionSetpoint = this.acquisitionPositionSetpointSupplier.get();
        }

        if (extension.getPosition() >= ExtensionConstants.kExtensionMaxMove) {
            retractExtension = true;
            extension.setSetpoint(ExtensionSetpoints.Storage);
        }
    }

    public void execute() {
        if (this.retractExtension) {
            if (extension.getPosition() <= ExtensionConstants.kExtensionMaxMove) {
                this.retractExtension = false;
            }
        } else if (!setpointsSet) {
            extension.setSetpoint(this.acquisitionPositionSetpoint.getExtensionSetpoint());
            shoulder.setSetpoint(this.acquisitionPositionSetpoint.getShoulderSetpoint());
            wrist.setSetpoint(this.acquisitionPositionSetpoint.getWristSetpoint());
            setpointsSet = true;
        }
    }

    public boolean isFinished() {
        return finishAction != FinishActions.NoFinish &&
                extension.onTarget() &&
                wrist.onTarget() &&
                shoulder.onTarget()
                && setpointsSet;

    }

    public void end(boolean interrupted) {
        if (finishAction != FinishActions.NoDisable) {
            wrist.disable();
            extension.disable();
            shoulder.disable();
        }
        setpointsSet = false;
    }
}