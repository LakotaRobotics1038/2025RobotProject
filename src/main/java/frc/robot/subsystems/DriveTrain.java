// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.Optional;

import org.photonvision.PhotonCamera;
import org.photonvision.PhotonUtils;
import org.photonvision.targeting.PhotonTrackedTarget;

import com.ctre.phoenix6.hardware.Pigeon2;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.constants.DriveConstants;
import frc.robot.libraries.MAXSwerveModule;

public class DriveTrain extends SubsystemBase {
    // Create MAXSwerveModules
    private final MAXSwerveModule frontLeft = new MAXSwerveModule(
            DriveConstants.kFrontLeftDrivingCanId,
            DriveConstants.kFrontLeftTurningCanId,
            DriveConstants.kFrontLeftChassisAngularOffset);

    private final MAXSwerveModule frontRight = new MAXSwerveModule(
            DriveConstants.kFrontRightDrivingCanId,
            DriveConstants.kFrontRightTurningCanId,
            DriveConstants.kFrontRightChassisAngularOffset);

    private final MAXSwerveModule rearLeft = new MAXSwerveModule(
            DriveConstants.kRearLeftDrivingCanId,
            DriveConstants.kRearLeftTurningCanId,
            DriveConstants.kBackLeftChassisAngularOffset);

    private final MAXSwerveModule rearRight = new MAXSwerveModule(
            DriveConstants.kRearRightDrivingCanId,
            DriveConstants.kRearRightTurningCanId,
            DriveConstants.kBackRightChassisAngularOffset);

    // The gyro sensor
    private final Pigeon2 gyro = new Pigeon2(DriveConstants.kGyroCanId);

    private PhotonCamera camera = new PhotonCamera("cameraName");

    private AprilTagFieldLayout aprilTagFieldLayout = AprilTagFieldLayout.loadField(AprilTagFields.kDefaultField);

    // Odometry class for tracking robot pose
    private SwerveDriveOdometry odometry = new SwerveDriveOdometry(
            DriveConstants.kDriveKinematics,
            Rotation2d.fromDegrees(-gyro.getYaw().getValueAsDouble()),
            new SwerveModulePosition[] {
                    frontLeft.getPosition(),
                    frontRight.getPosition(),
                    rearLeft.getPosition(),
                    rearRight.getPosition()
            });
    // Vision Estimated Position
    private Pose3d estimatedPose;

    // Singleton Setup
    private static DriveTrain instance;

    public static DriveTrain getInstance() {
        if (instance == null) {
            System.out.println("Creating a new DriveTrain");
            instance = new DriveTrain();
        }
        return instance;
    }

    private DriveTrain() {
        super();
        gyro.reset();
    }

    @Override
    public void periodic() {
        odometry.update(
                Rotation2d.fromDegrees(-gyro.getYaw().getValueAsDouble()),
                new SwerveModulePosition[] {
                        frontLeft.getPosition(),
                        frontRight.getPosition(),
                        rearLeft.getPosition(),
                        rearRight.getPosition()
                });

        var results = camera.getAllUnreadResults();
        PhotonTrackedTarget target = results.get(results.size() - 1).getBestTarget();

        int targetId = target.getFiducialId();
        Optional<Pose3d> poseAprilTagField = aprilTagFieldLayout.getTagPose(targetId);

        if (poseAprilTagField.isPresent()) {
            estimatedPose = PhotonUtils.estimateFieldToRobotAprilTag(target.getBestCameraToTarget(),
                    poseAprilTagField.get(), DriveConstants.cameraToRobot);
        }
    }

    public Pose3d getEstimatedPose() {
        return this.estimatedPose;
    }

    /**
     * Returns the currently-estimated pose of the robot.
     *
     * @return The pose.
     */
    public Pose2d getPose() {
        return odometry.getPoseMeters();
    }

    /**
     * Resets the odometry to the specified pose.
     *
     * @param pose The pose to which to set the odometry.
     */
    public void resetOdometry(Pose2d pose) {
        odometry.resetPosition(
                Rotation2d.fromDegrees(-gyro.getYaw().getValueAsDouble()),
                new SwerveModulePosition[] {
                        frontLeft.getPosition(),
                        frontRight.getPosition(),
                        rearLeft.getPosition(),
                        rearRight.getPosition()
                },
                pose);
    }

