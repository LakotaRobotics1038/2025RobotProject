package frc.robot.commands;

import com.revrobotics.spark.SparkClosedLoopController.ArbFFUnits;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.AcquisitionPositionSetpoint;
import frc.robot.constants.ArmConstants.ArmSetpoint;
import frc.robot.constants.ShoulderConstants.ShoulderSetpoints;
import frc.robot.constants.WristConstants.WristSetPoints;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Shoulder;
import frc.robot.subsystems.Wrist;

public class SetAcquisitionPositionCommand extends Command {

    private Shoulder shoulder = Shoulder.getInstance();
    private Wrist wrist = Wrist.getInstance();
    private Arm arm = Arm.getInstance();
    private AcquisitionPositionSetpoint acquisitionPositionSetpoint;

    public SetAcquisitionPositionCommand(AcquisitionPositionSetpoint acquisitionPositionSetpoint) {
        addRequirements(shoulder, wrist, arm);
        this.acquisitionPositionSetpoint = acquisitionPositionSetpoint;
    }

    public void initialize() {
        wrist.enable();
        shoulder.enable();
        arm.enable();
        shoulder.setSetpoint(this.acquisitionPositionSetpoint.getShoulderSetpoint());
        wrist.setSetpoint(this.acquisitionPositionSetpoint.getWristSetpoint());
        arm.setSetpoint(this.acquisitionPositionSetpoint.getArmSetpoint());
    }

    public boolean isFinished() {
        return wrist.onTarget() && shoulder.onTarget() && arm.onTarget();
    }

    public void end(boolean interrupted) {
        wrist.disable();
        shoulder.disable();
        arm.disable();
    }
}
