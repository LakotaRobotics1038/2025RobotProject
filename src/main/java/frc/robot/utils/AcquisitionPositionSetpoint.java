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
    LatchClimb(ShoulderSetpoints.LatchClimb, WristSetpoints.PrepClimb, ExtensionSetpoints.LatchClimb, true),
    PrepClimb(ShoulderSetpoints.Storage, WristSetpoints.PrepClimb, ExtensionSetpoints.LatchClimb, true),
    Climb(ShoulderSetpoints.Climb, WristSetpoints.Climb, ExtensionSetpoints.Climb, true);

    private ShoulderSetpoints shoulderSetpoint;
    private WristSetpoints wristSetPoint;
    private ExtensionSetpoints extensionSetpoint;
    private boolean isClimb;

    private AcquisitionPositionSetpoint(ShoulderSetpoints shoulderSetpoint, WristSetpoints wristSetPoint,
            ExtensionSetpoints extensionSetpoint) {
        this(shoulderSetpoint, wristSetPoint, extensionSetpoint, false);
    }

    private AcquisitionPositionSetpoint(ShoulderSetpoints shoulderSetpoint, WristSetpoints wristSetPoint,
            ExtensionSetpoints extensionSetpoint, boolean isClimb) {
        this.shoulderSetpoint = shoulderSetpoint;
        this.wristSetPoint = wristSetPoint;
        this.extensionSetpoint = extensionSetpoint;
        this.isClimb = isClimb;
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

    public boolean getIsClimb() {
        return this.isClimb;
    }
}