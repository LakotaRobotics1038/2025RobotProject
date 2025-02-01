package frc.robot.constants;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.util.Units;

public final class DriveConstants {
    // Driving Parameters - Note that these are not the maximum capable speeds of
    // the robot, rather the allowed maximum speeds
    public static final double kMaxSpeedMetersPerSecond = 6.77;
    public static final double kMaxAngularSpeed = 2 * Math.PI; // radians per second
    public static final double kFineAdjustmentPercent = 0.2;

    // Chassis configuration
    public static final double kTrackWidth = Units.inchesToMeters(22.5);
    // Distance between centers of right and left wheels on robot
    public static final double kWheelBase = Units.inchesToMeters(22.5);
    // Drive base radius in meters. Distance from robot center to furthest module.
    public static final double kBaseRadius = Units.inchesToMeters(15.91);
    // Distance between front and back wheels on robot
    public static final SwerveDriveKinematics kDriveKinematics = new SwerveDriveKinematics(
            new Translation2d(kWheelBase / 2, kTrackWidth / 2),
            new Translation2d(kWheelBase / 2, -kTrackWidth / 2),
            new Translation2d(-kWheelBase / 2, kTrackWidth / 2),
            new Translation2d(-kWheelBase / 2, -kTrackWidth / 2));

    // Angular offsets of the modules relative to the chassis in radians
    public static final double kFrontLeftChassisAngularOffset = -Math.PI / 2;
    public static final double kFrontRightChassisAngularOffset = 0;
    public static final double kBackLeftChassisAngularOffset = Math.PI;
    public static final double kBackRightChassisAngularOffset = Math.PI / 2;

    // SPARK MAX CAN IDs
    public static final int kRearLeftDrivingCanId = 18;
    public static final int kRearRightDrivingCanId = 2;
    public static final int kFrontRightDrivingCanId = 4;
    public static final int kFrontLeftDrivingCanId = 16;

    public static final int kRearLeftTurningCanId = 17;
    public static final int kRearRightTurningCanId = 1;
    public static final int kFrontRightTurningCanId = 3;
    public static final int kFrontLeftTurningCanId = 15;

    public static final boolean kGyroReversed = false;
    public static final int kGyroCanId = 25;
}