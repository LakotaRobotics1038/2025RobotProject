package frc.robot.constants;

public final class VisionConstants {
    public static final String kTableName = "Vision";
    public static final String kValuesTopic = "values";
    public static final String kRecordingTopic = "recording";
    public static final String kStreamCam0 = "shouldStream0";
    public static final String kEnabled0Topic = "enable0";
    public static final String kEnabled1Topic = "enable1";

    public static final double width = 1280;
    public static final double height = 720;
    public static final double fov = 100;

    public static final double driveP = 0.005;
    public static final double driveI = 0;
    public static final double driveD = 0.0002;
    public static final double spinP = 0.005;
    public static final double spinI = 0;
    public static final double spinD = 0.0002;
    public static final double spinSetpoint = 0.0;
    public static final double aprilTagArea = 28908;
}
