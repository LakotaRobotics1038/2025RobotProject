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

    private Shoulder shoulder = Shoulder.getInstance();
    private Wrist wrist = Wrist.getInstance();
    private Arm arm = Arm.getInstance();
    private AcquisitionPositionSetpoint acquisitionPositionSetpoint;

    public enum AcquisitionPositionSetpoint {

        L1Coral(ShoulderSetpoints.L1Coral, WristSetPoints.L1Coral, ArmSetpoint.L1Coral),
        L2Coral(ShoulderSetpoints.L2Coral, WristSetPoints.L2Coral, ArmSetpoint.L2Coral),
        L3Coral(ShoulderSetpoints.L3Coral, WristSetPoints.L3Coral, ArmSetpoint.L3Coral),
        L4Coral(ShoulderSetpoints.L4Coral, WristSetPoints.L4Coral, ArmSetpoint.L4Coral),
        L23Algae(ShoulderSetpoints.L23Algae, WristSetPoints.L23Algae, ArmSetpoint.L23Algae),
        L34Algae(ShoulderSetpoints.L34Algae, WristSetPoints.L34Algae, ArmSetpoint.L34Algae),
        Processor(ShoulderSetpoints.Processor, WristSetPoints.Processor, ArmSetpoint.Processor),
        FeederStation(ShoulderSetpoints.FeederStation, WristSetPoints.FeederStation, ArmSetpoint.FeederStation),
        Storage(ShoulderSetpoints.Storage, WristSetPoints.Storage, ArmSetpoint.Storage);

        private ShoulderSetpoints shoulderSetpoint;
        private WristSetPoints wristSetPoint;
        private ArmSetpoint armSetpoint;

        private AcquisitionPositionSetpoint(ShoulderSetpoints shoulderSetpoint, WristSetPoints wristSetPoint,
                ArmSetpoint armSetpoint) {
            this.shoulderSetpoint = shoulderSetpoint;
            this.wristSetPoint = wristSetPoint;
            this.armSetpoint = armSetpoint;
        }

        public ShoulderSetpoints getShoulderSetpoint() {
            return this.shoulderSetpoint;
        }

        public WristSetPoints getWristSetpoint() {
            return this.wristSetPoint;
        }

        public ArmSetpoint getArmSetpoint() {
            return this.armSetpoint;
        }
    }

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
