package frc.robot.commands;

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
        switch (setpointLevel) {
            case L1Coral:
            case L3Coral:
            case L4Coral:
                get123CoralWaypoint(scoringSide);
                break;
            case L2Coral:
                getLevel2CoralWaypoint(scoringSide);
                break;
            case L23Algae:
            case L34Algae:
                getAlgaeWaypoint(scoringSide);
                break;
            case Processor:
                getProcessorWaypoint(scoringSide);
                break;
            case FeederStation:
                getFeederStationWaypoint(scoringSide);
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

    private void get134CoralWaypoint(ScoringSide scoringSide) {
        switch (this.bestId) {
            case 6:
                this.waypoint = scoringSide == ScoringSide.LEFT ? DriveWaypoints.LeftCoral22
                        : DriveWaypoints.RightCoral22;
                this.isMirrored = true;
                break;
            case 7:
                this.waypoint = scoringSide == ScoringSide.LEFT ? DriveWaypoints.LeftCoral21
                        : DriveWaypoints.RightCoral21;
                this.isMirrored = true;
                break;
            case 8:
                this.waypoint = scoringSide == ScoringSide.LEFT ? DriveWaypoints.LeftCoral20
                        : DriveWaypoints.RightCoral20;
                this.isMirrored = true;
                break;
            case 9:
                this.waypoint = scoringSide == ScoringSide.LEFT ? DriveWaypoints.LeftCoral19
                        : DriveWaypoints.RightCoral19;
                this.isMirrored = true;
                break;
            case 10:
                this.waypoint = scoringSide == ScoringSide.LEFT ? DriveWaypoints.LeftCoral18
                        : DriveWaypoints.RightCoral18;
                this.isMirrored = true;
                break;
            case 11:
                this.waypoint = scoringSide == ScoringSide.LEFT ? DriveWaypoints.LeftCoral17
                        : DriveWaypoints.RightCoral17;
                this.isMirrored = true;
                break;
            case 17:
                this.waypoint = scoringSide == ScoringSide.LEFT ? DriveWaypoints.LeftCoral17
                        : DriveWaypoints.RightCoral17;
                this.isMirrored = false;
                break;
            case 18:
                this.waypoint = scoringSide == ScoringSide.LEFT ? DriveWaypoints.LeftCoral18
                        : DriveWaypoints.RightCoral18;
                this.isMirrored = false;
                break;
            case 19:
                this.waypoint = scoringSide == ScoringSide.LEFT ? DriveWaypoints.LeftCoral19
                        : DriveWaypoints.RightCoral19;
                this.isMirrored = false;
                break;
            case 20:
                this.waypoint = scoringSide == ScoringSide.LEFT ? DriveWaypoints.LeftCoral20
                        : DriveWaypoints.RightCoral20;
                this.isMirrored = false;
                break;
            case 21:
                this.waypoint = scoringSide == ScoringSide.LEFT ? DriveWaypoints.LeftCoral21
                        : DriveWaypoints.RightCoral21;
                this.isMirrored = false;
                break;
            case 22:
                this.waypoint = scoringSide == ScoringSide.LEFT ? DriveWaypoints.LeftCoral22
                        : DriveWaypoints.RightCoral22;
                this.isMirrored = false;
                break;

            default:
                break;
        }
    }

    private void getLevel2CoralWaypoint(ScoringSide scoringSide) {
        switch (this.bestId) {
            case 6:
                this.waypoint = scoringSide == ScoringSide.LEFT ? DriveWaypoints.Level2LeftCoral22
                        : DriveWaypoints.Level2RightCoral22;
                this.isMirrored = true;
                break;
            case 7:
                this.waypoint = scoringSide == ScoringSide.LEFT ? DriveWaypoints.Level2LeftCoral21
                        : DriveWaypoints.Level2RightCoral21;
                this.isMirrored = true;
                break;
            case 8:
                this.waypoint = scoringSide == ScoringSide.LEFT ? DriveWaypoints.Level2LeftCoral20
                        : DriveWaypoints.Level2RightCoral20;
                this.isMirrored = true;
                break;
            case 9:
                this.waypoint = scoringSide == ScoringSide.LEFT ? DriveWaypoints.Level2LeftCoral19
                        : DriveWaypoints.Level2RightCoral19;
                this.isMirrored = true;
                break;
            case 10:
                this.waypoint = scoringSide == ScoringSide.LEFT ? DriveWaypoints.Level2LeftCoral18
                        : DriveWaypoints.Level2RightCoral18;
                this.isMirrored = true;
                break;
            case 11:
                this.waypoint = scoringSide == ScoringSide.LEFT ? DriveWaypoints.Level2LeftCoral17
                        : DriveWaypoints.Level2RightCoral17;
                this.isMirrored = true;
                break;
            case 17:
                this.waypoint = scoringSide == ScoringSide.LEFT ? DriveWaypoints.Level2LeftCoral17
                        : DriveWaypoints.Level2RightCoral17;
                this.isMirrored = false;
                break;
            case 18:
                this.waypoint = scoringSide == ScoringSide.LEFT ? DriveWaypoints.Level2LeftCoral18
                        : DriveWaypoints.Level2RightCoral18;
                this.isMirrored = false;
                break;
            case 19:
                this.waypoint = scoringSide == ScoringSide.LEFT ? DriveWaypoints.Level2LeftCoral19
                        : DriveWaypoints.Level2RightCoral19;
                this.isMirrored = false;
                break;
            case 20:
                this.waypoint = scoringSide == ScoringSide.LEFT ? DriveWaypoints.Level2LeftCoral20
                        : DriveWaypoints.Level2RightCoral20;
                this.isMirrored = false;
                break;
            case 21:
                this.waypoint = scoringSide == ScoringSide.LEFT ? DriveWaypoints.Level2LeftCoral21
                        : DriveWaypoints.Level2RightCoral21;
                this.isMirrored = false;
                break;
            case 22:
                this.waypoint = scoringSide == ScoringSide.LEFT ? DriveWaypoints.Level2LeftCoral22
                        : DriveWaypoints.Level2RightCoral22;
                this.isMirrored = false;
                break;

            default:
                break;
        }
    }

    private void getAlgaeWaypoint(ScoringSide scoringSide) {
        switch (this.bestId) {
            case 6:
                this.waypoint = DriveWaypoints.AlgaeLevel22;
                this.isMirrored = true;
                break;
            case 7:
                this.waypoint = DriveWaypoints.AlgaeLevel21;
                this.isMirrored = true;
                break;
            case 8:
                this.waypoint = DriveWaypoints.AlgaeLevel20;
                this.isMirrored = true;
                break;
            case 9:
                this.waypoint = DriveWaypoints.AlgaeLevel19;
                this.isMirrored = true;
                break;
            case 10:
                this.waypoint = DriveWaypoints.AlgaeLevel18;
                this.isMirrored = true;
                break;
            case 11:
                this.waypoint = DriveWaypoints.AlgaeLevel17;
                this.isMirrored = true;
                break;
            case 17:
                this.waypoint = DriveWaypoints.AlgaeLevel17;
                this.isMirrored = false;
                break;
            case 18:
                this.waypoint = DriveWaypoints.AlgaeLevel18;
                this.isMirrored = false;
                break;
            case 19:
                this.waypoint = DriveWaypoints.AlgaeLevel19;
                this.isMirrored = false;
                break;
            case 20:
                this.waypoint = DriveWaypoints.AlgaeLevel20;
                this.isMirrored = false;
                break;
            case 21:
                this.waypoint = DriveWaypoints.AlgaeLevel21;
                this.isMirrored = false;
                break;
            case 22:
                this.waypoint = DriveWaypoints.AlgaeLevel22;
                this.isMirrored = false;
                break;

            default:
                break;
        }
    }

    private void getProcessorWaypoint(ScoringSide scoringSide) {
        switch (this.bestId) {
            case 3:
                this.waypoint = DriveWaypoints.Processor3;
                this.isMirrored = false;
                break;
            case 16:
                this.waypoint = DriveWaypoints.Processor16;
                this.isMirrored = false;
                break;
            default:
                break;
        }
    }

    private void getFeederStationWaypoint(ScoringSide scoringSide) {
        switch (this.bestId) {
            case 1:
                this.waypoint = DriveWaypoints.FeederStation12Point5;
                this.isMirrored = true;
                break;
            case 2:
                this.waypoint = DriveWaypoints.FeederStation13Point5;
                this.isMirrored = true;
                break;
            case 12:
                this.waypoint = DriveWaypoints.FeederStation12Point5;
                this.isMirrored = false;
                break;
            case 13:
                this.waypoint = DriveWaypoints.FeederStation13Point5;
                this.isMirrored = false;
                break;
            default:
                break;
        }
    }
}
