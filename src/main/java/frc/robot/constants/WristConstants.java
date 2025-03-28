package frc.robot.constants;

public class WristConstants {
    public enum WristSetpoints {
        L23Algae(-42.5),
        L34Algae(-36.0),
        Processor(-42.0),
        GroundAlgae(-31.0),
        Storage(-28.0),
        Barge(-159.0);

        private double setpoint;
        private double extMax;
        private double shoulderMin;
        private double shoulderMax;

        private WristSetpoints(double setpoint) {
            this.setpoint = setpoint;
        }

        public double getSetpoint() {
            return this.setpoint;
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
    public static final double kTolerance = 1.0;
    public static final double kMaxPower = 0.5;
    public static final double kMaxDistance = 180;
    public static final double kMinDistance = -180;
    public static final double kWristControllerP = 0.02;
    public static final double kWristControllerI = 0.002;
    public static final double kWristControllerD = 0;
}
