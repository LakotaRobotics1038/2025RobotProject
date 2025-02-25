package frc.robot.constants;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;

public enum DriveWaypoints {
    LeftCoral1(new Pose2d(new Translation2d(0, 0), new Rotation2d(0))),
    LeftCoral2(new Pose2d(new Translation2d(3.53, 5.1), new Rotation2d(Math.toRadians(120.0)))),
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
    LeftFeederStation4(new Pose2d(new Translation2d(1.42, 7.41), new Rotation2d(Math.toRadians(-55.0)))),
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
