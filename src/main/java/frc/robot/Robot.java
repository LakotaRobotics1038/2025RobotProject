// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.Optional;

import org.photonvision.EstimatedRobotPose;

import com.pathplanner.lib.commands.PathfindingCommand;
import com.pathplanner.lib.config.PIDConstants;
import com.pathplanner.lib.controllers.PPHolonomicDriveController;
import com.pathplanner.lib.path.PathConstraints;
import com.pathplanner.lib.pathfinding.LocalADStar;
import com.pathplanner.lib.pathfinding.Pathfinding;

import edu.wpi.first.hal.ControlWord;
import edu.wpi.first.hal.DriverStationJNI;
import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.autons.Auton;
import frc.robot.autons.AutonSelector;
import frc.robot.commands.PathOnTheFly;
import frc.robot.constants.AutoConstants;
import frc.robot.constants.DriveConstants;
import frc.robot.constants.SwerveModuleConstants;
import frc.robot.subsystems.Dashboard;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.SwagLights;
import frc.robot.subsystems.Vision;

public class Robot extends TimedRobot {
    // Singleton Instances
    private AutonSelector autonSelector = AutonSelector.getInstance();
    private SwagLights swagLights = SwagLights.getInstance();

    // Variables
    private Auton autonomousCommand;
    private ControlWord controlWordCache = new ControlWord();

    // Subsystems
    private DriveTrain driveTrain = DriveTrain.getInstance();
    private Vision vision = Vision.getInstance();

    @Override
    public void robotInit() {
        // Singleton instances that need to be created but not referenced
        DriverJoystick.getInstance();
        OperatorJoystick.getInstance();
        Dashboard.getInstance();

        addPeriodic(swagLights::periodic, 0.25);
        PathfindingCommand.warmupCommand().schedule();
    }

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
        // Correct pose estimate with vision measurements
        Optional<EstimatedRobotPose> visionEst = vision.getEstimatedGlobalPose();
        visionEst.ifPresent(
                est -> {
                    // Change our trust in the measurement based on the tags we can see
                    Matrix<N3, N1> estStdDevs = vision.getEstimationStdDevs();

                    driveTrain.addVisionMeasurement(
                            est.estimatedPose.toPose2d(), est.timestampSeconds, estStdDevs);
                });

    }

    @Override
    public void disabledInit() {
        System.out.println("Robot Disabled");
        DriverStationJNI.getControlWord(controlWordCache);
        if (controlWordCache.getEStop()) {
            swagLights.setEStop();
        } else {
            swagLights.setDisabled(true);
        }
    }

    @Override
    public void disabledPeriodic() {
    }

    @Override
    public void disabledExit() {
        swagLights.setDisabled(false);
    }

    @Override
    public void autonomousInit() {
        driveTrain.zeroHeading();
        autonomousCommand = autonSelector.chooseAuton();
        // if (DriverStation.isFMSAttached()) {
        // vision.startRecording();
        // }

        if (autonomousCommand != null) {
            Pose2d initialPose = autonomousCommand.getInitialPose();
            if (initialPose != null) {
                driveTrain.resetOdometry(initialPose);
            }
            driveTrain.setDrivingIdleMode(SwerveModuleConstants.kAutoDrivingMotorIdleMode);
            autonomousCommand.schedule();
        }
    }

    @Override
    public void autonomousPeriodic() {
    }

    @Override
    public void autonomousExit() {
        if (autonomousCommand != null) {
            autonomousCommand.cancel();
        }
    }

    @Override
    public void teleopInit() {
        Dashboard.getInstance().clearTrajectory();
        driveTrain.setDrivingIdleMode(SwerveModuleConstants.kTeleopDrivingMotorIdleMode);
    }

    @Override
    public void teleopPeriodic() {
    }

    @Override
    public void teleopExit() {
        driveTrain.setX();
        // vision.stopRecording();
    }

    @Override
    public void testInit() {
        CommandScheduler.getInstance().cancelAll();
    }

    @Override
    public void testPeriodic() {
    }

    @Override
    public void testExit() {
    }
}
