package frc.robot.commands;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.photonvision.targeting.PhotonPipelineResult;

import com.pathplanner.lib.util.FlippingUtil;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.OperatorState.ScoringSide;
import frc.robot.OperatorState;
import frc.robot.utils.AcquisitionPositionSetpoint;
import frc.robot.constants.AutoConstants.DriveWaypoints;
import frc.robot.subsystems.Vision;

public class MakeDetermineWaypointCommand extends Command {
    private Vision vision = Vision.getInstance();
    private int bestId = 0;
    private Optional<DriveWaypoints> waypoint;
    private boolean isMirrored;
    private OperatorState operatorState = OperatorState.getInstance();

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

        AcquisitionPositionSetpoint setpointLevel = operatorState.getLastInput();
        ScoringSide scoringSide = operatorState.getScoringSide();
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

    public Optional<DriveWaypoints> getWaypoint() {
        return this.waypoint;
    }

    public Optional<Pose2d> getPose2d() {
        return Optional.ofNullable(FlippingUtil.flipFieldPose(this.waypoint.get().getEndpoint()));
    }

    public Optional<Rotation2d> getRotation2d() {
        return Optional.ofNullable(waypoint.get().getRotation2d());
    }

    private void get134CoralWaypoint(boolean scoringSideLeft) {
        Map<Integer, Optional<DriveWaypoints>> idsAnd134CoralWaypoints = scoringSideLeft
                ? DriveWaypoints.getIdsAnd134CoralWaypointsLeftMap()
                : DriveWaypoints.getIdsAnd134CoralWaypointsRightMap();
        this.waypoint = idsAnd134CoralWaypoints.get(this.bestId);
        this.isMirrored = this.bestId <= 17;
    }

    private void getLevel2CoralWaypoint(boolean scoringSideLeft) {
        Map<Integer, Optional<DriveWaypoints>> idsAndLevel2Waypoints = scoringSideLeft
                ? DriveWaypoints.getIdsAndLevel2WaypointLeftMap()
                : DriveWaypoints.getIdsAndLevel2WaypointsRightMap();
        this.waypoint = idsAndLevel2Waypoints.get(this.bestId);
        this.isMirrored = this.bestId <= 17;
    }

    private void getAlgaeWaypoint() {
        Map<Integer, Optional<DriveWaypoints>> idsAndAlgaeWaypoints = DriveWaypoints
                .getIdsAndAlgaeDriveWaypointMap();
        this.waypoint = idsAndAlgaeWaypoints.get(this.bestId);
        this.isMirrored = this.bestId <= 10;
    }

    private void getProcessorWaypoint() {
        Map<Integer, Optional<DriveWaypoints>> idsAndProcessorWaypoints = DriveWaypoints
                .getIdsAndProcessorDriveWaypointMap();
        this.waypoint = idsAndProcessorWaypoints.get(this.bestId);
        this.isMirrored = false;
    }

    private void getFeederStationWaypoint() {
        Map<Integer, Optional<DriveWaypoints>> idsAndFeederStationWaypoints = DriveWaypoints
                .getIdsAndFeederStationDriveWaypointMap();
        this.waypoint = idsAndFeederStationWaypoints.get(this.bestId);
        this.isMirrored = this.bestId <= 2;
    }
}
