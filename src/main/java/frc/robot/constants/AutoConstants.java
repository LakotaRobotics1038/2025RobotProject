package frc.robot.constants;

import java.util.Optional;

import com.pathplanner.lib.config.RobotConfig;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.TrapezoidProfile;

public final class AutoConstants {
    public enum DriveWaypoints {
        LeftCoral17(new Pose2d(new Translation2d(3.656, 3.00), new Rotation2d(60.00))),
        Level2LeftCoral17(new Pose2d(new Translation2d(3.656, 3.00), new Rotation2d(-120.00))),
        RightCoral17(new Pose2d(new Translation2d(3.968, 2.808), new Rotation2d(60.00))),
        Level2RightCoral17(new Pose2d(new Translation2d(3.968, 2.808), new Rotation2d(-120.00))),
        LeftCoral18(new Pose2d(new Translation2d(3.165, 4.187), new Rotation2d(0.0))),
        Level2LeftCoral18(new Pose2d(new Translation2d(3.165, 4.187), new Rotation2d(180.0))),
        RightCoral18(new Pose2d(new Translation2d(0, 0), new Rotation2d(0))),
        Level2RightCoral18(new Pose2d(new Translation2d(0, 0), new Rotation2d(0))),
        LeftCoral19(new Pose2d(new Translation2d(0, 0), new Rotation2d(0))),
        Level2LeftCoral19(new Pose2d(new Translation2d(0, 0), new Rotation2d(0))),
        RightCoral19(new Pose2d(new Translation2d(0, 0), new Rotation2d(0))),
        Level2RightCoral19(new Pose2d(new Translation2d(0, 0), new Rotation2d(0))),
        LeftCoral20(new Pose2d(new Translation2d(0, 0), new Rotation2d(0))),
        Level2LeftCoral20(new Pose2d(new Translation2d(0, 0), new Rotation2d(0))),
        RightCoral20(new Pose2d(new Translation2d(0, 0), new Rotation2d(0))),
        Level2RightCoral20(new Pose2d(new Translation2d(0, 0), new Rotation2d(0))),
        LeftCoral21(new Pose2d(new Translation2d(0, 0), new Rotation2d(0))),
        Level2LeftCoral21(new Pose2d(new Translation2d(0, 0), new Rotation2d(0))),
        RightCoral21(new Pose2d(new Translation2d(0, 0), new Rotation2d(0))),
        Level2RightCoral21(new Pose2d(new Translation2d(0, 0), new Rotation2d(0))),
        LeftCoral22(new Pose2d(new Translation2d(0, 0), new Rotation2d(0))),
        Level2LeftCoral22(new Pose2d(new Translation2d(0, 0), new Rotation2d(0))),
        RightCoral22(new Pose2d(new Translation2d(0, 0), new Rotation2d(0))),
        Level2RightCoral22(new Pose2d(new Translation2d(0, 0), new Rotation2d(0))),
        AlgaeLevel17(new Pose2d(new Translation2d(0, 0), new Rotation2d(0))),
        AlgaeLevel18(new Pose2d(new Translation2d(0, 0), new Rotation2d(0))),
        AlgaeLevel19(new Pose2d(new Translation2d(0, 0), new Rotation2d(0))),
        AlgaeLevel20(new Pose2d(new Translation2d(0, 0), new Rotation2d(0))),
        AlgaeLevel21(new Pose2d(new Translation2d(0, 0), new Rotation2d(0))),
        AlgaeLevel22(new Pose2d(new Translation2d(0, 0), new Rotation2d(0))),
        Processor16(new Pose2d(new Translation2d(0, 0), new Rotation2d(0))),
        FeederStation12Point1(new Pose2d(new Translation2d(0, 0), new Rotation2d(0))),
        FeederStation12Point2(new Pose2d(new Translation2d(0, 0), new Rotation2d(0))),
        FeederStation12Point3(new Pose2d(new Translation2d(0, 0), new Rotation2d(0))),
        FeederStation12Point4(new Pose2d(new Translation2d(0, 0), new Rotation2d(0))),
        FeederStation12Point5(new Pose2d(new Translation2d(0, 0), new Rotation2d(0))),
        FeederStation12Point6(new Pose2d(new Translation2d(0, 0), new Rotation2d(0))),
        FeederStation12Point7(new Pose2d(new Translation2d(0, 0), new Rotation2d(0))),
        FeederStation12Point8(new Pose2d(new Translation2d(0, 0), new Rotation2d(0))),
        FeederStation12Point9(new Pose2d(new Translation2d(0, 0), new Rotation2d(0))),
        FeederStation13Point1(new Pose2d(new Translation2d(0, 0), new Rotation2d(0))),
        FeederStation13Point2(new Pose2d(new Translation2d(0, 0), new Rotation2d(0))),
        FeederStation13Point3(new Pose2d(new Translation2d(0, 0), new Rotation2d(0))),
        FeederStation13Point4(new Pose2d(new Translation2d(0, 0), new Rotation2d(0))),
        FeederStation13Point5(new Pose2d(new Translation2d(0, 0), new Rotation2d(0))),
        FeederStation13Point6(new Pose2d(new Translation2d(0, 0), new Rotation2d(0))),
        FeederStation13Point7(new Pose2d(new Translation2d(0, 0), new Rotation2d(0))),
        FeederStation13Point8(new Pose2d(new Translation2d(0, 0), new Rotation2d(0))),
        FeederStation13Point9(new Pose2d(new Translation2d(0, 0), new Rotation2d(0)));

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
    public static final double kMaxAngularSpeedRadiansPerSecond = Math.PI;
    public static final double kMaxAngularSpeedRadiansPerSecondSquared = Math.PI;

    public static final double kPXController = 1.0;
    public static final double kIXController = 0.35;
    public static final double kDController = 0.0;
    public static final double kPThetaController = 1.0;
    public static final double kIThetaController = 0.05;
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
