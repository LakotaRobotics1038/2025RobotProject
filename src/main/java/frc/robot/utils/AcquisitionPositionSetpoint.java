package frc.robot.utils;

import frc.robot.constants.ExtensionConstants.ExtensionSetpoints;
import frc.robot.constants.ShoulderConstants.ShoulderSetpoints;
import frc.robot.constants.WristConstants.WristSetpoints;

//Wait for SetAcquisitionPositionCommand to be merged
public enum AcquisitionPositionSetpoint {

    L1Coral(ShoulderSetpoints.L1Coral, WristSetpoints.L1Coral, ExtensionSetpoints.L1Coral),
    L2Coral(ShoulderSetpoints.L2Coral, WristSetpoints.L2Coral, ExtensionSetpoints.L2Coral),
    L3Coral(ShoulderSetpoints.L3Coral, WristSetpoints.L3Coral, ExtensionSetpoints.L3Coral),
    L4Coral(ShoulderSetpoints.L4Coral, WristSetpoints.L4Coral, ExtensionSetpoints.L4Coral),
    L23Algae(ShoulderSetpoints.L23Algae, WristSetpoints.L23Algae, ExtensionSetpoints.L23Algae),
    L34Algae(ShoulderSetpoints.L34Algae, WristSetpoints.L34Algae, ExtensionSetpoints.L34Algae),
    Processor(ShoulderSetpoints.Processor, WristSetpoints.Processor, ExtensionSetpoints.Processor),
    FeederStation(ShoulderSetpoints.FeederStation, WristSetpoints.FeederStation, ExtensionSetpoints.FeederStation),
    Storage(ShoulderSetpoints.Storage, WristSetpoints.Storage, ExtensionSetpoints.Storage),
    Barge(ShoulderSetpoints.Barge, WristSetpoints.Barge, ExtensionSetpoints.Barge);

    private ShoulderSetpoints shoulderSetpoint;
    private WristSetpoints wristSetPoint;
    private ExtensionSetpoints extensionSetpoint;

    private AcquisitionPositionSetpoint(ShoulderSetpoints shoulderSetpoint, WristSetpoints wristSetPoint,
            ExtensionSetpoints extensionSetpoint) {
        this.shoulderSetpoint = shoulderSetpoint;
        this.wristSetPoint = wristSetPoint;
        this.extensionSetpoint = extensionSetpoint;
    }

    public ShoulderSetpoints getShoulderSetpoint() {
        return this.shoulderSetpoint;
    }

    public WristSetpoints getWristSetpoint() {
        return this.wristSetPoint;
    }

    public ExtensionSetpoints getExtensionSetpoint() {
        return this.extensionSetpoint;
    }
}