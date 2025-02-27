package frc.robot.constants;

public class ExtensionConstants {
    public static final int kExtensionMotorPort = 2;
    public static final int kExtensionLaserPort = 36;

    public static final double kMinExtensionPower = 0.0;
    public static final double kMaxExtensionPower = 0.0;
    public static final double kExtensionMaximum = 0.0;

    public static final double kTolerance = 0.0;
    public static final double kEncoderConversion = 0.0;
    public static final double kP = 0.0;
    public static final double kI = 0.0;
    public static final double kD = 0.0;

    public enum ExtensionSetpoints {
        L1Coral(0.0),
        L2Coral(0.0),
        L3Coral(0.0),
        L4Coral(0.0),
        L23Algae(0.0),
        L34Algae(0.0),
        Processor(0.0),
        GroundAlgae(0.0),
        Storage(0.0),
        FeederStation(0.0),
        Barge(0.0),
        Climb(0.0),
        Zero(0.0);

        public final double position;

        ExtensionSetpoints(double position) {
            this.position = position;
        }
    }
}
