package frc.robot.commands;

import java.util.HashMap;
import java.util.List;

import org.photonvision.targeting.PhotonPipelineResult;

import com.pathplanner.lib.util.FlippingUtil;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.SetAcquisitionPositionCommand.AcquisitionPositionSetpoint;
import frc.robot.constants.AutoConstants.DriveWaypoints;
import frc.robot.subsystems.Vision;

public class MakeDetermineWaypointCommand extends Command {
    private Vision vision = Vision.getInstance();
    private int bestId = 0;
    private DriveWaypoints waypoint;
    private boolean isMirrored;

    public MakeDetermineWaypointCommand() {
        addRequirements(vision);
    }

    @Override
    public void initialize() {
        List<PhotonPipelineResult> visionResults = vision.getResults();
        double area = 0.0;
        for (PhotonPipelineResult result : visionResults) {
            if (result.hasTargets() && result.getBestTarget().getArea() > area) {
                area = result.getBestTarget().getArea();
                this.bestId = result.getBestTarget().getFiducialId();
            }
        }

        AcquisitionPositionSetpoint setpointLevel = OperatorState.getLastInput();
        ScoringSide scoringSide = OperatorState.getScoringSide();
        boolean scoringSideLeft = scoringSide == ScoringSide.LEFT;
        switch (setpointLevel) {
            case L1Coral:
            case L3Coral:
            case L4Coral:
                get134CoralWaypoint(scoringSideLeft);
                break;
            case L2Coral:
                getLevel2CoralWaypoint(scoringSideLeft);
                break;
            case L23Algae:
            case L34Algae:
                getAlgaeWaypoint();
                break;
            case Processor:
                getProcessorWaypoint();
                break;
            case FeederStation:
                getFeederStationWaypoint();
                break;
            default:
                break;
        }

    }

    @Override
    public boolean isFinished() {
        return true;
    }

    public int getBestID() {
        return this.bestId;
    }

    public boolean getIsMirrored() {
        return this.isMirrored;
    }

    public DriveWaypoints getWaypoint() {
        return this.waypoint;
    }

    public Pose2d getPose2d() {
        return FlippingUtil.flipFieldPose(this.waypoint.getEndpoint());
    }

    public Rotation2d getRotation2d() {
        return waypoint.getRotation2d();
    }

    private void get134CoralWaypoint(boolean scoringSideLeft) {
        HashMap<Integer, DriveWaypoints> idsAnd134CoralWaypoints = scoringSideLeft
                ? DriveWaypoints.getIdsAnd134CoralWaypointsLeftHashmap()
                : DriveWaypoints.getIdsAnd134CoralWaypointsRightHashmap();
        this.waypoint = idsAnd134CoralWaypoints.get(this.bestId);
        this.isMirrored = this.bestId <= 17;
    }

    private void getLevel2CoralWaypoint(boolean scoringSideLeft) {
        HashMap<Integer, DriveWaypoints> idsAndLevel2Waypoints = scoringSideLeft
                ? DriveWaypoints.getIdsAndLevel2WaypointLeftHashmap()
                : DriveWaypoints.getIdsAndLevel2WaypointsRightHashmap();
        this.waypoint = idsAndLevel2Waypoints.get(this.bestId);
        this.isMirrored = this.bestId <= 17;
    }

    private void getAlgaeWaypoint() {
        HashMap<Integer, DriveWaypoints> idsAndAlgaeWaypoints = DriveWaypoints.getIdsAndAlgaeDriveWaypointHashmap();
        this.waypoint = idsAndAlgaeWaypoints.get(this.bestId);
        this.isMirrored = this.bestId <= 10;
    }

    private void getProcessorWaypoint() {
        HashMap<Integer, DriveWaypoints> idsAndProcessorWaypoints = DriveWaypoints
                .getIdsAndProcessorDriveWaypointHashmap();
        this.waypoint = idsAndProcessorWaypoints.get(this.bestId);
        this.isMirrored = false;
    }

    private void getFeederStationWaypoint() {
        HashMap<Integer, DriveWaypoints> idsAndFeederStationWaypoints = DriveWaypoints
                .getIdsAndFeederStationDriveWaypointHashmap();
        this.waypoint = idsAndFeederStationWaypoints.get(this.bestId);
        this.isMirrored = this.bestId <= 2;
    }
}
