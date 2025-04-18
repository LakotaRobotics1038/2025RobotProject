package frc.robot.utils;

import frc.robot.constants.ExtensionConstants.ExtensionSetpoints;
import frc.robot.constants.ShoulderConstants.ShoulderSetpoints;
import frc.robot.constants.WristConstants.WristSetpoints;
import frc.robot.subsystems.Shoulder;

public enum AcquisitionPositionSetpoint {

    L23Algae(ShoulderSetpoints.L23Algae, WristSetpoints.L23Algae, ExtensionSetpoints.L23Algae),
    L34Algae(ShoulderSetpoints.L34Algae, WristSetpoints.L34Algae, ExtensionSetpoints.L34Algae),
    Processor(ShoulderSetpoints.Processor, WristSetpoints.Processor, ExtensionSetpoints.Processor),
    Barge(ShoulderSetpoints.Barge, WristSetpoints.Barge, ExtensionSetpoints.Barge),
    GroundAlgae(ShoulderSetpoints.GroundAlgae, WristSetpoints.GroundAlgae, ExtensionSetpoints.GroundAlgae),
    Storage(ShoulderSetpoints.Storage, WristSetpoints.Storage, ExtensionSetpoints.Zero),
    PrepClimb(ShoulderSetpoints.PrepClimb, WristSetpoints.PrepClimb, ExtensionSetpoints.PrepClimb),
    Climb(ShoulderSetpoints.Climb, WristSetpoints.Climb, ExtensionSetpoints.Climb);

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
        if (shoulderSetpoint == null) {
            return Shoulder.getInstance().getSetpoint();
        }
        return this.shoulderSetpoint;
    }

    public WristSetpoints getWristSetpoint() {
        return this.wristSetPoint;
    }

    public ExtensionSetpoints getExtensionSetpoint() {
        return this.extensionSetpoint;
    }
}