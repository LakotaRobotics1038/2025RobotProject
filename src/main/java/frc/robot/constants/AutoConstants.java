package frc.robot.constants;

import java.util.Optional;

import com.pathplanner.lib.config.RobotConfig;

import edu.wpi.first.math.trajectory.TrapezoidProfile;

public final class AutoConstants {
    public static final double kMaxAccelerationMetersPerSecondSquared = 7;
    public static final double kMaxAngularSpeedRadiansPerSecond = Math.PI;
    public static final double kMaxAngularSpeedRadiansPerSecondSquared = Math.PI;

    public static final double kPXController = 1.0;
    public static final double kIXController = 0.35;
    public static final double kPThetaController = 1.0;
    public static final double kIThetaController = 0.05;

    // Constraint for the motion profiled robot angle controller
    public static final TrapezoidProfile.Constraints kThetaControllerConstraints = new TrapezoidProfile.Constraints(
            kMaxAngularSpeedRadiansPerSecond, kMaxAngularSpeedRadiansPerSecondSquared);

    public static final Optional<RobotConfig> kRobotConfig = loadRobotConfig();

    // Static method to load the RobotConfig
    private static Optional<RobotConfig> loadRobotConfig() {
        try {
            return Optional.of(RobotConfig.fromGUISettings());
        } catch (Exception e) {
            // Handle exception as needed
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
