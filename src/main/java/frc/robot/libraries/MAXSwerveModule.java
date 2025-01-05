// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.libraries;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;

import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.REVLibError;
import com.revrobotics.RelativeEncoder;

import frc.robot.constants.SwerveModuleConstants;

public class MAXSwerveModule {
    private final SparkMax drivingSpark;
    private final SparkMax turningSpark;

    private final RelativeEncoder drivingEncoder;
    private final AbsoluteEncoder turningEncoder;

    private final SparkClosedLoopController drivingClosedLoopController;
    private final SparkClosedLoopController turningClosedLoopController;

    private double chassisAngularOffset = 0;
    private SwerveModuleState desiredState = new SwerveModuleState(0.0, new Rotation2d());

    /**
     * Constructs a MAXSwerveModule and configures the driving and turning motor,
     * encoder, and PID controller. This configuration is specific to the REV
     * MAXSwerve Module built with NEOs, SPARKS MAX, and a Through Bore
     * Encoder.
     */
    public MAXSwerveModule(int drivingCANId, int turningCANId, double chassisAngularOffset) {
        drivingSpark = new SparkMax(drivingCANId, MotorType.kBrushless);
        turningSpark = new SparkMax(turningCANId, MotorType.kBrushless);

        SparkMaxConfig drivingConfig = new SparkMaxConfig();
        SparkMaxConfig turningConfig = new SparkMaxConfig();

        drivingEncoder = drivingSpark.getEncoder();
        turningEncoder = turningSpark.getAbsoluteEncoder();

        drivingClosedLoopController = drivingSpark.getClosedLoopController();
        turningClosedLoopController = turningSpark.getClosedLoopController();

        drivingConfig
                .idleMode(IdleMode.kBrake)
                .smartCurrentLimit(SwerveModuleConstants.kDrivingMotorCurrentLimit);
        drivingConfig.encoder
                .positionConversionFactor(SwerveModuleConstants.kDrivingEncoderPositionFactor)
                .velocityConversionFactor(SwerveModuleConstants.kDrivingEncoderVelocityFactor);
        drivingConfig.closedLoop
                .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
                .pid(SwerveModuleConstants.kDrivingP, SwerveModuleConstants.kDrivingI, SwerveModuleConstants.kDrivingD)
                .velocityFF(SwerveModuleConstants.kDrivingFF)
                .outputRange(SwerveModuleConstants.kDrivingMinOutput, SwerveModuleConstants.kDrivingMaxOutput);

        turningConfig
                .idleMode(IdleMode.kBrake)
                .smartCurrentLimit(SwerveModuleConstants.kTurningMotorCurrentLimit);
        turningConfig.absoluteEncoder
                // Invert the turning encoder, since the output shaft rotates in the opposite
                // direction of the steering motor in the MAXSwerve Module.
                .inverted(true)
                .positionConversionFactor(SwerveModuleConstants.kTurningEncoderPositionFactor)
                .velocityConversionFactor(SwerveModuleConstants.kTurningEncoderVelocityFactor);
        turningConfig.closedLoop
                .feedbackSensor(FeedbackSensor.kAbsoluteEncoder)
                .pid(SwerveModuleConstants.kTurningP, SwerveModuleConstants.kTurningI, SwerveModuleConstants.kTurningD)
                .outputRange(SwerveModuleConstants.kTurningMinOutput, SwerveModuleConstants.kTurningMaxOutput)
                // Enable PID wrap around for the turning motor. This will allow the PID
                // controller to go through 0 to get to the setpoint i.e. going from 350 degrees
                // to 10 degrees will go through 0 rather than the other direction which is a
                // longer route.
                .positionWrappingEnabled(true)
                .positionWrappingInputRange(0, SwerveModuleConstants.kTurningEncoderPositionFactor);

        // Apply the respective configurations to the SPARKS. Reset parameters before
        // applying the configuration to bring the SPARK to a known good state. Persist
        // the settings to the SPARK to avoid losing them on a power cycle.
        drivingSpark.configure(drivingConfig, ResetMode.kResetSafeParameters,
                PersistMode.kPersistParameters);
        turningSpark.configure(turningConfig, ResetMode.kResetSafeParameters,
                PersistMode.kPersistParameters);

        this.chassisAngularOffset = chassisAngularOffset;
        desiredState.angle = new Rotation2d(turningEncoder.getPosition());
        drivingEncoder.setPosition(0);
    }

    /**
     * Sets the idle mode of the module's driving spark max
     *
     * @param mode The desired idle mode
     * @return The status of the requested action
     */
    public REVLibError setDrivingIdleMode(IdleMode mode) {
        SparkMaxConfig config = new SparkMaxConfig();
        config.idleMode(SparkBaseConfig.IdleMode.kBrake);
        return drivingSpark.configure(config, SparkBase.ResetMode.kNoResetSafeParameters, SparkBase.PersistMode.kPersistParameters);
    }

    /**
     * Returns the current state of the module.
     *
     * @return The current state of the module.
     */
    public SwerveModuleState getState() {
        // Apply chassis angular offset to the encoder position to get the position
        // relative to the chassis.
        return new SwerveModuleState(drivingEncoder.getVelocity(),
                new Rotation2d(turningEncoder.getPosition() - chassisAngularOffset));
    }

    /**
     * Returns the current position of the module.
     *
     * @return The current position of the module.
     */
    public SwerveModulePosition getPosition() {
        // Apply chassis angular offset to the encoder position to get the position
        // relative to the chassis.
        return new SwerveModulePosition(
                drivingEncoder.getPosition(),
                new Rotation2d(turningEncoder.getPosition() - chassisAngularOffset));
    }

    /**
     * Sets the desired state for the module.
     *
     * @param desiredState Desired state with speed and angle.
     */
    public void setDesiredState(SwerveModuleState desiredState) {
        // Apply chassis angular offset to the desired state.
        SwerveModuleState correctedDesiredState = new SwerveModuleState();
        correctedDesiredState.speedMetersPerSecond = desiredState.speedMetersPerSecond;
        correctedDesiredState.angle = desiredState.angle.plus(Rotation2d.fromRadians(chassisAngularOffset));

        // Optimize the reference state to avoid spinning further than 90 degrees.
        correctedDesiredState.optimize(new Rotation2d(turningEncoder.getPosition()));

        // Command driving and turning SPARKS towards their respective setpoints.
        drivingClosedLoopController.setReference(correctedDesiredState.speedMetersPerSecond, ControlType.kVelocity);
        turningClosedLoopController.setReference(correctedDesiredState.angle.getRadians(), ControlType.kPosition);

        this.desiredState = desiredState;
    }

    /** Zeroes all the SwerveModule encoders. */
    public void resetEncoders() {
        drivingEncoder.setPosition(0);
    }
}