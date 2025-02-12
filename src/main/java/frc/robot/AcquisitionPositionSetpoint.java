package frc.robot;

import frc.robot.constants.ArmConstants.ArmSetpoint;
import frc.robot.constants.ShoulderConstants.ShoulderSetpoints;
import frc.robot.constants.WristConstants.WristSetPoints;

//Wait for SetAcquisitionPositionCommand to be merged
public enum AcquisitionPositionSetpoint {

    L1Coral(ShoulderSetpoints.L1Coral, WristSetPoints.L1Coral, ArmSetpoint.L1Coral),
    L2Coral(ShoulderSetpoints.L2Coral, WristSetPoints.L2Coral, ArmSetpoint.L2Coral),
    L3Coral(ShoulderSetpoints.L3Coral, WristSetPoints.L3Coral, ArmSetpoint.L3Coral),
    L4Coral(ShoulderSetpoints.L4Coral, WristSetPoints.L4Coral, ArmSetpoint.L4Coral),
    L23Algae(ShoulderSetpoints.L23Algae, WristSetPoints.L23Algae, ArmSetpoint.L23Algae),
    L34Algae(ShoulderSetpoints.L34Algae, WristSetPoints.L34Algae, ArmSetpoint.L34Algae),
    Processor(ShoulderSetpoints.Processor, WristSetPoints.Processor, ArmSetpoint.Processor),
    FeederStation(ShoulderSetpoints.FeederStation, WristSetPoints.FeederStation, ArmSetpoint.FeederStation),
    Storage(ShoulderSetpoints.Storage, WristSetPoints.Storage, ArmSetpoint.Storage),
    Barge(ShoulderSetpoints.Barge, WristSetPoints.Barge, ArmSetpoint.Barge);

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