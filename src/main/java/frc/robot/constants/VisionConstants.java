package frc.robot.constants;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.math.util.Units;

public final class VisionConstants {
    public static final String kTableName = "Vision";
    public static final String kValuesTopic = "values";
    public static final String kRecordingTopic = "recording";
    public static final String kStreamCam0 = "shouldStream0";
    public static final String kEnabled0Topic = "enable0";
    public static final String kEnabled1Topic = "enable1";

    public static final double kWidth = 800;
    public static final double kHeight = 600;
    public static final double kFov = 100;

    public static final double kDriveP = 0.005;
    public static final double kDriveI = 0;
    public static final double kDriveD = 0.0002;
    public static final double kSpinP = 0.005;
    public static final double kSpinI = 0;
    public static final double kSpinD = 0.0002;
    public static final double kSpinSetpoint = 0.0;
    public static final double kAprilTagArea = 28908;

    public static final AprilTagFieldLayout kTagLayout = AprilTagFieldLayout.loadField(AprilTagFields.kDefaultField);

    private static final double kFrontCameraXOffset = Units.inchesToMeters(13.730);
    private static final double kFrontCameraYOffset = Units.inchesToMeters(-11.995);
    private static final double kFrontCameraZOffset = Units.inchesToMeters(7.35);
    public static final Transform3d kRobotToFrontCam = new Transform3d(
            new Translation3d(kFrontCameraXOffset, kFrontCameraYOffset, kFrontCameraZOffset),
            new Rotation3d(0, 0, 0));
    public static final String kRobotToFrontCamName = "frontCamera";

    private static final double kBackCameraXOffset = Units.inchesToMeters(-10.5277);
    private static final double kBackCameraYOffset = Units.inchesToMeters(-11.391);
    private static final double kBackCameraZOffset = Units.inchesToMeters(7.6);
    public static final Transform3d kRobotToBackCam = new Transform3d(
            new Translation3d(kBackCameraXOffset, kBackCameraYOffset, kBackCameraZOffset),
            new Rotation3d(0, 0, Math.toRadians(180 - 45)));
    public static final String kRobotToBackCamName = "backCamera";

    public static final Matrix<N3, N1> kSingleTagStdDevs = VecBuilder.fill(4, 4, 8);
    public static final Matrix<N3, N1> kMultiTagStdDevs = VecBuilder.fill(0.5, 0.5, 1);
}
