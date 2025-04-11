package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.ExtensionConstants.ExtensionSetpoints;
import frc.robot.constants.ShoulderConstants.ShoulderSetpoints;
import frc.robot.constants.WristConstants.WristSetpoints;
import frc.robot.subsystems.Extension;
import frc.robot.subsystems.Shoulder;
import frc.robot.subsystems.Vision;
import frc.robot.subsystems.Wrist;
import frc.robot.utils.AcquisitionPositionSetpoint;

public class SetAcquisitionPositionCommand extends Command {
    private Shoulder shoulder = Shoulder.getInstance();
    private Wrist wrist = Wrist.getInstance();
    private Extension extension = Extension.getInstance();
    private Vision vision = Vision.getInstance();

    private AcquisitionPositionSetpoint acquisitionPositionSetpoint;
    private Supplier<AcquisitionPositionSetpoint> acquisitionPositionSetpointSupplier;
    private FinishActions finishAction;
    private WristSetpoints wristSetpoint;
    private ExtensionSetpoints extensionSetpoint;
    private ShoulderSetpoints shoulderSetpoint;
    private boolean isGroundAlgae;
    private boolean isFromBarge;
    private boolean waitUntilStorage;
    private boolean isClimb;

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

        if (acquisitionPositionSetpoint == AcquisitionPositionSetpoint.Climb && shoulder.getPosition() < 300
                && extension.getPosition() < 1) {
            isClimb = true;
        }

        if (this.acquisitionPositionSetpoint == AcquisitionPositionSetpoint.GroundAlgae) {
            vision.setAlgaeMode();
            if (shoulder.getPosition() > 328 && shoulder.getPosition() < 332 && extension.getPosition() < 1) {
                wrist.setSetpoint(-20);
                extension.setSetpoint(9);
                isGroundAlgae = true;
            }
        }

        if (this.acquisitionPositionSetpoint == AcquisitionPositionSetpoint.Storage && extension.getPosition() < 10) {
            extension.setSetpoint(15);
            waitUntilStorage = true;
        }

        if (shoulder.getPosition() < 310 && extension.getPosition() > 20
                && acquisitionPositionSetpoint.getExtensionSetpoint().position < 10) {
            isFromBarge = true;
        }

        wristSetpoint = acquisitionPositionSetpoint.getWristSetpoint();
        extensionSetpoint = acquisitionPositionSetpoint.getExtensionSetpoint();
        shoulderSetpoint = acquisitionPositionSetpoint.getShoulderSetpoint();

        wrist.enable();
        shoulder.enable();
        extension.enable();
        if (!isGroundAlgae && !isFromBarge && !waitUntilStorage) {
            shoulder.setSetpoint(shoulderSetpoint);
            extension.setSetpoint(extensionSetpoint);
        } else if (isFromBarge) {
            extension.setSetpoint(extensionSetpoint);
        } else if (waitUntilStorage) {
            shoulder.setSetpoint(shoulderSetpoint);
        }
    }

    @Override
    public void execute() {
        double wristPos = wristSetpoint.getSetpoint();
        double shoulderPos = shoulder.getPosition();
        double extPos = extension.getPosition();
        if (!isClimb) {
            if (shoulderPos > 290 && shoulderPos < 305 && extPos > 20) {
                wristPos = MathUtil.clamp(wristPos, -165, -5);
            } else if (extPos > 10 && shoulderPos < 338 && shoulderPos > 335) {
                wristPos = MathUtil.clamp(wristPos, -35, 0);
            } else if (extPos > 20 && shoulderPos < 340) {
                wristPos = MathUtil.clamp(wristPos, -50, 0);
            } else if (shoulderPos < 360 && shoulderPos > 350 && extPos < 10) {
                wristPos = MathUtil.clamp(wristPos, -44, -5);
            } else if (shoulderPos < 350 && shoulderPos > 336 && extPos < 10) {
                wristPos = MathUtil.clamp(wristPos, -43, -20);
            } else if (shoulderPos > 317 && shoulderPos < 327 && extPos < 10) {
                wristPos = MathUtil.clamp(wristPos, -40, -5);
            } else if (shoulderPos < 336 && shoulderPos > 333 && extPos < 20) {
                wristPos = MathUtil.clamp(wristPos, -45, -30);
            } else if (shoulderPos < 336 && shoulderPos > 323 && extPos < 20) {
                wristPos = MathUtil.clamp(wristPos, -45, -5);
            } else if (shoulderPos > 323 && shoulderPos < 350 && extPos < 20) {
                wristPos = MathUtil.clamp(wristPos, -56, -5);
            } else if (shoulderPos > 308 && shoulderPos < 317 && extPos < 10) {
                wristPos = MathUtil.clamp(wristPos, -55, -5);
            } else if (shoulderPos > 350 && shoulderPos < 360 && extPos < 10) {
                wristPos = MathUtil.clamp(wristPos, -44, -5);
            } else if (shoulderPos < 308 && shoulderPos > 300 && extPos < 10) {
                wristPos = MathUtil.clamp(wristPos, -40, -5);
            } else if (shoulderPos > 345 && extPos < 20) {
                wristPos = MathUtil.clamp(wristPos, -35, -5);
            }
        }

        if (extension.getPosition() < 10 && isFromBarge) {
            shoulder.setSetpoint(shoulderSetpoint);
            isFromBarge = false;
        }

        if (isGroundAlgae && wrist.onTarget() && extension.onTarget()) {
            shoulder.setSetpoint(shoulderSetpoint);
            extension.setSetpoint(extensionSetpoint);
        }

        if (wrist.onTarget() && waitUntilStorage) {
            shoulder.setSetpoint(shoulderSetpoint);
            extension.setSetpoint(extensionSetpoint);
            if (shoulder.onTarget() && extension.onTarget()) {
                waitUntilStorage = false;
            }
        }

        wrist.setSetpoint(wristPos);
    }

    public boolean isFinished() {
        return finishAction != FinishActions.NoFinish &&
                extension.onTarget() &&
                shoulder.onTarget() &&
                wrist.onTarget();
    }

    public void end(boolean interrupted) {
        if (this.acquisitionPositionSetpoint == AcquisitionPositionSetpoint.GroundAlgae) {
            vision.setAprilTagMode();
        }
        if (finishAction != FinishActions.NoDisable) {
            wrist.disable();
            extension.disable();
            shoulder.disable();
        }
        isGroundAlgae = false;
        waitUntilStorage = false;
        isFromBarge = false;
        isClimb = false;
    }
}