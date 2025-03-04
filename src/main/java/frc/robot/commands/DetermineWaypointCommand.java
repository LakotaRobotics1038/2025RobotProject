package frc.robot.commands;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import com.pathplanner.lib.util.FlippingUtil;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.AutoConstants.DriveWaypoints;
import frc.robot.subsystems.Vision;
import frc.robot.utils.AcquisitionPositionSetpoint;

public class DetermineWaypointCommand extends Command {
    private final Vision vision = Vision.getInstance();
    private int bestId = -1;
    private Optional<DriveWaypoints> waypoint = Optional.empty();
    private boolean isMirrored;

    public DetermineWaypointCommand() {
        addRequirements(vision);
    }

    @Override
    public void initialize() {
        // List<PhotonPipelineResult> visionResults = vision.getResults();
        // double area = 0.0;
        // for (PhotonPipelineResult result : visionResults) {
        // if (result.hasTargets() && result.getBestTarget().getArea() > area) {
        // area = result.getBestTarget().getArea();
        // this.bestId = result.getBestTarget().getFiducialId();
        // }
        // }

        List<PhotonPipelineResult> visionResults = vision.getResults();
        Set<Integer> reefIDs = Set.of(6, 7, 8, 9, 10, 11, 17, 18, 19, 20, 21, 22);
        Set<Integer> processorIDs = Set.of(3, 16);
        Set<Integer> feederStationIDs = Set.of(1, 2, 12, 13);
        // AcquisitionPositionSetpoint setpointLevel = OperatorState.getLastInput();
        AcquisitionPositionSetpoint setpointLevel = AcquisitionPositionSetpoint.L2Coral;
        // boolean scoringFlipped = OperatorState.isScoringFlipped();
        boolean scoringFlipped = false;

        switch (setpointLevel) {
            case L1Coral:
            case L2Coral:
            case L3Coral:
            case L4Coral:
            case L23Algae:
            case L34Algae:
                getBestTarget(reefIDs, visionResults);
                break;
            case Processor:
                getBestTarget(processorIDs, visionResults);
            case FeederStation:
                getBestTarget(feederStationIDs, visionResults);
            default:
                break;
        }

        switch (setpointLevel) {
            case L1Coral:
            case L3Coral:
            case L4Coral:
                this.waypoint = get134CoralWaypoint(scoringFlipped);
                break;
            case L2Coral:
                this.waypoint = getLevel2CoralWaypoint(scoringFlipped);
                break;
            case L23Algae:
            case L34Algae:
                this.waypoint = getAlgaeWaypoint(scoringFlipped);
                break;
            case Processor:
                this.waypoint = getProcessorWaypoint(scoringFlipped);
                break;
            case FeederStation:
                this.waypoint = getFeederStationWaypoint(scoringFlipped);
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

    private void getBestTarget(Set<Integer> set, List<PhotonPipelineResult> visionResults) {
        double area = 0.0;

        for (PhotonPipelineResult result : visionResults) {
            if (result.hasTargets()) {
                PhotonTrackedTarget bestTarget = result.getBestTarget();
                int targetId = bestTarget.getFiducialId();

                if (set.contains(targetId) && bestTarget.getArea() > area) {
                    area = bestTarget.getArea();
                    this.bestId = targetId;
                }
            }
        }
    }

    private Optional<DriveWaypoints> get134CoralWaypoint(boolean scoringFlipped) {
        return switch (this.bestId) {
            case 6 -> {
                this.isMirrored = true;
                yield Optional.of(scoringFlipped ? DriveWaypoints.LeftCoral22
                        : DriveWaypoints.RightCoral22);
            }
            case 7 -> {
                this.isMirrored = true;
                yield Optional.of(scoringFlipped ? DriveWaypoints.LeftCoral21
                        : DriveWaypoints.RightCoral21);
            }
            case 8 -> {
                this.isMirrored = true;
                yield Optional.of(scoringFlipped ? DriveWaypoints.LeftCoral20
                        : DriveWaypoints.RightCoral20);
            }
            case 9 -> {
                this.isMirrored = true;
                yield Optional.of(scoringFlipped ? DriveWaypoints.LeftCoral19
                        : DriveWaypoints.RightCoral19);
            }
            case 10 -> {
                this.isMirrored = true;
                yield Optional.of(scoringFlipped ? DriveWaypoints.LeftCoral18
                        : DriveWaypoints.RightCoral18);
            }
            case 11 -> {
                this.isMirrored = true;
                yield Optional.of(scoringFlipped ? DriveWaypoints.LeftCoral17
                        : DriveWaypoints.RightCoral17);
            }
            case 17 -> {
                this.isMirrored = false;
                yield Optional.of(scoringFlipped ? DriveWaypoints.LeftCoral17
                        : DriveWaypoints.RightCoral17);
            }
            case 18 -> {
                this.isMirrored = false;
                yield Optional.of(scoringFlipped ? DriveWaypoints.LeftCoral18
                        : DriveWaypoints.RightCoral18);
            }
            case 19 -> {
                this.isMirrored = false;
                yield Optional.of(scoringFlipped ? DriveWaypoints.LeftCoral19
                        : DriveWaypoints.RightCoral19);
            }
            case 20 -> {
                this.isMirrored = false;
                yield Optional.of(scoringFlipped ? DriveWaypoints.LeftCoral20
                        : DriveWaypoints.RightCoral20);
            }
            case 21 -> {
                this.isMirrored = false;
                yield Optional.of(scoringFlipped ? DriveWaypoints.LeftCoral21
                        : DriveWaypoints.RightCoral21);
            }
            case 22 -> {
                this.isMirrored = false;
                yield Optional.of(scoringFlipped ? DriveWaypoints.LeftCoral22
                        : DriveWaypoints.RightCoral22);
            }
            default -> Optional.empty();
        };
    }

    private Optional<DriveWaypoints> getLevel2CoralWaypoint(boolean scoringFlipped) {
        return switch (this.bestId) {
            case 6 -> {
                this.isMirrored = true;
                yield Optional.of(scoringFlipped ? DriveWaypoints.Level2LeftCoral22
                        : DriveWaypoints.Level2RightCoral22);
            }
            case 7 -> {
                this.isMirrored = true;
                yield Optional.of(scoringFlipped ? DriveWaypoints.Level2LeftCoral21
                        : DriveWaypoints.Level2RightCoral21);
            }
            case 8 -> {
                this.isMirrored = true;
                yield Optional.of(scoringFlipped ? DriveWaypoints.Level2LeftCoral20
                        : DriveWaypoints.Level2RightCoral20);
            }
            case 9 -> {
                this.isMirrored = true;
                yield Optional.of(scoringFlipped ? DriveWaypoints.Level2LeftCoral19
                        : DriveWaypoints.Level2RightCoral19);
            }
            case 10 -> {
                this.isMirrored = true;
                yield Optional.of(scoringFlipped ? DriveWaypoints.Level2LeftCoral18
                        : DriveWaypoints.Level2RightCoral18);
            }
            case 11 -> {
                this.isMirrored = true;
                yield Optional.of(scoringFlipped ? DriveWaypoints.Level2LeftCoral17
                        : DriveWaypoints.Level2RightCoral17);
            }
            case 17 -> {
                this.isMirrored = false;
                yield Optional.of(scoringFlipped ? DriveWaypoints.Level2LeftCoral17
                        : DriveWaypoints.Level2RightCoral17);
            }
            case 18 -> {
                this.isMirrored = false;
                yield Optional.of(scoringFlipped ? DriveWaypoints.Level2LeftCoral18
                        : DriveWaypoints.Level2RightCoral18);
            }
            case 19 -> {
                this.isMirrored = false;
                yield Optional.of(scoringFlipped ? DriveWaypoints.Level2LeftCoral19
                        : DriveWaypoints.Level2RightCoral19);
            }
            case 20 -> {
                this.isMirrored = false;
                yield Optional.of(scoringFlipped ? DriveWaypoints.Level2LeftCoral20
                        : DriveWaypoints.Level2RightCoral20);
            }
            case 21 -> {
                this.isMirrored = false;
                yield Optional.of(scoringFlipped ? DriveWaypoints.Level2LeftCoral21
                        : DriveWaypoints.Level2RightCoral21);
            }
            case 22 -> {
                this.isMirrored = false;
                yield Optional.of(scoringFlipped ? DriveWaypoints.Level2LeftCoral22
                        : DriveWaypoints.Level2RightCoral22);
            }
            default -> Optional.empty();
        };
    }

    private Optional<DriveWaypoints> getAlgaeWaypoint(boolean scoringFlipped) {
        return switch (this.bestId) {
            case 6 -> {
                this.isMirrored = true;
                yield Optional.of(DriveWaypoints.AlgaeLevel22);
            }
            case 7 -> {
                this.isMirrored = true;
                yield Optional.of(DriveWaypoints.AlgaeLevel21);
            }
            case 8 -> {
                this.isMirrored = true;
                yield Optional.of(DriveWaypoints.AlgaeLevel20);
            }
            case 9 -> {
                this.isMirrored = true;
                yield Optional.of(DriveWaypoints.AlgaeLevel19);
            }
            case 10 -> {
                this.isMirrored = true;
                yield Optional.of(DriveWaypoints.AlgaeLevel18);
            }
            case 11 -> {
                this.isMirrored = true;
                yield Optional.of(DriveWaypoints.AlgaeLevel17);
            }
            case 17 -> {
                this.isMirrored = false;
                yield Optional.of(DriveWaypoints.AlgaeLevel17);
            }
            case 18 -> {
                this.isMirrored = false;
                yield Optional.of(DriveWaypoints.AlgaeLevel18);
            }
            case 19 -> {
                this.isMirrored = false;
                yield Optional.of(DriveWaypoints.AlgaeLevel19);
            }
            case 20 -> {
                this.isMirrored = false;
                yield Optional.of(DriveWaypoints.AlgaeLevel20);
            }
            case 21 -> {
                this.isMirrored = false;
                yield Optional.of(DriveWaypoints.AlgaeLevel21);
            }
            case 22 -> {
                this.isMirrored = false;
                yield Optional.of(DriveWaypoints.AlgaeLevel22);
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

    private Optional<DriveWaypoints> getFeederStationWaypoint(boolean scoringFlipped) {
        return switch (this.bestId) {
            case 1 -> {
                this.isMirrored = true;
                yield Optional.of(DriveWaypoints.FeederStation12Point5);
            }
            case 2 -> {
                this.isMirrored = true;
                yield Optional.of(DriveWaypoints.FeederStation13Point5);
            }
            case 12 -> {
                this.isMirrored = false;
                yield Optional.of(DriveWaypoints.FeederStation12Point5);
            }
            case 13 -> {
                this.isMirrored = false;
                yield Optional.of(DriveWaypoints.FeederStation13Point5);
            }
            default -> Optional.empty();
        };
    }
}