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
    public static final Transform3d kRobotToCam = new Transform3d(new Translation3d(0.1, 0.0, 0.1),
            new Rotation3d(0, 0, 0));
    public static final Matrix<N3, N1> kSingleTagStdDevs = VecBuilder.fill(4, 4, 8);
    public static final Matrix<N3, N1> kMultiTagStdDevs = VecBuilder.fill(0.5, 0.5, 1);
}
