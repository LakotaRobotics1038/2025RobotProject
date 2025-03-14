package frc.robot.constants;

public class ShoulderConstants {
    public enum ShoulderSetpoints {
        L1Coral(27.0),
        L2Coral(22.0),
        L3Coral(65.0),
        L4Coral(63.0),
        L23Algae(61.0),
        L34Algae(61.0),
        Processor(31.0),
        GroundAlgae(0.0),
        Storage(34.0),
        FeederStation(3.5),
        Barge(58.0),
        Climb(55.0);

        public final double setpoint;

        private ShoulderSetpoints(double setpoint) {
            this.setpoint = setpoint;
        }
    }

    public static final int kLeftMotorPort = 6;
    public static final int kRightMotorPort = 5;

    public static final double kP = 0.03;
    public static final double kI = 0.0;
    public static final double kD = 0.0;

    public static final double kMaxPower = 0.75;
    public static final double kMaxDistance = 76.0;

    public static final double kTolerance = 2.0;

    public static final double kMaxExtendedShoulderAngle = 0.0;
    public static final double kEncoderConversion = 120.0;
}
