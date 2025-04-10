package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.AcquisitionConstants;
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
    private boolean isGroundAngle;

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
        if (acquisitionPositionSetpoint == AcquisitionPositionSetpoint.GroundAlgae) {
            wrist.setSetpoint(-42);
            isGroundAngle = true;
        } else if (acquisitionPositionSetpoint == AcquisitionPositionSetpoint.Processor) {
            wrist.setSetpoint(-54);
            isGroundAngle = true;
        } else {
            shoulder.setSetpoint(shoulderSetpoint);
            extension.setSetpoint(extensionSetpoint);
        }

    }

    @Override
    public void execute() {
        double wristPos = wristSetpoint.getSetpoint();
        double shoulderPos = shoulder.getPosition();
        double extPos = extension.getPosition();

        if (!isGroundAngle) {
            final double WRIST_LENGTH = 3; // TODO Random Value. Should be changed and placed in a constants file.
            final double WRIST_WHEEL_OFFSET_ANGLE = 2; // TODO Random Value. Should be changed and placed in a constants
                                                       // file.
            final double MAX_VERT_EXTENSION = 18; // TODO Random Value. Should be changed and placed in a constants
                                                  // file.
            final double SWERVE_MODULE_HEIGHT = 1;
            double extVertLength = extPos * AcquisitionConstants.cos(shoulderPos);
            double extHoriLength = extPos * AcquisitionConstants.sin(shoulderPos);
            double wristTopWheelVertLength = extVertLength
                    + WRIST_LENGTH * AcquisitionConstants.cos(shoulderPos + wristPos);
            double wristTopWheelHoriLength = extHoriLength
                    + WRIST_LENGTH * AcquisitionConstants.sin(shoulderPos + wristPos);
            double wristBottomWheelVertLength = extVertLength
                    + WRIST_LENGTH * AcquisitionConstants.cos(shoulderPos + wristPos + WRIST_WHEEL_OFFSET_ANGLE);
            double wristBottomWheelHoriLength = extHoriLength
                    + WRIST_LENGTH * AcquisitionConstants.sin(shoulderPos + wristPos + WRIST_WHEEL_OFFSET_ANGLE);

            if (shoulderPos <= 0) {
                shoulderPos = Math.min(shoulderPos,
                        90 - Math.toDegrees(AcquisitionConstants.acos(MAX_VERT_EXTENSION / extPos)
                                - Math.max(AcquisitionConstants.acos(wristTopWheelVertLength / WRIST_LENGTH),
                                        AcquisitionConstants.acos(wristBottomWheelVertLength / WRIST_LENGTH))));
                shoulderPos = Math.min(shoulderPos,
                        90 - Math.toDegrees(AcquisitionConstants.asin(SWERVE_MODULE_HEIGHT / extPos)
                                - Math.max(AcquisitionConstants.asin(wristTopWheelHoriLength / WRIST_LENGTH),
                                        AcquisitionConstants.asin(wristBottomWheelHoriLength / WRIST_LENGTH))));
            } else {
                shoulderPos = Math.max(shoulderPos,
                        -90 + Math.toDegrees(AcquisitionConstants.acos(MAX_VERT_EXTENSION / extPos)
                                + Math.max(AcquisitionConstants.acos(wristTopWheelVertLength / WRIST_LENGTH),
                                        AcquisitionConstants.acos(wristBottomWheelVertLength / WRIST_LENGTH))));
                shoulderPos = Math.max(shoulderPos,
                        -90 + Math.toDegrees(AcquisitionConstants.asin(SWERVE_MODULE_HEIGHT / extPos)
                                + Math.max(AcquisitionConstants.asin(wristTopWheelHoriLength / WRIST_LENGTH),
                                        AcquisitionConstants.asin(wristBottomWheelHoriLength / WRIST_LENGTH))));
            }

            // Will something bad happen if MAX_VERT_EXTENSION - extVertLength < 0? idk
            // probably
            // but is this even possible?

            // Should a max be put here with and without the offset variable to account for
            // both the points?

            if (wristPos <= 0) {
                wristPos = Math.min(wristPos,
                        -AcquisitionConstants.acos((MAX_VERT_EXTENSION - extVertLength) / WRIST_LENGTH)
                                + WRIST_WHEEL_OFFSET_ANGLE);
                wristPos = Math.min(wristPos,
                        -AcquisitionConstants.asin((SWERVE_MODULE_HEIGHT - extHoriLength) / WRIST_LENGTH)
                                + WRIST_WHEEL_OFFSET_ANGLE);
            } else {
                wristPos = Math.max(wristPos,
                        AcquisitionConstants.acos((MAX_VERT_EXTENSION - extVertLength) / WRIST_LENGTH)
                                - WRIST_WHEEL_OFFSET_ANGLE);
                wristPos = Math.max(wristPos,
                        AcquisitionConstants.asin((SWERVE_MODULE_HEIGHT - extHoriLength) / WRIST_LENGTH)
                                - WRIST_WHEEL_OFFSET_ANGLE);
            }

            // if (shoulderPos > 290 && shoulderPos < 305 && extPos > 20) {
            // wristPos = MathUtil.clamp(wristPos, -165, -42.5);
            // } else if (extPos > 10 && shoulderPos < 338 && shoulderPos > 335) {
            // wristPos = MathUtil.clamp(wristPos, -35, 0);
            // } else if (extPos > 20 && shoulderPos < 340) {
            // wristPos = MathUtil.clamp(wristPos, -41.5, 0);
            // } else if (shoulderPos < 360 && shoulderPos > 350 && extPos < 10) {
            // wristPos = MathUtil.clamp(wristPos, -44, -36);
            // } else if (shoulderPos < 350 && shoulderPos > 336 && extPos < 10) {
            // wristPos = MathUtil.clamp(wristPos, -53, -40);
            // } else if (shoulderPos > 317 && shoulderPos < 327 && extPos < 10) {
            // wristPos = MathUtil.clamp(wristPos, -60, -60);
            // } else if (shoulderPos < 336 && shoulderPos > 323 && extPos < 20) {
            // wristPos = MathUtil.clamp(wristPos, -60, -43);
            // } else if (shoulderPos > 323 && shoulderPos < 336 && extPos < 20) {
            // wristPos = MathUtil.clamp(wristPos, -56, -44);
            // } else if (shoulderPos > 323 && shoulderPos < 350 && extPos < 20) {
            // wristPos = MathUtil.clamp(wristPos, -56, -44);
            // } else if (shoulderPos > 308 && shoulderPos < 317 && extPos < 10) {
            // wristPos = MathUtil.clamp(wristPos, -55, -50);
            // } else if (shoulderPos > 350 && shoulderPos < 360 && extPos < 10) {
            // wristPos = MathUtil.clamp(wristPos, -44, -31);
            // } else if (shoulderPos < 308 && shoulderPos > 300) {
            // wristPos = MathUtil.clamp(wristPos, -40, 0);
            // } else if (shoulderPos > 350 && shoulderPos < 360 && extPos < 10) {
            // wristPos = MathUtil.clamp(wristPos, -44, -31);
            // }

            wrist.setSetpoint(wristPos);
        } else {
            if (wrist.onTarget()) {
                shoulder.setSetpoint(shoulderSetpoint);
                extension.setSetpoint(extensionSetpoint);
                if (shoulder.onTarget() && extension.onTarget()) {
                    wrist.setSetpoint(wristPos);
                }
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
    }
}