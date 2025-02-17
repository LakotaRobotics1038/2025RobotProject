package frc.robot.constants;

import java.util.HashMap;
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
        RightCoral18(new Pose2d(new Translation2d(3.165, 3.863), new Rotation2d(0.0))),
        Level2RightCoral18(new Pose2d(new Translation2d(3.165, 3.863), new Rotation2d(180.0))),
        LeftCoral19(new Pose2d(new Translation2d(3.992, 5.230), new Rotation2d(-60.0))),
        Level2LeftCoral19(new Pose2d(new Translation2d(3.992, 5.230), new Rotation2d(120.0))),
        RightCoral19(new Pose2d(new Translation2d(3.704, 5.062), new Rotation2d(-60.0))),
        Level2RightCoral19(new Pose2d(new Translation2d(3.704, 5.062), new Rotation2d(120.0))),
        LeftCoral20(new Pose2d(new Translation2d(5.299, 5.074), new Rotation2d(-120.0))),
        Level2LeftCoral20(new Pose2d(new Translation2d(5.299, 5.074), new Rotation2d(60.0))),
        RightCoral20(new Pose2d(new Translation2d(4.975, 5.206), new Rotation2d(-120.0))),
        Level2RightCoral20(new Pose2d(new Translation2d(4.975, 5.206), new Rotation2d(60.0))),
        LeftCoral21(new Pose2d(new Translation2d(5.826, 3.875), new Rotation2d(180.000))),
        Level2LeftCoral21(new Pose2d(new Translation2d(5.826, 3.875), new Rotation2d(0.0))),
        RightCoral21(new Pose2d(new Translation2d(5.826, 4.187), new Rotation2d(180.0))),
        Level2RightCoral21(new Pose2d(new Translation2d(5.826, 4.187), new Rotation2d(0))),
        LeftCoral22(new Pose2d(new Translation2d(5.275, 2.976), new Rotation2d(120.0))),
        Level2LeftCoral22(new Pose2d(new Translation2d(5.275, 2.976), new Rotation2d(-60.0))),
        RightCoral22(new Pose2d(new Translation2d(4.963, 2.832), new Rotation2d(120.0))),
        Level2RightCoral22(new Pose2d(new Translation2d(4.963, 2.832), new Rotation2d(-60.0))),
        AlgaeLevel17(new Pose2d(new Translation2d(3.824, 2.904), new Rotation2d(60.0))),
        AlgaeLevel18(new Pose2d(new Translation2d(3.189, 4.007), new Rotation2d(0))),
        AlgaeLevel19(new Pose2d(new Translation2d(3.848, 5.134), new Rotation2d(-60.0))),
        AlgaeLevel20(new Pose2d(new Translation2d(5.143, 5.170), new Rotation2d(-120.0))),
        AlgaeLevel21(new Pose2d(new Translation2d(5.778, 4.055), new Rotation2d(180.0))),
        AlgaeLevel22(new Pose2d(new Translation2d(5.143, 2.928), new Rotation2d(120.0))),
        Processor3(new Pose2d(new Translation2d(11.484, 7.543), new Rotation2d(90.0))),
        Processor16(new Pose2d(new Translation2d(5.982, 0.495), new Rotation2d(-90.0))),
        FeederStation12Point1(new Pose2d(new Translation2d(0, 0), new Rotation2d(-125.0))),
        FeederStation12Point2(new Pose2d(new Translation2d(0, 0), new Rotation2d(-125.0))),
        FeederStation12Point3(new Pose2d(new Translation2d(0, 0), new Rotation2d(-125.0))),
        FeederStation12Point4(new Pose2d(new Translation2d(0, 0), new Rotation2d(-125.0))),
        FeederStation12Point5(new Pose2d(new Translation2d(0, 0), new Rotation2d(-125.0))),
        FeederStation12Point6(new Pose2d(new Translation2d(0, 0), new Rotation2d(-125.0))),
        FeederStation12Point7(new Pose2d(new Translation2d(0, 0), new Rotation2d(-125.0))),
        FeederStation12Point8(new Pose2d(new Translation2d(0, 0), new Rotation2d(-125.0))),
        FeederStation12Point9(new Pose2d(new Translation2d(0, 0), new Rotation2d(-125.0))),
        FeederStation13Point1(new Pose2d(new Translation2d(0, 0), new Rotation2d(125.0))),
        FeederStation13Point2(new Pose2d(new Translation2d(0, 0), new Rotation2d(125.0))),
        FeederStation13Point3(new Pose2d(new Translation2d(0, 0), new Rotation2d(125.0))),
        FeederStation13Point4(new Pose2d(new Translation2d(0, 0), new Rotation2d(125.0))),
        FeederStation13Point5(new Pose2d(new Translation2d(0, 0), new Rotation2d(125.0))),
        FeederStation13Point6(new Pose2d(new Translation2d(0, 0), new Rotation2d(125.0))),
        FeederStation13Point7(new Pose2d(new Translation2d(0, 0), new Rotation2d(125.0))),
        FeederStation13Point8(new Pose2d(new Translation2d(0, 0), new Rotation2d(125.0))),
        FeederStation13Point9(new Pose2d(new Translation2d(0, 0), new Rotation2d(125.0)));

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

        public static HashMap<Integer, DriveWaypoints> getIdsAnd134CoralWaypointsLeftHashmap() {
            HashMap<Integer, DriveWaypoints> idAndDriveWaypoints = new HashMap<Integer, DriveWaypoints>();
            idAndDriveWaypoints.put(6, DriveWaypoints.LeftCoral22);
            idAndDriveWaypoints.put(7, DriveWaypoints.LeftCoral21);
            idAndDriveWaypoints.put(8, DriveWaypoints.LeftCoral20);
            idAndDriveWaypoints.put(9, DriveWaypoints.LeftCoral19);
            idAndDriveWaypoints.put(10, DriveWaypoints.LeftCoral18);
            idAndDriveWaypoints.put(11, DriveWaypoints.LeftCoral17);
            idAndDriveWaypoints.put(17, DriveWaypoints.LeftCoral17);
            idAndDriveWaypoints.put(18, DriveWaypoints.LeftCoral18);
            idAndDriveWaypoints.put(19, DriveWaypoints.LeftCoral19);
            idAndDriveWaypoints.put(20, DriveWaypoints.LeftCoral20);
            idAndDriveWaypoints.put(21, DriveWaypoints.LeftCoral21);
            return idAndDriveWaypoints;
        }

        public static HashMap<Integer, DriveWaypoints> getIdsAnd134CoralWaypointsRightHashmap() {
            HashMap<Integer, DriveWaypoints> idAndDriveWaypoints = new HashMap<Integer, DriveWaypoints>();
            idAndDriveWaypoints.put(6, DriveWaypoints.RightCoral22);
            idAndDriveWaypoints.put(7, DriveWaypoints.RightCoral21);
            idAndDriveWaypoints.put(8, DriveWaypoints.RightCoral20);
            idAndDriveWaypoints.put(9, DriveWaypoints.RightCoral19);
            idAndDriveWaypoints.put(10, DriveWaypoints.RightCoral18);
            idAndDriveWaypoints.put(11, DriveWaypoints.RightCoral17);
            idAndDriveWaypoints.put(17, DriveWaypoints.RightCoral17);
            idAndDriveWaypoints.put(18, DriveWaypoints.RightCoral18);
            idAndDriveWaypoints.put(19, DriveWaypoints.RightCoral19);
            idAndDriveWaypoints.put(20, DriveWaypoints.RightCoral20);
            idAndDriveWaypoints.put(21, DriveWaypoints.RightCoral21);
            idAndDriveWaypoints.put(22, DriveWaypoints.RightCoral22);
            return idAndDriveWaypoints;

        }

        public static HashMap<Integer, DriveWaypoints> getIdsAndLevel2WaypointLeftHashmap() {
            HashMap<Integer, DriveWaypoints> idAndDriveWaypoints = new HashMap<Integer, DriveWaypoints>();
            idAndDriveWaypoints.put(6, DriveWaypoints.Level2LeftCoral22);
            idAndDriveWaypoints.put(7, DriveWaypoints.Level2LeftCoral21);
            idAndDriveWaypoints.put(8, DriveWaypoints.Level2LeftCoral20);
            idAndDriveWaypoints.put(9, DriveWaypoints.Level2LeftCoral19);
            idAndDriveWaypoints.put(10, DriveWaypoints.Level2LeftCoral18);
            idAndDriveWaypoints.put(11, DriveWaypoints.Level2LeftCoral17);
            idAndDriveWaypoints.put(17, DriveWaypoints.Level2LeftCoral17);
            idAndDriveWaypoints.put(18, DriveWaypoints.Level2LeftCoral18);
            idAndDriveWaypoints.put(19, DriveWaypoints.Level2LeftCoral19);
            idAndDriveWaypoints.put(20, DriveWaypoints.Level2LeftCoral20);
            idAndDriveWaypoints.put(21, DriveWaypoints.Level2LeftCoral21);
            idAndDriveWaypoints.put(22, DriveWaypoints.Level2LeftCoral22);
            return idAndDriveWaypoints;
        }

        public static HashMap<Integer, DriveWaypoints> getIdsAndLevel2WaypointsRightHashmap() {
            HashMap<Integer, DriveWaypoints> idAndDriveWaypoints = new HashMap<Integer, DriveWaypoints>();
            idAndDriveWaypoints.put(6, DriveWaypoints.Level2RightCoral22);
            idAndDriveWaypoints.put(7, DriveWaypoints.Level2RightCoral21);
            idAndDriveWaypoints.put(8, DriveWaypoints.Level2RightCoral20);
            idAndDriveWaypoints.put(9, DriveWaypoints.Level2RightCoral19);
            idAndDriveWaypoints.put(10, DriveWaypoints.Level2RightCoral18);
            idAndDriveWaypoints.put(11, DriveWaypoints.Level2RightCoral17);
            idAndDriveWaypoints.put(17, DriveWaypoints.Level2RightCoral17);
            idAndDriveWaypoints.put(18, DriveWaypoints.Level2RightCoral18);
            idAndDriveWaypoints.put(19, DriveWaypoints.Level2RightCoral19);
            idAndDriveWaypoints.put(20, DriveWaypoints.Level2RightCoral20);
            idAndDriveWaypoints.put(21, DriveWaypoints.Level2RightCoral21);
            idAndDriveWaypoints.put(22, DriveWaypoints.Level2RightCoral22);
            return idAndDriveWaypoints;
        }

        public static HashMap<Integer, DriveWaypoints> getIdsAndAlgaeDriveWaypointHashmap() {
            HashMap<Integer, DriveWaypoints> idAndDriveWaypoints = new HashMap<Integer, DriveWaypoints>();
            idAndDriveWaypoints.put(6, DriveWaypoints.AlgaeLevel22);
            idAndDriveWaypoints.put(7, DriveWaypoints.AlgaeLevel21);
            idAndDriveWaypoints.put(8, DriveWaypoints.AlgaeLevel20);
            idAndDriveWaypoints.put(9, DriveWaypoints.AlgaeLevel19);
            idAndDriveWaypoints.put(10, DriveWaypoints.AlgaeLevel18);
            idAndDriveWaypoints.put(11, DriveWaypoints.AlgaeLevel17);
            idAndDriveWaypoints.put(17, DriveWaypoints.AlgaeLevel17);
            idAndDriveWaypoints.put(18, DriveWaypoints.AlgaeLevel18);
            idAndDriveWaypoints.put(19, DriveWaypoints.AlgaeLevel19);
            idAndDriveWaypoints.put(20, DriveWaypoints.AlgaeLevel20);
            idAndDriveWaypoints.put(21, DriveWaypoints.AlgaeLevel21);
            idAndDriveWaypoints.put(22, DriveWaypoints.AlgaeLevel22);
            return idAndDriveWaypoints;
        }

        public static HashMap<Integer, DriveWaypoints> getIdsAndProcessorDriveWaypointHashmap() {
            HashMap<Integer, DriveWaypoints> idAndDriveWaypoints = new HashMap<Integer, DriveWaypoints>();
            idAndDriveWaypoints.put(3, DriveWaypoints.Processor3);
            idAndDriveWaypoints.put(16, DriveWaypoints.Processor16);
            return idAndDriveWaypoints;
        }

        public static HashMap<Integer, DriveWaypoints> getIdsAndFeederStationDriveWaypointHashmap() {
            HashMap<Integer, DriveWaypoints> idAndDriveWaypoints = new HashMap<Integer, DriveWaypoints>();
            idAndDriveWaypoints.put(1, DriveWaypoints.FeederStation12Point5);
            idAndDriveWaypoints.put(2, DriveWaypoints.FeederStation13Point5);
            idAndDriveWaypoints.put(12, DriveWaypoints.FeederStation12Point5);
            idAndDriveWaypoints.put(13, DriveWaypoints.FeederStation13Point5);
            return idAndDriveWaypoints;
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
