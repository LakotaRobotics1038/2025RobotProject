package frc.robot.constants;

public class WristConstants {
    public enum WristSetpoints {
        L1Coral(0),
        L2Coral(0),
        L3Coral(0),
        L4Coral(0),
        L23Algae(0),
        L34Algae(0),
        Processor(0),
        GroundAlgae(0),
        Storage(0),
        FeederStation(0),
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

    public static final int kWristCanId = 0;
    public static final double kMaxPower = 0;
    public static final double kMinPower = 0;
    public static final double kMaxDistance = 0;
    public static final double kWristControllerP = 0;
    public static final double kWristControllerI = 0;
    public static final double kWristControllerD = 0;
}
