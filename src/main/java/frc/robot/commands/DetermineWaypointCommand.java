package frc.robot.commands;

import java.util.List;
import java.util.Optional;

import org.photonvision.targeting.PhotonPipelineResult;

import com.pathplanner.lib.util.FlippingUtil;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.OperatorState;
import frc.robot.constants.AutoConstants.DriveWaypoints;
import frc.robot.subsystems.Vision;
import frc.robot.utils.AcquisitionPositionSetpoint;

public class DetermineWaypointCommand extends Command {
    private Vision vision = Vision.getInstance();
    private int bestId = 0;
    private Optional<DriveWaypoints> waypoint;
    private boolean isMirrored;

    public DetermineWaypointCommand() {
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
        boolean scoringFlipped = OperatorState.isScoringFlipped();
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

    public int getBestID() {
        return this.bestId;
    }

    public boolean getIsMirrored() {
        return this.isMirrored;
    }

    public Optional<Pose2d> getPose2d() {
        return Optional.ofNullable(FlippingUtil.flipFieldPose(this.waypoint.get().getEndpoint()));
    }

    public Optional<Rotation2d> getRotation2d() {
        return Optional.ofNullable(waypoint.get().getRotation2d());
    }

    private Optional<DriveWaypoints> get134CoralWaypoint(boolean scoringFlipped) {
        switch (this.bestId) {
            case 6:
                this.isMirrored = true;
                return Optional.of(scoringFlipped ? DriveWaypoints.LeftCoral22
                        : DriveWaypoints.RightCoral22);
            case 7:
                this.isMirrored = true;
                return Optional.of(scoringFlipped ? DriveWaypoints.LeftCoral21
                        : DriveWaypoints.RightCoral21);
            case 8:
                this.isMirrored = true;
                return Optional.of(scoringFlipped ? DriveWaypoints.LeftCoral20
                        : DriveWaypoints.RightCoral20);
            case 9:
                this.isMirrored = true;
                return Optional.of(scoringFlipped ? DriveWaypoints.LeftCoral19
                        : DriveWaypoints.RightCoral19);
            case 10:
                this.isMirrored = true;
                return Optional.of(scoringFlipped ? DriveWaypoints.LeftCoral18
                        : DriveWaypoints.RightCoral18);
            case 11:
                this.isMirrored = true;
                return Optional.of(scoringFlipped ? DriveWaypoints.LeftCoral17
                        : DriveWaypoints.RightCoral17);
            case 17:
                this.isMirrored = false;
                return Optional.of(scoringFlipped ? DriveWaypoints.LeftCoral17
                        : DriveWaypoints.RightCoral17);
            case 18:
                this.isMirrored = false;
                return Optional.of(scoringFlipped ? DriveWaypoints.LeftCoral18
                        : DriveWaypoints.RightCoral18);
            case 19:
                this.isMirrored = false;
                return Optional.of(scoringFlipped ? DriveWaypoints.LeftCoral19
                        : DriveWaypoints.RightCoral19);
            case 20:
                this.isMirrored = false;
                return Optional.of(scoringFlipped ? DriveWaypoints.LeftCoral20
                        : DriveWaypoints.RightCoral20);
            case 21:
                this.isMirrored = false;
                return Optional.of(scoringFlipped ? DriveWaypoints.LeftCoral21
                        : DriveWaypoints.RightCoral21);
            case 22:
                this.isMirrored = false;
                return Optional.of(scoringFlipped ? DriveWaypoints.LeftCoral22
                        : DriveWaypoints.RightCoral22);
            default:
                return Optional.empty();
        }
    }

    private Optional<DriveWaypoints> getLevel2CoralWaypoint(boolean scoringFlipped) {
        switch (this.bestId) {
            case 6:
                this.isMirrored = true;
                return Optional.of(scoringFlipped ? DriveWaypoints.Level2LeftCoral22
                        : DriveWaypoints.Level2RightCoral22);
            case 7:
                this.isMirrored = true;
                return Optional.of(scoringFlipped ? DriveWaypoints.Level2LeftCoral21
                        : DriveWaypoints.Level2RightCoral21);
            case 8:
                this.isMirrored = true;
                return Optional.of(scoringFlipped ? DriveWaypoints.Level2LeftCoral20
                        : DriveWaypoints.Level2RightCoral20);
            case 9:
                this.isMirrored = true;
                return Optional.of(scoringFlipped ? DriveWaypoints.Level2LeftCoral19
                        : DriveWaypoints.Level2RightCoral19);
            case 10:
                this.isMirrored = true;
                return Optional.of(scoringFlipped ? DriveWaypoints.Level2LeftCoral18
                        : DriveWaypoints.Level2RightCoral18);
            case 11:
                this.isMirrored = true;
                return Optional.of(scoringFlipped ? DriveWaypoints.Level2LeftCoral17
                        : DriveWaypoints.Level2RightCoral17);
            case 17:
                this.isMirrored = false;
                return Optional.of(scoringFlipped ? DriveWaypoints.Level2LeftCoral17
                        : DriveWaypoints.Level2RightCoral17);
            case 18:
                this.isMirrored = false;
                return Optional.of(scoringFlipped ? DriveWaypoints.Level2LeftCoral18
                        : DriveWaypoints.Level2RightCoral18);
            case 19:
                this.isMirrored = false;
                return Optional.of(scoringFlipped ? DriveWaypoints.Level2LeftCoral19
                        : DriveWaypoints.Level2RightCoral19);
            case 20:
                this.isMirrored = false;
                return Optional.of(scoringFlipped ? DriveWaypoints.Level2LeftCoral20
                        : DriveWaypoints.Level2RightCoral20);
            case 21:
                this.isMirrored = false;
                return Optional.of(scoringFlipped ? DriveWaypoints.Level2LeftCoral21
                        : DriveWaypoints.Level2RightCoral21);
            case 22:
                this.isMirrored = false;
                return Optional.of(scoringFlipped ? DriveWaypoints.Level2LeftCoral22
                        : DriveWaypoints.Level2RightCoral22);
            default:
                return Optional.empty();
        }
    }

    private Optional<DriveWaypoints> getAlgaeWaypoint(boolean scoringFlipped) {
        switch (this.bestId) {
            case 6:
                this.isMirrored = true;
                return Optional.of(DriveWaypoints.AlgaeLevel22);
            case 7:
                this.isMirrored = true;
                return Optional.of(DriveWaypoints.AlgaeLevel21);
            case 8:
                this.isMirrored = true;
                return Optional.of(DriveWaypoints.AlgaeLevel20);
            case 9:
                this.isMirrored = true;
                return Optional.of(DriveWaypoints.AlgaeLevel19);
            case 10:
                this.isMirrored = true;
                return Optional.of(DriveWaypoints.AlgaeLevel18);
            case 11:
                this.isMirrored = true;
                return Optional.of(DriveWaypoints.AlgaeLevel17);
            case 17:
                this.isMirrored = false;
                return Optional.of(DriveWaypoints.AlgaeLevel17);
            case 18:
                this.isMirrored = false;
                return Optional.of(DriveWaypoints.AlgaeLevel18);
            case 19:
                this.isMirrored = false;
                return Optional.of(DriveWaypoints.AlgaeLevel19);
            case 20:
                this.isMirrored = false;
                return Optional.of(DriveWaypoints.AlgaeLevel20);
            case 21:
                this.isMirrored = false;
                return Optional.of(DriveWaypoints.AlgaeLevel21);
            case 22:
                this.isMirrored = false;
                return Optional.of(DriveWaypoints.AlgaeLevel22);
            default:
                return Optional.empty();
        }
    }

    private Optional<DriveWaypoints> getProcessorWaypoint(boolean scoringFlipped) {
        switch (this.bestId) {
            case 3:
                this.isMirrored = false;
                return Optional.of(DriveWaypoints.Processor3);
            case 16:
                this.isMirrored = false;
                return Optional.of(DriveWaypoints.Processor16);
            default:
                return Optional.empty();
        }
    }

    private Optional<DriveWaypoints> getFeederStationWaypoint(boolean scoringFlipped) {
        switch (this.bestId) {
            case 1:
                this.isMirrored = true;
                return Optional.of(DriveWaypoints.FeederStation12Point5);
            case 2:
                this.isMirrored = true;
                return Optional.of(DriveWaypoints.FeederStation13Point5);
            case 12:
                this.isMirrored = false;
                return Optional.of(DriveWaypoints.FeederStation12Point5);
            case 13:
                this.isMirrored = false;
                return Optional.of(DriveWaypoints.FeederStation13Point5);
            default:
                return Optional.empty();
        }
    }
}