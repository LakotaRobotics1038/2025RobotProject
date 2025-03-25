package frc.robot.constants;

import static edu.wpi.first.units.Units.MetersPerSecond;

import java.util.Optional;

import com.pathplanner.lib.config.RobotConfig;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.TrapezoidProfile;

public final class AutoConstants {
    public enum DriveWaypoints {
        Algae17(new Pose2d(new Translation2d(3.4, 3.23), Rotation2d.fromDegrees(60.0))),
        Algae18(new Pose2d(new Translation2d(3.14, 4.34), Rotation2d.fromDegrees(0))),
        Algae19(new Pose2d(new Translation2d(4.03, 5.29), Rotation2d.fromDegrees(-60.0))),
        Algae20(new Pose2d(new Translation2d(5.28, 5.09), Rotation2d.fromDegrees(-120.0))),
        Algae21(new Pose2d(new Translation2d(5.77, 3.83), Rotation2d.fromDegrees(180.0))),
        Algae22(new Pose2d(new Translation2d(4.95, 2.71), Rotation2d.fromDegrees(120.0))),
        Processor3(new Pose2d(new Translation2d(12.07, 8.27), Rotation2d.fromDegrees(90.0))),
        Processor16(new Pose2d(new Translation2d(6.18, 0.20), Rotation2d.fromDegrees(-90.0))),
        Storage(new Pose2d(new Translation2d(0, 0), Rotation2d.fromDegrees(0.0)));

        private Pose2d endpoint;

        private DriveWaypoints(Pose2d endpoint) {
            this.endpoint = endpoint;
        }

        public Pose2d getEndpoint() {
            return endpoint;
        }

        public Rotation2d getRotation2d() {
            return endpoint.getRotation();
        }

    }

    public static final double kMaxAccelerationMetersPerSecondSquared = 7;
    public static final double maxSpeed = SwerveConstants.kSpeedAt12Volts.in(MetersPerSecond);
    public static final double kMaxAngularSpeedRadiansPerSecond = Math.PI;
    public static final double kMaxAngularSpeedRadiansPerSecondSquared = Math.PI;

    public static final double kPXController = 8;
    public static final double kIXController = 0.02;
    public static final double kDController = 0.0;
    public static final double kPThetaController = 1.35;
    public static final double kIThetaController = 0.0;
    public static final double kDThetaController = 0.0;

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
