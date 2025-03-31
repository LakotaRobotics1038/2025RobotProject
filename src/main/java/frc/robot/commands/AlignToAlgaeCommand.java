package frc.robot.commands;

import java.util.List;
import java.util.function.Supplier;

import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Vision;

public class AlignToAlgaeCommand extends Command {
    private Vision vision = Vision.getInstance();
    private DriveTrain driveTrain = DriveTrain.getInstance();

    private List<PhotonPipelineResult> visionResults;
    private PhotonTrackedTarget bestAlgae;

    private Supplier<Double> xMove;
    private Supplier<Double> yMove;

    private PIDController turnController;
    private double kP;
    private double kI;
    private double kD;

    public AlignToAlgaeCommand(Supplier<Double> xMove, Supplier<Double> yMove) {
        super.addRequirements(vision, driveTrain);
        this.xMove = xMove;
        this.yMove = yMove;
        turnController = new PIDController(kP, kI, kD);
    }

    @Override
    public void initialize() {
        vision.getResultsBackCam();
        vision.setAlgaeMode();
    }

    @Override
    public void execute() {
        visionResults = vision.getResultsBackCam();
        getBestAlgae(visionResults);

        double turn = turnController.calculate(bestAlgae.getYaw(), 0);
        driveTrain.drive(xMove.get(), yMove.get(), turn, true);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        vision.setAprilTagMode();
    }

    private void getBestAlgae(List<PhotonPipelineResult> visionResults) {
        for (PhotonPipelineResult result : visionResults) {
            if (result.hasTargets()) {
                for (PhotonTrackedTarget photonTarget : result.getTargets()) {
                    if (photonTarget.getFiducialId() == 0) {
                        bestAlgae = photonTarget;
                    }
                }
            }
        }
    }
}
