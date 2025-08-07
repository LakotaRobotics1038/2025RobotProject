// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.SignalLogger;
import com.pathplanner.lib.commands.FollowPathCommand;

import edu.wpi.first.hal.ControlWord;
import edu.wpi.first.hal.DriverStationJNI;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.net.WebServer;
import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.DoubleTopic;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.autons.Auton;
import frc.robot.autons.AutonSelector;
import frc.robot.constants.SwerveConstants;
import frc.robot.subsystems.Dashboard;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.SwagLights;
import frc.robot.subsystems.Vision;

public class Robot extends TimedRobot {
    // Singleton Instances
    private AutonSelector autonSelector = AutonSelector.getInstance();
    private SwagLights swagLights = SwagLights.getInstance();
    private NetworkTableInstance inst;
    private NetworkTable table;
    private DoublePublisher unfilteredJoystickY;
    private DoublePublisher unfilteredJoystickRotation;

    // Variables
    private Auton autonomousCommand;
    private ControlWord controlWordCache = new ControlWord();

    // Subsystems
    private DriveTrain driveTrain = DriveTrain.getInstance();
    private Vision vision = Vision.getInstance();

    // Driver Joystick Reference
    private DriverJoystick joystick = DriverJoystick.getInstance();

    // Human Interface Devices
    private OperatorPanel operatorPanel = OperatorPanel.getInstance();

    @Override
    public void robotInit() {
        // Singleton instances that need to be created but not referenced
        DriverJoystick.getInstance();
        OperatorPanel.getInstance();
        Dashboard.getInstance();
        inst = NetworkTableInstance.getDefault();
        table = inst.getTable("joystickinputs");
        unfilteredJoystickY = table.getDoubleTopic("UnfilteredJoystickYForwardValue").publish();
        unfilteredJoystickRotation = table.getDoubleTopic("UnfilteredJoystickRotationValue").publish();
        unfilteredJoystickY.setDefault(0);
        unfilteredJoystickRotation.setDefault(0);
        // PathfindingCommand.warmupCommand().schedule();
        FollowPathCommand.warmupCommand().schedule();

        WebServer.start(5800, Filesystem.getDeployDirectory().getPath());

        addPeriodic(swagLights::periodic, 0.25);
    }

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();

        vision.frontCamGetEstimatedGlobalPose().ifPresent(estimatedPose -> {
            driveTrain.addVisionMeasurement(estimatedPose.estimatedPose.toPose2d(),
                    estimatedPose.timestampSeconds,
                    vision.getEstimationStdDevs());
        });

        vision.backCamGetEstimatedGlobalPose().ifPresent(estimatedPose -> {
            driveTrain.addVisionMeasurement(estimatedPose.estimatedPose.toPose2d(),
                    estimatedPose.timestampSeconds,
                    vision.getEstimationStdDevs());
        });
        unfilteredJoystickY.set(joystick.getUnfilteredForwardValue());
        unfilteredJoystickRotation.set(joystick.getUnfilteredRotationValue());

    }

    @Override
    public void disabledInit() {
        SignalLogger.stop();
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
        operatorPanel.clearDefaults();
        autonomousCommand = autonSelector.chooseAuton();
        // if (DriverStation.isFMSAttached()) {
        // vision.startRecording();
        // }

        if (autonomousCommand != null) {
            Pose2d initialPose = autonomousCommand.getInitialPose();
            if (initialPose != null) {
                driveTrain.resetPose(initialPose);
            }
            driveTrain.configNeutralMode(SwerveConstants.kAutonDrivingMotorNeutralMode);
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
        driveTrain.configNeutralMode(SwerveConstants.kTeleopDrivingMotorNeutralMode);
        SignalLogger.start();

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
