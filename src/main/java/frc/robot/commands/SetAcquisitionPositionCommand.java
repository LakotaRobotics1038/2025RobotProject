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
        extension.setSetpoint(extensionSetpoint);
    }

    @Override
    public void execute() {
        double wristPos = wristSetpoint.getSetpoint();
        double shoulderPos = shoulder.getPosition();
        double extPos = extension.getPosition();

        // if (shoulderPos > 10) {
        // extension.setSetpoint(extensionSetpoint);
        // } else if (shoulderPos > 5) {
        // extPos = MathUtil.clamp(extPos, 0, 10);
        // } else {
        // extPos = MathUtil.clamp(extPos, 0, 0);
        // }

        if (extPos > 10 && shoulderPos < 336 && shoulderPos > 333) {
            wristPos = MathUtil.clamp(wristPos, -60, 0);
        } else if (extPos > 10 && shoulderPos < 338 && shoulderPos > 335) {
            wristPos = MathUtil.clamp(wristPos, -35, 0);
        } else if (extPos > 10 && shoulderPos < 340) {
            wristPos = MathUtil.clamp(wristPos, -30, 0);
        } else if (shoulderPos < 360 && shoulderPos > 332 && extPos < 10) {
            wristPos = MathUtil.clamp(wristPos, -44, -36);
        } else if (shoulderPos < 350 && shoulderPos > 336 && extPos < 10) {
            wristPos = MathUtil.clamp(wristPos, -53, -38);
        } else if (shoulderPos < 336 && shoulderPos > 323 && extPos < 20) {
            wristPos = MathUtil.clamp(wristPos, -60, -40);
        } else if (shoulderPos > 323 && shoulderPos < 350 && extPos < 20) {
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