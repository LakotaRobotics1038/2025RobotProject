package frc.robot.constants;

public class ShoulderConstants {
    public enum ShoulderSetpoints {
        L1Coral(0.0),
        L2Coral(22.0),
        L3Coral(65.0),
        L4Coral(63.0),
        L23Algae(318.0),
        L34Algae(314.0),
        Processor(336.0),
        GroundAlgae(4.0),
        Storage(301.0),
        FeederStation(0.0),
        Barge(297.0),
        Climb(55.0),
        Vertical(50),
        BackOfBot(10);

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
