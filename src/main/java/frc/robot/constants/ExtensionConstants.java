package frc.robot.constants;

import edu.wpi.first.units.Units;

public class ExtensionConstants {
    public static final int kExtensionMotorPort = 2;
    public static final int kExtensionLaserPort = 36;

    public static final double kMinExtensionPower = -1.0;
    public static final double kMaxExtensionPower = 1.0;
    public static final double kExtensionMaximum = 25.9;
    public static final double kExtensionMaxMove = 0.0;

    public static final double kTolerance = 0.5;
    private static final double kLeadScrewPitch = 80.0; // millimeters
    private static final double kLeadScrewPitchInInches = Units.Inches.convertFrom(kLeadScrewPitch, Units.Millimeters);
    private static final double kGearboxReduction = 16.0;
    public static final double kEncoderConversion = kLeadScrewPitchInInches / kGearboxReduction;
    public static final double kP = 0.8;
    public static final double kI = 0.001;
    public static final double kD = 0.0;

    public enum ExtensionSetpoints {
        L1Coral(0.0),
        L2Coral(0.0),
        L3Coral(2.0),
        L4Coral(kExtensionMaximum),
        L23Algae(0.0),
        L34Algae(15.0),
        Processor(0.0),
        GroundAlgae(0.0),
        Storage(0.0),
        FeederStation(0.0),
        Barge(kExtensionMaximum),
        Climb(0.0),
        Zero(0.0);

        public final double position;

        ExtensionSetpoints(double position) {
            this.position = position;
        }
    }
}
