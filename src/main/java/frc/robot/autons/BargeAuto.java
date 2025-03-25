package frc.robot.autons;

import java.io.IOException;
import java.util.Optional;

import org.json.simple.parser.ParseException;

import com.pathplanner.lib.util.FileVersionException;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.AcquireAlgaeCommand;
import frc.robot.commands.DisposeAlgaeCommand;
import frc.robot.commands.SetAcquisitionPositionCommand;
import frc.robot.commands.SetAcquisitionPositionCommand.FinishActions;
import frc.robot.utils.AcquisitionPositionSetpoint;;

public class BargeAuto extends Auton {
    BargeAuto(Optional<Alliance> alliance) throws FileVersionException, IOException, ParseException {
        super(alliance);

        super.addCommands(
                new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.L23Algae, FinishActions.NoDisable)
                        .alongWith(followPathCommand(Paths.getMidPoseToTag21Algae())),
                new AcquireAlgaeCommand().withDeadline(followPathCommand(Paths.getReefTag21ToNet()))
                        .alongWith(new WaitCommand(0.5).andThen(
                                new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.Barge,
                                        FinishActions.NoDisable)))
                        .withTimeout(3),

                new DisposeAlgaeCommand().withTimeout(0.5),
                new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.L34Algae, FinishActions.NoDisable),

                followPathCommand(Paths.getNetToTag20Algae()),
                new AcquireAlgaeCommand().withDeadline(
                        followPathCommand(Paths.getNetToTag20Algae()))
                        .alongWith(new WaitCommand(0.5))
                        .andThen(new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.Barge,
                                FinishActions.NoDisable))
                        .withTimeout(3.0),
                new DisposeAlgaeCommand());
    }
}
