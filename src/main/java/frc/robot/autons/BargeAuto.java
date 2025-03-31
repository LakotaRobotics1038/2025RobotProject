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
import frc.robot.commands.SetAcquisitionPositionEscapeCommand;
import frc.robot.utils.AcquisitionPositionSetpoint;;

public class BargeAuto extends Auton {
    BargeAuto(Optional<Alliance> alliance) throws FileVersionException, IOException, ParseException {
        super(alliance);

        super.addCommands(
                new SetAcquisitionPositionEscapeCommand(SetAcquisitionPositionEscapeCommand.FinishActions.Default),
                new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.L23Algae, FinishActions.NoDisable)
                        .alongWith(followPathCommand(Paths.getMidPoseToTag21Algae())),
                new AcquireAlgaeCommand().raceWith(followPathCommand(Paths.getReefTag21ToNet()))
                        .alongWith(new WaitCommand(0.5).andThen(
                                new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.Barge,
                                        FinishActions.NoDisable)))
                        .withTimeout(3),

                new DisposeAlgaeCommand().withTimeout(1),
                new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.L34Algae, FinishActions.NoDisable)
                        .withDeadline(
                                followPathCommand(Paths.getNetToTag20Algae())),
                new AcquireAlgaeCommand().raceWith(
                        followPathCommand(Paths.getReefTag20ToNet()))
                        .alongWith(new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.Barge,
                                FinishActions.NoDisable)
                                .withTimeout(3.0)),
                new DisposeAlgaeCommand().withTimeout(1),
                new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.Storage));
    }
}
