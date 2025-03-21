package frc.robot.constants;

public class WristConstants {
    public enum WristSetpoints {
        L1Coral(135),
        L2Coral(80.0),
        L3Coral(110.0),
        L4Coral(127.5),
        L23Algae(10),
        L34Algae(10),
        Processor(65.0),
        GroundAlgae(0),
        Storage(35),
        FeederStation(166.0),
        Climb(20.0),
        Barge(-61.0),
        Ground2(180);

        private double setpoint;

        private WristSetpoints(double setpoint) {
            this.setpoint = setpoint;
        }

        public double getSetpoint() {
            return this.setpoint;
        }
    }

    public static final int kWristCanId = 3;
    public static final double kEncoderConversion = 360.0;
    public static final double kTolerance = 2.0;
    public static final double kMaxPower = 0.5;
    public static final double kMinPower = -0.5;
    public static final double kMaxDistance = 180;
    public static final double kMinDistance = -180;
    public static final double kWristControllerP = 0.018;
    public static final double kWristControllerI = 0.002;
    public static final double kWristControllerD = 0;
}
