package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.ArmConstants.ArmSetpoint;
import frc.robot.constants.ShoulderConstants.ShoulderSetpoints;
import frc.robot.constants.WristConstants.WristSetPoints;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Shoulder;
import frc.robot.subsystems.Wrist;

public class SetAcquisitionPositionCommand extends Command {

    private Shoulder shoulder;
    private Wrist wrist;
    private Arm arm;
    private ShoulderSetpoints shoulderSetpoint;
    private WristSetPoints wristSetpoint;
    private ArmSetpoint armSetpoint;

    public SetAcquisitionPositionCommand(ShoulderSetpoints shoulderSetpoint, WristSetPoints wristSetpoint,
            ArmSetpoint armSetpoint) {
        this.shoulder = Shoulder.getInstance();
        this.wrist = Wrist.getInstance();
        this.arm = Arm.getInstance();
        this.shoulderSetpoint = shoulderSetpoint;
        this.wristSetpoint = wristSetpoint;
        this.armSetpoint = armSetpoint;
    }

    public void initialize() {
        wrist.enable();
        shoulder.enable();
        arm.enable();
        shoulder.setSetpoint(shoulderSetpoint);
        wrist.setSetpoint(wristSetpoint);
        arm.setSetpoint(armSetpoint);
    }

    public void execute() {

    }

    public boolean isFinished() {
        return (wrist.onTarget() && shoulder.onTarget() && arm.onTarget()) || arm.isPressed();
    }

    public void end(boolean isInterrupted) {
        wrist.disable();
        shoulder.disable();
        arm.disable();
    }
}
