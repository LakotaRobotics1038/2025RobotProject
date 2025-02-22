package frc.robot.constants;

public class ShoulderConstants {
    public enum ShoulderSetpoints {
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
        Climb(0.0);

        public final double setpoint;

        private ShoulderSetpoints(double setpoint) {
            this.setpoint = setpoint;
        }
    }

    public static final int kLeftMotorPort = 6;
    public static final int kRightMotorPort = 5;

    public static final double kP = 0.0;
    public static final double kI = 0.0;
    public static final double kD = 0.0;

    public static final double kMaxPower = 0.0;
    public static final double kMaxDistance = 0.0;

    public static final double kTolerance = 0.0;
    public static final double kEncoderConversion = 0.0;
}
