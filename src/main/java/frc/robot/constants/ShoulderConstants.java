package frc.robot.constants;

public class ShoulderConstants {
    public enum ShoulderSetpoints {
        L23Algae(318.0),
        L34Algae(314.0),
        Processor(336.0),
        GroundAlgae(355.0),
        Storage(301.0),
        Barge(300.0),
        Vertical(300),
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

    public static final double kMaxPower = 0.3;
    public static final double kMaxDistance = 290.0;

    public static final double kTolerance = 2.0;

    public static final double kMaxExtendedShoulderAngle = 0.0;
    public static final double kEncoderConversion = 360.0;
}
