package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.DashboardConstants;
import frc.robot.subsystems.DriveTrain;

public class AlignWithBargeCommand extends Command {
    public DriveTrain driveTrain = DriveTrain.getInstance();

    private Supplier<Double> yMove;
    // private Alliance alliance;

    private static double kZP = 0.015;
    private static double kZI = 0.0;
    private static double kZD = 0.0;
    private PIDController zController = new PIDController(kZP, kZI, kZD);

    private static double kXP = 0.1;
    private static double kXI = 0.0;
    private static double kXD = 0.0;
    private PIDController xController = new PIDController(kXP, kXI, kXD);

    public AlignWithBargeCommand(Supplier<Double> yMove) {
        super.addRequirements(driveTrain);
        this.yMove = yMove;

        zController.enableContinuousInput(-180, 180);
        SmartDashboard.putData(DashboardConstants.kBargeAlignPIDX, xController);
        SmartDashboard.putData(DashboardConstants.kBargeAlignPIDZ, zController);
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
            double x = xController.calculate(driveTrain.getX(), 9.84);
            double z = zController.calculate(driveTrain.getRotation(), 180);
            x = MathUtil.clamp(x, -.4, .4);
            z = MathUtil.clamp(z, -.5, .5);
            driveTrain.setControl(driveTrain.drive(x, -yMove.get(), z, true));
        } else if (driveTrain.getX() < 8.6) {
            double x = xController.calculate(driveTrain.getX(), 7.900);
            double z = zController.calculate(driveTrain.getRotation(), 0);
            x = MathUtil.clamp(x, -.25, .25);
            z = MathUtil.clamp(z, -.5, .5);
            driveTrain.setControl(driveTrain.drive(x, -yMove.get(), z, true));
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
