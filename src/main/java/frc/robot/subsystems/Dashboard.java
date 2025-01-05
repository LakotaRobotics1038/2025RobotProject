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

    // Choosers
    private SendableChooser<AutonChoices> autoChooser = new SendableChooser<>();
    private SendableChooser<Double> delayChooser = new SendableChooser<>();

    // Tabs
    private final ShuffleboardTab driversTab = Shuffleboard.getTab("Drivers");
    private final ShuffleboardTab controlsTab = Shuffleboard.getTab("Controls");

    // Variables
    private final Field2d field = new Field2d();

    // Controls Tab Inputs
    private GenericEntry resetGyro = controlsTab.add("Reset Gyro", false)
            .withSize(1, 1)
            .withPosition(0, 0)
            .withWidget(BuiltInWidgets.kToggleButton)
            .getEntry();

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
            double angle = driveTrain.getHeading();
            angle %= 360;
            return angle < 0 ? angle + 360 : angle;
        })
                .withPosition(2, 0)
                .withSize(2, 1);
        // .withWidget(BuiltInWidgets.kGyro);

        controlsTab.addNumber("Roll", driveTrain::getRoll)
                .withPosition(1, 0);

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
        //         .withPosition(6, 0)
        //         .withSize(4, 4);

        controlsTab.add(field)
                .withPosition(2, 0)
                .withSize(8, 5)
                .withWidget(BuiltInWidgets.kField);
    }

    @Override
    public void periodic() {
        // Controls Tab
        if (resetGyro.getBoolean(false)) {
            driveTrain.zeroHeading();
            resetGyro.setBoolean(false);
        }
        field.setRobotPose(driveTrain.getPose());
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
}
