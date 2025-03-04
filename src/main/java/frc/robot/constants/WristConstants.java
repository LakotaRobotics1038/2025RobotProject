package frc.robot.constants;

public class WristConstants {
    public enum WristSetpoints {
        L1Coral(0),
        L2Coral(80.0),
        L3Coral(130.0),
        L4Coral(170.0),
        L23Algae(20),
        L34Algae(15),
        Processor(70.0),
        GroundAlgae(0),
        Storage(35),
        FeederStation(-24.0),
        Climb(20.0),
        Barge(-70.0);

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
    public static final double kMaxPower = 0.5;
    public static final double kMinPower = -0.5;
    public static final double kMaxDistance = 0;
    public static final double kWristControllerP = 0.018;
    public static final double kWristControllerI = 0.002;
    public static final double kWristControllerD = 0;
}
