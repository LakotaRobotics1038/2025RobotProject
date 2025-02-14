package frc.robot.commands;

import com.revrobotics.spark.SparkClosedLoopController.ArbFFUnits;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.ExtensionConstants.ExtensionSetpoints;
import frc.robot.constants.ShoulderConstants.ShoulderSetpoints;
import frc.robot.constants.WristConstants.WristSetPoints;
import frc.robot.subsystems.Extension;
import frc.robot.subsystems.Shoulder;
import frc.robot.subsystems.Wrist;

public class SetAcquisitionPositionCommand extends Command {

    private Shoulder shoulder = Shoulder.getInstance();
    private Wrist wrist = Wrist.getInstance();
    private Extension extension = Extension.getInstance();
    private AcquisitionPositionSetpoint acquisitionPositionSetpoint;

    public enum AcquisitionPositionSetpoint {

        L1Coral(ShoulderSetpoints.L1Coral, WristSetPoints.L1Coral, ExtensionSetpoints.L1Coral),
        L2Coral(ShoulderSetpoints.L2Coral, WristSetPoints.L2Coral, ExtensionSetpoints.L2Coral),
        L3Coral(ShoulderSetpoints.L3Coral, WristSetPoints.L3Coral, ExtensionSetpoints.L3Coral),
        L4Coral(ShoulderSetpoints.L4Coral, WristSetPoints.L4Coral, ExtensionSetpoints.L4Coral),
        L23Algae(ShoulderSetpoints.L23Algae, WristSetPoints.L23Algae, ExtensionSetpoints.L23Algae),
        L34Algae(ShoulderSetpoints.L34Algae, WristSetPoints.L34Algae, ExtensionSetpoints.L34Algae),
        Processor(ShoulderSetpoints.Processor, WristSetPoints.Processor, ExtensionSetpoints.Processor),
        FeederStation(ShoulderSetpoints.FeederStation, WristSetPoints.FeederStation, ExtensionSetpoints.FeederStation),
        Storage(ShoulderSetpoints.Storage, WristSetPoints.Storage, ExtensionSetpoints.Storage);

        private ShoulderSetpoints shoulderSetpoint;
        private WristSetPoints wristSetPoint;
        private ExtensionSetpoints armSetpoint;

        private AcquisitionPositionSetpoint(ShoulderSetpoints shoulderSetpoint, WristSetPoints wristSetPoint,
                ExtensionSetpoints armSetpoint) {
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

        public ExtensionSetpoints getArmSetpoint() {
            return this.armSetpoint;
        }
    }

    public SetAcquisitionPositionCommand(AcquisitionPositionSetpoint acquisitionPositionSetpoint) {
        addRequirements(shoulder, wrist, extension);
        this.acquisitionPositionSetpoint = acquisitionPositionSetpoint;
    }

    public void initialize() {
        wrist.enable();
        shoulder.enable();
        extension.enable();
        shoulder.setSetpoint(this.acquisitionPositionSetpoint.getShoulderSetpoint());
        wrist.setSetpoint(this.acquisitionPositionSetpoint.getWristSetpoint());
        extension.setSetpoint(this.acquisitionPositionSetpoint.getArmSetpoint());
    }

    public boolean isFinished() {
        return wrist.onTarget() && shoulder.onTarget() && extension.onTarget();
    }

    public void end(boolean interrupted) {
        wrist.disable();
        shoulder.disable();
        extension.disable();
    }
}
