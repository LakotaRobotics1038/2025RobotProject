package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.math.MathUtil;
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
    private WristSetpoints wristSetpoint;
    private ExtensionSetpoints extensionSetpoint;
    private ShoulderSetpoints shoulderSetpoint;

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
        if (acquisitionPositionSetpoint == null) {
            acquisitionPositionSetpoint = acquisitionPositionSetpointSupplier.get();
        }

        wristSetpoint = acquisitionPositionSetpoint.getWristSetpoint();
        extensionSetpoint = acquisitionPositionSetpoint.getExtensionSetpoint();
        shoulderSetpoint = acquisitionPositionSetpoint.getShoulderSetpoint();

        wrist.enable();
        shoulder.enable();
        extension.enable();

        shoulder.setSetpoint(shoulderSetpoint);
    }

    @Override
    public void execute() {
        double wristPos = wristSetpoint.getSetpoint();
        double shoulderPos = shoulder.getPosition();
        double extPos = extension.getPosition();

        if (extPos > 10 && shoulderPos < 24 && shoulderPos > 27) {
            shoulderPos = MathUtil.clamp(shoulderPos, 0, 44);
            wristPos = MathUtil.clamp(wristPos, -60, 0);
        } else if (extPos > 10 && shoulderPos > 22 && shoulderPos < 25) {
            shoulderPos = MathUtil.clamp(shoulderPos, 0, 23);
            wristPos = MathUtil.clamp(wristPos, -35, 0);
        } else if (extPos > 10 && shoulderPos < 20) {
            shoulderPos = MathUtil.clamp(shoulderPos, 0, extPos);
            wristPos = MathUtil.clamp(wristPos, -30, 0);
        } else if (shoulderPos > 0 && shoulderPos < 28 && extPos < 10) {
            wristPos = MathUtil.clamp(wristPos, -44, -36);
        } else if (shoulderPos > 10 && shoulderPos < 24 && extPos < 10) {
            wristPos = MathUtil.clamp(wristPos, -53, -38);
        } else if (shoulderPos > 24 && shoulderPos < 37 && extPos < 20) {
            wristPos = MathUtil.clamp(wristPos, -60, -40);
        } else if (shoulderPos > 37 && shoulderPos < 10 && extPos < 20) {
            wristPos = MathUtil.clamp(wristPos, 20, 50);
        }

        if (wrist.onTarget()) {
            wrist.disable();
        }
        // BAD ZONES

        // Ground Pickup - 32 -> 36.292
        // L23 Algae - 51.609 -> 150.492
        // L34 Algae - 40.881 -> 155.55
        // Barge - 200.798 -> 311.22

        wrist.setSetpoint(wristPos);
        extension.setSetpoint(extPos);
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