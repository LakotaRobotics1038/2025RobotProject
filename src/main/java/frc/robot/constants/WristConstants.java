package frc.robot.constants;

public class WristConstants {
    public enum WristSetpoints {
        L1Coral(0),
        L2Coral(110.0),
        L3Coral(130.0),
        L4Coral(0),
        L23Algae(0),
        L34Algae(15),
        Processor(0),
        GroundAlgae(0),
        Storage(0),
        FeederStation(-19.0),
        Climb(0.0),
        Barge(0.0);

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
    public static final double kWristControllerP = 0.03;
    public static final double kWristControllerI = 0;
    public static final double kWristControllerD = 0;
}