    /**
     * Method to drive the robot using joystick info.
     *
     * @param xSpeed        Speed of the robot in the x direction (forward).
     * @param ySpeed        Speed of the robot in the y direction (sideways).
     * @param rot           Angular rate of the robot.
     * @param fieldRelative Whether the provided x and y speeds are relative to the
     *                      field.
     */
    public void drive(double xSpeed, double ySpeed, double rot, boolean fieldRelative) {
        // Adjust input based on max speed
        xSpeed *= DriveConstants.kMaxSpeedMetersPerSecond;
        ySpeed *= DriveConstants.kMaxSpeedMetersPerSecond;
        rot *= DriveConstants.kMaxAngularSpeed;

        this.applyChassisSpeeds(
                fieldRelative
                        ? ChassisSpeeds.fromFieldRelativeSpeeds(xSpeed, ySpeed, rot,
                                Rotation2d.fromDegrees(-gyro.getYaw().getValueAsDouble()))
                        : new ChassisSpeeds(xSpeed, ySpeed, rot));
    }

    public void applyChassisSpeeds(ChassisSpeeds speeds) {
        SwerveModuleState[] swerveModuleStates = DriveConstants.kDriveKinematics.toSwerveModuleStates(speeds);
        this.setModuleStates(swerveModuleStates);
    }

    /**
     * Gets the actual chassis speeds
     *
     * @return actual chassis speeds
     */
    public ChassisSpeeds getChassisSpeeds() {
        return DriveConstants.kDriveKinematics.toChassisSpeeds(getModuleStates());
    }

    /**
     * Sets the wheels into an X formation to prevent movement.
     */
    public void setX() {
        frontLeft.setDesiredState(new SwerveModuleState(0, Rotation2d.fromDegrees(45)));
        frontRight.setDesiredState(new SwerveModuleState(0, Rotation2d.fromDegrees(-45)));
        rearLeft.setDesiredState(new SwerveModuleState(0, Rotation2d.fromDegrees(-45)));
        rearRight.setDesiredState(new SwerveModuleState(0, Rotation2d.fromDegrees(45)));
    }

    /**
     * Sets the swerve ModuleStates.
     *
     * @param desiredStates The desired SwerveModule states.
     */
    public void setModuleStates(SwerveModuleState[] desiredStates) {
        SwerveDriveKinematics.desaturateWheelSpeeds(
                desiredStates, DriveConstants.kMaxSpeedMetersPerSecond);
        frontLeft.setDesiredState(desiredStates[0]);
        frontRight.setDesiredState(desiredStates[1]);
        rearLeft.setDesiredState(desiredStates[2]);
        rearRight.setDesiredState(desiredStates[3]);
    }

    /**
     * Gets the swerve ModuleStates.
     *
     * @return The current SwerveModule states.
     */
    public SwerveModuleState[] getModuleStates() {
        return new SwerveModuleState[] {
                frontLeft.getState(),
                frontRight.getState(),
                rearLeft.getState(),
                rearRight.getState(),
        };
    }

    public void setDrivingIdleMode(IdleMode mode) {
        frontLeft.setDrivingIdleMode(mode);
        rearLeft.setDrivingIdleMode(mode);
        frontRight.setDrivingIdleMode(mode);
        rearRight.setDrivingIdleMode(mode);
    }

    /** Resets the drive encoders to currently read a position of 0. */
    public void resetEncoders() {
        frontLeft.resetEncoders();
        rearLeft.resetEncoders();
        frontRight.resetEncoders();
        rearRight.resetEncoders();
    }

    /** Zeroes the heading of the robot. */
    public void zeroHeading() {
        gyro.reset();
    }

    /**
     * Returns the heading of the robot.
     *
     * @return the robot's heading in degrees, from -180 to 180
     */
    public double getHeading() {
        return Rotation2d.fromDegrees(-gyro.getYaw().getValueAsDouble()).getDegrees();
    }

    /**
     * Returns the turn rate of the robot.
     *
     * @return The turn rate of the robot, in degrees per second
     */
    public double getTurnRate() {
        return gyro.getAngularVelocityZWorld().getValueAsDouble() * (DriveConstants.kGyroReversed ? -1.0 : 1.0);
    }

    /**
     * Returns the roll value of the robot.
     *
     * @return the robot's roll in degrees, from 0 to 360
     */
    public double getRoll() {
        return -gyro.getRoll().getValueAsDouble();
    }

    /**
     * Returns the pitch value of the robot.
     *
     * @return the robot's pitch in degrees, from ? to ?
     */
    public double getPitch() {
        return gyro.getPitch().getValueAsDouble();
    }
}