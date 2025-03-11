package frc.robot.subsystems;

import java.util.ArrayList;

import com.pathplanner.lib.util.PathPlannerLogging;

import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.autons.AutonSelector.AutonChoices;

public class Dashboard extends SubsystemBase {
    // Inputs
    private DriveTrain driveTrain = DriveTrain.getInstance();
    private Climb climb = Climb.getInstance();
    private Wrist wrist = Wrist.getInstance();
    private Shoulder shoulder = Shoulder.getInstance();
    private Extension extension = Extension.getInstance();

    // Choosers
    private SendableChooser<AutonChoices> autoChooser = new SendableChooser<>();
    private SendableChooser<Double> delayChooser = new SendableChooser<>();

    // Tabs
    private final ShuffleboardTab driversTab = Shuffleboard.getTab("Drivers");
    private final ShuffleboardTab controlsTab = Shuffleboard.getTab("Controls");

    // Driver Tab Inputs
    private GenericEntry extensionOffset = driversTab.add("Extension Offset", 0)
            .withPosition(2, 1)
            .withSize(2, 1)
            .withWidget(BuiltInWidgets.kTextView)
            .getEntry();

    private GenericEntry shoulderOffset = driversTab.add("Shoulder Offset", 0)
            .withPosition(2, 2)
            .withSize(2, 1)
            .withWidget(BuiltInWidgets.kTextView)
            .getEntry();

    private GenericEntry wristOffset = driversTab.add("Wrist Offset", 0)
            .withPosition(2, 3)
            .withSize(2, 1)
            .withWidget(BuiltInWidgets.kTextView)
            .getEntry();

    // Variables
    private final Field2d field = new Field2d();

    // Singleton Setup
    private static Dashboard instance;

    public static Dashboard getInstance() {
        if (instance == null) {
            System.out.println("Creating a new Dashboard");
            instance = new Dashboard();
        }
        return instance;
    }

    private Dashboard() {
        super();

        driversTab.add("Auton Choices", autoChooser)
                .withPosition(0, 0)
                .withSize(2, 1);

        driversTab.add("Delay Choices", delayChooser)
                .withPosition(0, 1)
                .withSize(2, 1);

        driversTab.addNumber("Gyro", () -> {
            double angle = driveTrain.getState().RawHeading.getDegrees();
            angle %= 360;
            return angle < 0 ? angle + 360 : angle;
        })
                .withPosition(2, 0)
                .withSize(2, 1);
        // .withWidget(BuiltInWidgets.kGyro);

        driversTab.add(field)
                .withPosition(2, 1)
                .withSize(4, 3)
                .withWidget(BuiltInWidgets.kField);

        PathPlannerLogging.setLogTargetPoseCallback((pose) -> {
            field.getObject("target pose").setPose(pose);
        });

        PathPlannerLogging.setLogActivePathCallback((poses) -> {
            field.getObject("poses").setPoses(poses);
        });

        // driversTab.add("Camera Stream", camera)
        // .withPosition(6, 0)
        // .withSize(4, 4);

        controlsTab.add(field)
                .withPosition(2, 0)
                .withSize(8, 5)
                .withWidget(BuiltInWidgets.kField);

        controlsTab.addNumber("X", driveTrain::getX)
                .withPosition(5, 5)
                .withSize(2, 1);
        controlsTab.addNumber("Y", driveTrain::getY)
                .withPosition(5, 4)
                .withSize(2, 1);
        controlsTab.addNumber("R", driveTrain::getRotation)
                .withPosition(5, 4)
                .withSize(2, 1);
        controlsTab.addNumber("Climb", climb::getPosition)
                .withPosition(3, 3)
                .withSize(2, 1);
    }

    @Override
    public void periodic() {
        // Controls Tab
        field.setRobotPose(driveTrain.getState().Pose);
        wrist.setOffset(wristOffset.getDouble(0));
        shoulder.setOffset(shoulderOffset.getDouble(0));
        extension.setOffset(extensionOffset.getDouble(0));
    }

    /**
     * Puts the given {@link Trajectory} on the dashboard
     *
     * @param trajectory
     */
    public void setTrajectory(Trajectory trajectory) {
        this.field.getObject("traj").setTrajectory(trajectory);
    }

    /**
     * Removes the trajectory line from the dashboard
     */
    public void clearTrajectory() {
        this.field.getObject("traj").setPoses(new ArrayList<>());
    }

    /**
     * Gets the sendable chooser for Auton Modes
     *
     * @return
     */
    public SendableChooser<AutonChoices> getAutoChooser() {
        return autoChooser;
    }

    public SendableChooser<Double> getDelayChooser() {
        return delayChooser;
    }

    public double getExtensionOffset() {
        return this.extensionOffset.getDouble(0);
    }

    public double getWristOffset() {
        return this.wristOffset.getDouble(0);
    }

    public double getShoulderOffset() {
        return this.shoulderOffset.getDouble(0);
    }
}
