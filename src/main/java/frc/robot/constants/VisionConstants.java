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

public final class VisionConstants {
    public static final String kTableName = "Vision";
    public static final String kValuesTopic = "values";
    public static final String kRecordingTopic = "recording";
    public static final String kStreamCam0 = "shouldStream0";
    public static final String kEnabled0Topic = "enable0";
    public static final String kEnabled1Topic = "enable1";

    public static final double width = 800;
    public static final double height = 600;
    public static final double fov = 100;

    public static final double driveP = 0.005;
    public static final double driveI = 0;
    public static final double driveD = 0.0002;
    public static final double spinP = 0.005;
    public static final double spinI = 0;
    public static final double spinD = 0.0002;
    public static final double spinSetpoint = 0.0;
    public static final double aprilTagArea = 28908;

    public static final AprilTagFieldLayout kTagLayout = AprilTagFieldLayout.loadField(AprilTagFields.kDefaultField);

    private static final double kFrontCameraXOffset = 0.1;
    private static final double kFrontCameraYOffset = 0.0;
    private static final double kFrontCameraZOffset = 0.1;
    public static final Transform3d kRobotToFrontCam = new Transform3d(
            new Translation3d(kFrontCameraXOffset, kFrontCameraYOffset, kFrontCameraZOffset),
            new Rotation3d(0, 0, 0));
    public static final String kRobotToFrontCamName = "frontCamera";
    private static final double kBackCameraXOffset = 0.1;
    private static final double kBackCameraYOffset = 0.0;
    private static final double kBackCameraZOffset = 0.1;
    public static final Transform3d kRobotToBackCam = new Transform3d(
            new Translation3d(kBackCameraXOffset, kBackCameraYOffset, kBackCameraZOffset),
            new Rotation3d(0, 0, 0));
    public static final String kRobotToBackCamName = "backCamera";
    public static final Matrix<N3, N1> kSingleTagStdDevs = VecBuilder.fill(4, 4, 8);
    public static final Matrix<N3, N1> kMultiTagStdDevs = VecBuilder.fill(0.5, 0.5, 1);
}
