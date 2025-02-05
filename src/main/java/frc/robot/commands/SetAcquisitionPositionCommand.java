package frc.robot.commands;

import com.revrobotics.spark.SparkClosedLoopController.ArbFFUnits;

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
    private SetAcquisitionPositionSetpoint setAcquisitionPositionSetpoint;

    public enum SetAcquisitionPositionSetpoint {

        L1Coral(ShoulderSetpoints.L1Coral, WristSetPoints.L1Coral, ArmSetpoint.L1Coral),
        L2Coral(ShoulderSetpoints.L2Coral, WristSetPoints.L2Coral, ArmSetpoint.L2Coral),
        L3Coral(ShoulderSetpoints.L3Coral, WristSetPoints.L3Coral, ArmSetpoint.L3Coral),
        L4Coral(ShoulderSetpoints.L4Coral, WristSetPoints.L4Coral, ArmSetpoint.L4Coral),
        L23Algae(ShoulderSetpoints.L23Algae, WristSetPoints.L23Algae, ArmSetpoint.L23Algae),
        L34Algae(ShoulderSetpoints.L34Algae, WristSetPoints.L34Algae, ArmSetpoint.L34Algae),
        Processor(ShoulderSetpoints.Processor, WristSetPoints.Processor, ArmSetpoint.Processor),
        FeederStation(ShoulderSetpoints.FeederStation, WristSetPoints.FeederStation, ArmSetpoint.FeederStation),
        Storage(ShoulderSetpoints.Storage, WristSetPoints.Storage, ArmSetpoint.Storage);

        private ShoulderSetpoints shoulderSetpoints;
        private WristSetPoints wristSetPoints;
        private ArmSetpoint armSetpoints;

        private SetAcquisitionPositionSetpoint(ShoulderSetpoints shoulderSetpoints, WristSetPoints wristSetPoints,
                ArmSetpoint armSetpoints) {
            this.shoulderSetpoints = shoulderSetpoints;
            this.wristSetPoints = wristSetPoints;
            this.armSetpoints = armSetpoints;
        }

        public ShoulderSetpoints getShoulderSetpoints() {
            return this.shoulderSetpoints;
        }

        public WristSetPoints getWristSetPoints() {
            return this.wristSetPoints;
        }

        public ArmSetpoint getArmSetpoint() {
            return this.armSetpoints;
        }
    }

    public SetAcquisitionPositionCommand(SetAcquisitionPositionSetpoint setAcquisitionPositionSetpoint) {
        this.shoulder = Shoulder.getInstance();
        this.wrist = Wrist.getInstance();
        this.arm = Arm.getInstance();
        this.setAcquisitionPositionSetpoint = setAcquisitionPositionSetpoint;
    }

    public void initialize() {
        wrist.enable();
        shoulder.enable();
        arm.enable();
        shoulder.setSetpoint(this.setAcquisitionPositionSetpoint.getShoulderSetpoints());
        wrist.setSetpoint(this.setAcquisitionPositionSetpoint.getWristSetPoints());
        arm.setSetpoint(this.setAcquisitionPositionSetpoint.getArmSetpoint());
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
