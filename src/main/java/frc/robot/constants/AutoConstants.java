package frc.robot.constants;

import java.util.Optional;

import com.pathplanner.lib.config.RobotConfig;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.TrapezoidProfile;

public final class AutoConstants {
    public enum DriveWaypoints {
        LeftCoral1(new Pose2d(new Translation2d(0, 0), new Rotation2d(0))),
        LeftCoral2(new Pose2d(new Translation2d(0, 0), new Rotation2d(0))),
        LeftCoral3(new Pose2d(new Translation2d(0, 0), new Rotation2d(0))),
        LeftCoral4(new Pose2d(new Translation2d(0, 0), new Rotation2d(0))),
        RightCoral1(new Pose2d(new Translation2d(0, 0), new Rotation2d(0))),
        RightCoral2(new Pose2d(new Translation2d(0, 0), new Rotation2d(0))),
        RightCoral3(new Pose2d(new Translation2d(0, 0), new Rotation2d(0))),
        RightCoral4(new Pose2d(new Translation2d(0, 0), new Rotation2d(0))),
        Algae23(new Pose2d(new Translation2d(0, 0), new Rotation2d(0))),
        Algae34(new Pose2d(new Translation2d(0, 0), new Rotation2d(0))),
        Processor(new Pose2d(new Translation2d(0, 0), new Rotation2d(0))),
        LeftFeederStation1(new Pose2d(new Translation2d(0, 0), new Rotation2d(0))),
        LeftFeederStation2(new Pose2d(new Translation2d(0, 0), new Rotation2d(0))),
        LeftFeederStation3(new Pose2d(new Translation2d(0, 0), new Rotation2d(0))),
        LeftFeederStation4(new Pose2d(new Translation2d(0, 0), new Rotation2d(0))),
        LeftFeederStation5(new Pose2d(new Translation2d(0, 0), new Rotation2d(0))),
        LeftFeederStation6(new Pose2d(new Translation2d(0, 0), new Rotation2d(0))),
        LeftFeederStation7(new Pose2d(new Translation2d(0, 0), new Rotation2d(0))),
        LeftFeederStation8(new Pose2d(new Translation2d(0, 0), new Rotation2d(0))),
        LeftFeederStation9(new Pose2d(new Translation2d(0, 0), new Rotation2d(0))),
        RightFeederStation1(new Pose2d(new Translation2d(0, 0), new Rotation2d(0))),
        RightFeederStation2(new Pose2d(new Translation2d(0, 0), new Rotation2d(0))),
        RightFeederStation3(new Pose2d(new Translation2d(0, 0), new Rotation2d(0))),
        RightFeederStation4(new Pose2d(new Translation2d(0, 0), new Rotation2d(0))),
        RightFeederStation5(new Pose2d(new Translation2d(0, 0), new Rotation2d(0))),
        RightFeederStation6(new Pose2d(new Translation2d(0, 0), new Rotation2d(0))),
        RightFeederStation7(new Pose2d(new Translation2d(0, 0), new Rotation2d(0))),
        RightFeederStation8(new Pose2d(new Translation2d(0, 0), new Rotation2d(0))),
        RightFeederStation9(new Pose2d(new Translation2d(0, 0), new Rotation2d(0)));

        private Pose2d endpoint;

        private DriveWaypoints(Pose2d endpoint) {
            this.endpoint = endpoint;
        }

        public Pose2d getEndpoint() {
            return endpoint;
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
