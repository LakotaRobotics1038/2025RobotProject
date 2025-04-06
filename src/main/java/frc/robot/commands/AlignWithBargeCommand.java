package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Vision;

public class AlignWithBargeCommand extends Command {
    public DriveTrain driveTrain = DriveTrain.getInstance();
    private Vision vision = Vision.getInstance();

    private Supplier<Double> yMove;
    // private Alliance alliance;

    private static double kZP = 0.0;
    private static double kZI = 0.0;
    private static double kZD = 0.0;
    private PIDController zController = new PIDController(kZP, kZI, kZD);

    private static double kXP = 0.0;
    private static double kXI = 0.0;
    private static double kXD = 0.0;
    private PIDController xController = new PIDController(kXP, kXI, kXD);

    public void AlignToAlgaeCommand(Supplier<Double> yMove) {
        super.addRequirements(vision, driveTrain);
        this.yMove = yMove;

        Shuffleboard.getTab("Controls").add("z Controller barge", zController);
        zController.setSetpoint(0);
        zController.disableContinuousInput();

        Shuffleboard.getTab("Controls").add("x Controller barge", xController);
        zController.setSetpoint(0);
        zController.disableContinuousInput();
    }

    @Override
    public void initialize() {
        // Optional<Alliance> opAlliance = DriverStation.getAlliance();
        // if (opAlliance.isPresent()) {
        // alliance = opAlliance.get();
        // }
    }

    @Override
    public void execute() {
        if (driveTrain.getX() > 9) {
            double x = xController.calculate(driveTrain.getX(), 9.578);
            double z = zController.calculate(driveTrain.getRotation(), 0);
            x = MathUtil.clamp(x, -1, 1);
            z = MathUtil.clamp(z, -1, 1);
            driveTrain.setControl(driveTrain.drive(x, -yMove.get(), z, true));
        } else if (driveTrain.getX() < 8.6) {
            double x = xController.calculate(driveTrain.getX(), 8.023);
            double z = zController.calculate(driveTrain.getRotation(), 0);
            x = MathUtil.clamp(x, -1, 1);
            z = MathUtil.clamp(z, -1, 1);
            driveTrain.setControl(driveTrain.drive(x, -yMove.get(), z, true));
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
