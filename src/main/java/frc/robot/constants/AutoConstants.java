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
        Algae17(new Pose2d(new Translation2d(3.288, 2.57), Rotation2d.fromDegrees(-120))),
        Algae18(new Pose2d(new Translation2d(3.86, 4.3), Rotation2d.fromDegrees(-180))),
        Algae19(new Pose2d(new Translation2d(4.17, 4.99), Rotation2d.fromDegrees(120.0))),
        Algae20(new Pose2d(new Translation2d(5.05, 4.57), Rotation2d.fromDegrees(60.0))),
        Algae21(new Pose2d(new Translation2d(5.12, 3.699), Rotation2d.fromDegrees(0.0))),
        Algae22(new Pose2d(new Translation2d(4.58, 3.30), Rotation2d.fromDegrees(-60.0))),
        Processor3(new Pose2d(new Translation2d(11.21, 6.911), Rotation2d.fromDegrees(-90.0))),
        Processor16(new Pose2d(new Translation2d(6.00, 1.17), Rotation2d.fromDegrees(90.0))),
        Storage(new Pose2d(new Translation2d(0, 0), Rotation2d.fromDegrees(0.0)));

        // BARGE X: 8.023
        private final Pose2d endpoint;

        DriveWaypoints(Pose2d endpoint) {
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
