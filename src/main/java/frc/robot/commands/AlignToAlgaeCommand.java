package frc.robot.commands;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Vision;

public class AlignToAlgaeCommand extends Command {
    private Vision vision = Vision.getInstance();
    private DriveTrain driveTrain = DriveTrain.getInstance();

    private Optional<PhotonTrackedTarget> bestAlgae = Optional.empty();

    private Supplier<Double> xMove;
    private Supplier<Double> yMove;

    private static double kP = 0.015;
    private static double kI = 0.0;
    private static double kD = 0.0;
    private PIDController turnController = new PIDController(kP, kI, kD);

    public AlignToAlgaeCommand(Supplier<Double> xMove, Supplier<Double> yMove) {
        super.addRequirements(vision, driveTrain);
        this.xMove = xMove;
        this.yMove = yMove;

        Shuffleboard.getTab("Controls").add("Algae Align", turnController);
        turnController.setSetpoint(0);
        turnController.disableContinuousInput();
    }

    @Override
    public void initialize() {
        this.bestAlgae = Optional.empty();
    }

    @Override
    public void execute() {
        List<PhotonPipelineResult> visionResults = vision.getResultsBackCam();
        getBestAlgae(visionResults);

        double turn = 0;
        if (this.bestAlgae.isPresent()) {
            turn = turnController.calculate(bestAlgae.get().getYaw(), 0);
        }

        turn = MathUtil.clamp(turn, -1, 1);
        driveTrain.setControl(driveTrain.drive(xMove.get(), -yMove.get(), turn, true));
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        this.bestAlgae = Optional.empty();
    }

    private void getBestAlgae(List<PhotonPipelineResult> visionResults) {
        for (PhotonPipelineResult result : visionResults) {
            if (result.hasTargets()) {
                this.bestAlgae = Optional.of(result.getBestTarget());
            }
        }
    }
}
