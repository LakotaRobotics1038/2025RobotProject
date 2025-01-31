package frc.robot.constants;

public class ArmConstants {
    public static final int kArmMotorPort = 0;
    public static final int kArmLaserPort = 0;

    public static final double kMinArmPower = 0.0;
    public static final double kMaxArmPower = 0.0;
    public static final double kArmMaxExtension = 0.0;

    public static final double kTolerance = 0.0;
    public static final double kEncoderConversion = 0.0;
    public static final double kP = 0.0;
    public static final double kI = 0.0;
    public static final double kD = 0.0;

    public enum ArmSetpoint {
        L1Coral(0.0),
        L2Coral(0.0),
        L3Coral(0.0),
        L4Coral(0.0),
        L23Algae(0.0),
        L34Algae(0.0),
        Processor(0.0),
        GroundAlgae(0.0),
        Storage(0.0),
        FeederStation(0.0);

        public final double position;

        ArmSetpoint(double position) {
            this.position = position;
        }
    }
}
