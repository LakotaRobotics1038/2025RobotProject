package frc.robot.utils;

import frc.robot.constants.ArmConstants.ArmSetpoints;
import frc.robot.constants.ShoulderConstants.ShoulderSetpoints;
import frc.robot.constants.WristConstants.WristSetPoints;

//Wait for SetAcquisitionPositionCommand to be merged
public enum AcquisitionPositionSetpoint {

    L1Coral(ShoulderSetpoints.L1Coral, WristSetPoints.L1Coral, ArmSetpoints.L1Coral),
    L2Coral(ShoulderSetpoints.L2Coral, WristSetPoints.L2Coral, ArmSetpoints.L2Coral),
    L3Coral(ShoulderSetpoints.L3Coral, WristSetPoints.L3Coral, ArmSetpoints.L3Coral),
    L4Coral(ShoulderSetpoints.L4Coral, WristSetPoints.L4Coral, ArmSetpoints.L4Coral),
    L23Algae(ShoulderSetpoints.L23Algae, WristSetPoints.L23Algae, ArmSetpoints.L23Algae),
    L34Algae(ShoulderSetpoints.L34Algae, WristSetPoints.L34Algae, ArmSetpoints.L34Algae),
    Processor(ShoulderSetpoints.Processor, WristSetPoints.Processor, ArmSetpoints.Processor),
    FeederStation(ShoulderSetpoints.FeederStation, WristSetPoints.FeederStation, ArmSetpoints.FeederStation),
    Storage(ShoulderSetpoints.Storage, WristSetPoints.Storage, ArmSetpoints.Storage),
    Barge(ShoulderSetpoints.Barge, WristSetPoints.Barge, ArmSetpoints.Barge);

    private ShoulderSetpoints shoulderSetpoint;
    private WristSetPoints wristSetPoint;
    private ArmSetpoints armSetpoint;

    private AcquisitionPositionSetpoint(ShoulderSetpoints shoulderSetpoint, WristSetPoints wristSetPoint,
            ArmSetpoints armSetpoint) {
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

    public ArmSetpoints getArmSetpoint() {
        return this.armSetpoint;
    }
}