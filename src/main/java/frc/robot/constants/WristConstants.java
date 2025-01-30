package frc.robot.constants;

public class WristConstants {
    public enum WristSetPoints {

        L1_CORAL(0),
        L2_CORAL(0),
        L3_CORAL(0),
        L4_CORAL(0),
        L23_CORAL(0),
        L34_CORAL(0),
        PROCESSOR(0),
        GROUND_ALGAE(0),
        STORAGE(0),
        FEEDER_STATION(0);

        private double setpoint;

        private WristSetPoints(double setpoint) {
            this.setpoint = setpoint;
        }

        public double getSetpoint() {
            return this.setpoint;
        }
    }

    public static final int kWristCanId = 0;
}
