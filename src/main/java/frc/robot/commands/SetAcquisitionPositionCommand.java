package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.Timer;
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
    private boolean isSupplier;
    private boolean isAuton;
    private double timeToMove;
    private Timer timer = new Timer();

    public SetAcquisitionPositionCommand(Supplier<AcquisitionPositionSetpoint> acquisitionPositionSetpointSupplier) {
        addRequirements(shoulder, wrist, extension);
        this.acquisitionPositionSetpointSupplier = acquisitionPositionSetpointSupplier;
        this.isSupplier = true;
    }

    public SetAcquisitionPositionCommand(AcquisitionPositionSetpoint acquisitionPositionSetpoint) {
        addRequirements(shoulder, wrist, extension);
        this.acquisitionPositionSetpoint = acquisitionPositionSetpoint;
        this.isSupplier = false;
    }

    public SetAcquisitionPositionCommand(AcquisitionPositionSetpoint acquisitionPositionSetpoint,
            double timeToMove) {
        addRequirements(shoulder, wrist, extension);
        this.acquisitionPositionSetpoint = acquisitionPositionSetpoint;
        this.isSupplier = false;
        this.timeToMove = timeToMove;
    }

    public void initialize() {
        timer.restart();
        wrist.enable();
        shoulder.enable();
        extension.enable();

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
        } else {
            if (isSupplier) {
                extension.setSetpoint(acquisitionPositionSetpointSupplier.get().getExtensionSetpoint());
                shoulder.setSetpoint(acquisitionPositionSetpointSupplier.get().getShoulderSetpoint());
                wrist.setSetpoint(acquisitionPositionSetpointSupplier.get().getWristSetpoint());
            } else {
                extension.setSetpoint(this.acquisitionPositionSetpoint.getExtensionSetpoint());
                shoulder.setSetpoint(this.acquisitionPositionSetpoint.getShoulderSetpoint());
                wrist.setSetpoint(this.acquisitionPositionSetpoint.getWristSetpoint());
            }
        }
    }

    public boolean isFinished() {
        // return isAuton && extension.onTarget() && shoulder.onTarget() &&
        // wrist.onTarget();
        return this.timeToMove != 0 && timer.get() >= timeToMove;
    }

    public void end(boolean interrupted) {
        timer.restart();
        timer.stop();
        if (!isAuton) {
            wrist.disable();
            extension.disable();
            shoulder.disable();
        }
    }
}
