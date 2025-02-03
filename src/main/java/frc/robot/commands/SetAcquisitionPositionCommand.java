package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.ArmConstants.ArmSetpoint;
import frc.robot.subsystems.Arm;

public class SetAcquisitionPositionCommand extends Command {

    // private Shoulder shoulder;
    // private Wrist wrist;
    private Arm arm;
    private ArmSetpoint setpoint;

    public SetAcquisitionPositionCommand(ArmSetpoint setpoint) {
        this.arm = Arm.getInstance();
        this.setpoint = setpoint;
    }

    public void initialize() {
        arm.enable();

    }

    public void execute() {
        arm.setSetpoint(setpoint);
    }

    public boolean isFinished() {
        return false;
    }

    public void end(boolean isInterrupted) {

    }
}
