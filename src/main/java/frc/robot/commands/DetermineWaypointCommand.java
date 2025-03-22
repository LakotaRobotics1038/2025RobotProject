package frc.robot.commands;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import com.pathplanner.lib.util.FlippingUtil;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.OperatorState;
import frc.robot.constants.AutoConstants.DriveWaypoints;
import frc.robot.subsystems.Vision;
import frc.robot.utils.AcquisitionPositionSetpoint;

public class DetermineWaypointCommand extends Command {
    private final Vision vision = Vision.getInstance();
    private final OperatorState operatorState = OperatorState.getInstance();
    private List<PhotonPipelineResult> visionResults;
    private int bestId = -1;
    private PhotonTrackedTarget bestAlgae;
    private Optional<DriveWaypoints> waypoint = Optional.empty();
    private boolean isMirrored;

    public DetermineWaypointCommand() {
        addRequirements(vision);
    }

    @Override
    public void initialize() {
        int[] reefIDs = { 6, 7, 8, 9, 10, 11, 17, 18, 19, 20, 21, 22 };
        int[] processorIDs = { 3, 16 };
        AcquisitionPositionSetpoint setpointLevel = operatorState.getLastInput();
        boolean scoringFlipped = operatorState.isScoringFlipped();

        switch (setpointLevel) {
            case L34Algae:
            case L23Algae:
            case Processor:
                this.visionResults = vision.getResultsBackCam();
                break;
            default:
                break;
        }

        switch (setpointLevel) {
            case L23Algae:
            case L34Algae:
                getBestTarget(reefIDs, this.visionResults);
                break;
            case Processor:
                getBestTarget(processorIDs, this.visionResults);
            default:
                break;
        }

        switch (setpointLevel) {
            case L23Algae:
            case L34Algae:
                this.waypoint = getAlgaeWaypoint(scoringFlipped);
                break;
            case Processor:
                this.waypoint = getProcessorWaypoint(scoringFlipped);
                break;
            case Storage:
                this.waypoint = Optional.empty();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    public Optional<Pose2d> getPose2d() {
        return isMirrored
                ? this.waypoint.map(driveWaypoints -> FlippingUtil.flipFieldPose(driveWaypoints.getEndpoint()))
                : this.waypoint.map(DriveWaypoints::getEndpoint);
    }

    private void getBestTarget(int[] ids, List<PhotonPipelineResult> visionResults) {
        double area = 0.0;
        for (PhotonPipelineResult result : visionResults) {
            if (result.hasTargets()) {
                for (PhotonTrackedTarget photonTarget : result.getTargets()) {
                    int targetId = photonTarget.getFiducialId();
                    System.out.println(targetId);
                    if (Arrays.stream(ids).anyMatch(x -> x == targetId) && photonTarget.getArea() > area) {
                        area = photonTarget.getArea();
                        this.bestId = targetId;
                        System.out.println(bestId);
                    }
                }
            }
        }
    }

    private Optional<DriveWaypoints> getAlgaeWaypoint(boolean scoringFlipped) {
        return switch (this.bestId) {
            case 6 -> {
                this.isMirrored = true;
                yield Optional.of(DriveWaypoints.Algae22);
            }
            case 7 -> {
                this.isMirrored = true;
                yield Optional.of(DriveWaypoints.Algae21);
            }
            case 8 -> {
                this.isMirrored = true;
                yield Optional.of(DriveWaypoints.Algae20);
            }
            case 9 -> {
                this.isMirrored = true;
                yield Optional.of(DriveWaypoints.Algae19);
            }
            case 10 -> {
                this.isMirrored = true;
                yield Optional.of(DriveWaypoints.Algae18);
            }
            case 11 -> {
                this.isMirrored = true;
                yield Optional.of(DriveWaypoints.Algae17);
            }
            case 17 -> {
                this.isMirrored = false;
                yield Optional.of(DriveWaypoints.Algae17);
            }
            case 18 -> {
                this.isMirrored = false;
                yield Optional.of(DriveWaypoints.Algae18);
            }
            case 19 -> {
                this.isMirrored = false;
                yield Optional.of(DriveWaypoints.Algae19);
            }
            case 20 -> {
                this.isMirrored = false;
                yield Optional.of(DriveWaypoints.Algae20);
            }
            case 21 -> {
                this.isMirrored = false;
                yield Optional.of(DriveWaypoints.Algae21);
            }
            case 22 -> {
                this.isMirrored = false;
                yield Optional.of(DriveWaypoints.Algae22);
            }
            default -> Optional.empty();
        };
    }

    private Optional<DriveWaypoints> getProcessorWaypoint(boolean scoringFlipped) {
        return switch (this.bestId) {
            case 3 -> {
                this.isMirrored = false;
                yield Optional.of(DriveWaypoints.Processor3);
            }
            case 16 -> {
                this.isMirrored = false;
                yield Optional.of(DriveWaypoints.Processor16);
            }
            default -> Optional.empty();
        };
    }

}