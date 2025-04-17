package frc.robot.subsystems;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import com.pathplanner.lib.util.PathPlannerLogging;

import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.autons.AutonSelector.AutonChoices;
import frc.robot.constants.DashboardConstants;

public class Dashboard extends SubsystemBase {
    // Inputs
    private DriveTrain driveTrain = DriveTrain.getInstance();
    private Wrist wrist = Wrist.getInstance();
    private Shoulder shoulder = Shoulder.getInstance();
    private Extension extension = Extension.getInstance();
    private Vision vision = Vision.getInstance();
    private double greenLightThreshold;
    private double yellowLightThreshold;
    private double redLightThreshold;

    // Choosers
    private SendableChooser<AutonChoices> autoChooser = new SendableChooser<>();
    private SendableChooser<Double> delayChooser = new SendableChooser<>();

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

        SmartDashboard.putNumber(DashboardConstants.kExtensionOffset, 0);
        SmartDashboard.putNumber(DashboardConstants.kShoulderOffset, 0);
        SmartDashboard.putNumber(DashboardConstants.kWristOffset, 0);

        SmartDashboard.putData(DashboardConstants.kAutonChoices, autoChooser);
        SmartDashboard.putData(DashboardConstants.kDelayChoices, delayChooser);

        PathPlannerLogging.setLogTargetPoseCallback((pose) -> {
            field.getObject("target pose").setPose(pose);
        });

        PathPlannerLogging.setLogActivePathCallback((poses) -> {
            field.getObject("poses").setPoses(poses);
        });
    }

    @Override
    public void periodic() {
        // Controls Tab
        SmartDashboard.putNumber(DashboardConstants.kRobotX, driveTrain.getX());
        SmartDashboard.putNumber(DashboardConstants.kRobotY, driveTrain.getY());
        SmartDashboard.putNumber(DashboardConstants.kRobotRot, driveTrain.getRotation());

        field.setRobotPose(driveTrain.getState().Pose);

        // Drivers tab
        wrist.setOffset(SmartDashboard.getNumber(DashboardConstants.kWristOffset, 0));
        shoulder.setOffset(SmartDashboard.getNumber(DashboardConstants.kShoulderOffset, 0));
        extension.setOffset(SmartDashboard.getNumber(DashboardConstants.kExtensionOffset, 0));

        SmartDashboard.putNumber(DashboardConstants.kExtensionCurrent, extension.getPosition());
        SmartDashboard.putNumber(DashboardConstants.kShoulderCurrent, shoulder.getPosition());
        SmartDashboard.putNumber(DashboardConstants.kWristCurrent, wrist.getPosition());

        if (vision.getPipelineIndex() == 0) {
            List<PhotonPipelineResult> camResults = vision.getResultsBackCam();
            Optional<PhotonTrackedTarget> bestTarget = Optional.empty();
            for (PhotonPipelineResult result : camResults) {
                if (result.hasTargets()) {
                    bestTarget = Optional.of(result.getBestTarget());
                }
            }
            if (Optional.of(bestTarget).orElse(null) != null) {
                double pitch = Math.abs(bestTarget.get().getPitch());
                if (pitch > redLightThreshold) {
                    SmartDashboard.putString(DashboardConstants.kStoplight, DashboardConstants.kRed.toHexString());
                } else if (pitch < redLightThreshold && pitch > yellowLightThreshold) {
                    SmartDashboard.putString(DashboardConstants.kStoplight, DashboardConstants.kYellow.toHexString());
                } else if (pitch < greenLightThreshold) {
                    SmartDashboard.putString(DashboardConstants.kStoplight, DashboardConstants.kGreen.toHexString());
                }
            }
        }
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
