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
        LeftCoral17(new Pose2d(new Translation2d(4.16, 2.72), Rotation2d.fromDegrees(60.00))),
        Level2LeftCoral17(new Pose2d(new Translation2d(3.98, 2.84), Rotation2d.fromDegrees(-120.00))),
        RightCoral17(new Pose2d(new Translation2d(3.58, 3.30), Rotation2d.fromDegrees(60.00))),
        Level2RightCoral17(new Pose2d(new Translation2d(4.4, 2.49), Rotation2d.fromDegrees(-120.00))),
        LeftCoral18(new Pose2d(new Translation2d(3.22, 4.54), Rotation2d.fromDegrees(0.0))),
        Level2LeftCoral18(new Pose2d(new Translation2d(3.17, 3.76), Rotation2d.fromDegrees(180.0))),
        RightCoral18(new Pose2d(new Translation2d(3.21, 4.10), Rotation2d.fromDegrees(0.0))),
        Level2RightCoral18(new Pose2d(new Translation2d(3.0, 3.48), Rotation2d.fromDegrees(180.0))),
        LeftCoral19(new Pose2d(new Translation2d(4.21, 5.34), Rotation2d.fromDegrees(-60.0))),
        Level2LeftCoral19(new Pose2d(new Translation2d(3.84, 5.05), Rotation2d.fromDegrees(120.0))),
        RightCoral19(new Pose2d(new Translation2d(3.88, 5.01), Rotation2d.fromDegrees(-60.0))),
        Level2RightCoral19(new Pose2d(new Translation2d(3.38, 5), Rotation2d.fromDegrees(Math.toRadians(120.0)))),
        LeftCoral20(new Pose2d(new Translation2d(5.36, 4.94), Rotation2d.fromDegrees(-120.0))),
        Level2LeftCoral20(new Pose2d(new Translation2d(5.43, 5.23), Rotation2d.fromDegrees(60.0))),
        RightCoral20(new Pose2d(new Translation2d(5.09, 5.04), Rotation2d.fromDegrees(-120.0))),
        Level2RightCoral20(new Pose2d(new Translation2d(4.73, 5.57), Rotation2d.fromDegrees(60.0))),
        LeftCoral21(new Pose2d(new Translation2d(5.37, 3.77), Rotation2d.fromDegrees(180.000))),
        Level2LeftCoral21(new Pose2d(new Translation2d(5.78, 4.27), Rotation2d.fromDegrees(0.0))),
        RightCoral21(new Pose2d(new Translation2d(5.39, 4.24), Rotation2d.fromDegrees(180.0))),
        Level2RightCoral21(new Pose2d(new Translation2d(5.90, 4.57), Rotation2d.fromDegrees(0))),
        LeftCoral22(new Pose2d(new Translation2d(4.78, 2.67), Rotation2d.fromDegrees(120.0))),
        Level2LeftCoral22(new Pose2d(new Translation2d(5.41, 2.9), Rotation2d.fromDegrees(-60.0))),
        RightCoral22(new Pose2d(new Translation2d(5.02, 2.83), Rotation2d.fromDegrees(120.0))),
        Level2RightCoral22(new Pose2d(new Translation2d(5.73, 3.03), Rotation2d.fromDegrees(-60.0))),
        Algae17(new Pose2d(new Translation2d(3.4, 3.23), Rotation2d.fromDegrees(60.0))),
        Algae18(new Pose2d(new Translation2d(3.14, 4.34), Rotation2d.fromDegrees(0))),
        Algae19(new Pose2d(new Translation2d(4.03, 5.29), Rotation2d.fromDegrees(-60.0))),
        Algae20(new Pose2d(new Translation2d(5.28, 5.09), Rotation2d.fromDegrees(-120.0))),
        Algae21(new Pose2d(new Translation2d(5.77, 3.83), Rotation2d.fromDegrees(180.0))),
        Algae22(new Pose2d(new Translation2d(4.95, 2.71), Rotation2d.fromDegrees(120.0))),
        Processor3(new Pose2d(new Translation2d(12.07, 8.27), Rotation2d.fromDegrees(90.0))),
        Processor16(new Pose2d(new Translation2d(6.18, 0.20), Rotation2d.fromDegrees(-90.0))),
        FeederStation12Point1(new Pose2d(new Translation2d(0.9, 1.13), Rotation2d.fromDegrees(55.0))),
        FeederStation13Point1(new Pose2d(new Translation2d(1.39, 7.42), Rotation2d.fromDegrees(125.0))),
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
