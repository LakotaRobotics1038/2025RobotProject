package frc.robot.utils;

import frc.robot.constants.ExtensionConstants.ExtensionSetpoints;
import frc.robot.constants.ShoulderConstants.ShoulderSetpoints;
import frc.robot.constants.WristConstants.WristSetpoints;

public enum AcquisitionPositionSetpoint {

    L23Algae(ShoulderSetpoints.L23Algae, WristSetpoints.L23Algae, ExtensionSetpoints.L23Algae),
    L34Algae(ShoulderSetpoints.L34Algae, WristSetpoints.L34Algae, ExtensionSetpoints.L34Algae),
    Processor(ShoulderSetpoints.Processor, WristSetpoints.Processor, ExtensionSetpoints.Processor),
    Storage(ShoulderSetpoints.Storage, WristSetpoints.Storage, ExtensionSetpoints.Storage),
    Barge(ShoulderSetpoints.Barge, WristSetpoints.Barge, ExtensionSetpoints.Barge),
    GroundAlgae(ShoulderSetpoints.GroundAlgae, WristSetpoints.GroundAlgae, ExtensionSetpoints.GroundAlgae);

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