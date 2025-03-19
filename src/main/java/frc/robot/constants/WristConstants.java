package frc.robot.constants;

public class WristConstants {
    public enum WristSetpoints {
        L1Coral(135, 0, 0, 0, 0),
        L2Coral(80.0, 0, 0, 0, 0),
        L3Coral(110.0, 0, 0, 0, 0),
        L4Coral(127.5, 0, 0, 0, 0),
        L23Algae(10, 0, 0, 0, 0),
        L34Algae(10, 0, 0, 0, 0),
        Processor(65.0, 0, 0, 0, 0),
        GroundAlgae(0, 0, 0, 0, 0),
        Storage(35, 0, 0, 0, 0),
        FeederStation(166.0, 0, 0, 0, 0),
        Climb(20.0, 0, 0, 0, 0),
        Barge(-61.0, 0, 0, 0, 0),
        Ground(50, 0, 0, 0, 0),
        Ground2(180, 0, 0, 0, 0);

        private double setpoint;
        private double extMin;
        private double extMax;
        private double shoulderMin;
        private double shoulderMax;

        private WristSetpoints(double setpoint, double extMin, double extMax, double shoulderMin, double shoulderMax) {
            this.setpoint = setpoint;
            this.extMin = extMin;
            this.extMax = extMax;
            this.shoulderMin = shoulderMin;
            this.shoulderMax = shoulderMax;
        }

        public double getSetpoint() {
            return this.setpoint;
        }

        public double getExtMin() {
            return this.extMin;
        }

        public double getExtMax() {
            return this.extMax;
        }

        public double getShoulderMin() {
            return this.shoulderMin;
        }

        public double getShoulderMax() {
            return this.shoulderMax;
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
