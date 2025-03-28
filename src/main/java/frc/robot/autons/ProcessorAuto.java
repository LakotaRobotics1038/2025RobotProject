package frc.robot.autons;

import java.io.IOException;
import java.util.Optional;

import org.json.simple.parser.ParseException;

import com.pathplanner.lib.util.FileVersionException;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.AcquireAlgaeCommand;
import frc.robot.commands.DisposeAlgaeCommand;
import frc.robot.commands.SetAcquisitionPositionCommand;
import frc.robot.commands.SetAcquisitionPositionEscape;
import frc.robot.commands.SetAcquisitionPositionCommand.FinishActions;
import frc.robot.utils.AcquisitionPositionSetpoint;

public class ProcessorAuto extends Auton {
    ProcessorAuto(Optional<Alliance> alliance) throws FileVersionException, IOException, ParseException {
        super(alliance);

        super.addCommands(
                new SetAcquisitionPositionEscape(SetAcquisitionPositionEscape.FinishActions.Default),
                (new AcquireAlgaeCommand().withTimeout(2)).raceWith(
                        new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.L23Algae, FinishActions.NoDisable)
                                .alongWith(followPathCommand(Paths.getMidPoseToTag21Algae())).withTimeout(2)),
                (new AcquireAlgaeCommand().withTimeout(2))
                        .raceWith(followPathCommand(Paths.getReefTag21ToProcessor()))
                        .alongWith(new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.Processor,
                                FinishActions.NoDisable).withTimeout(2)),
                new DisposeAlgaeCommand().withTimeout(0.5),

                new AcquireAlgaeCommand().raceWith(
                        new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.L34Algae, FinishActions.NoDisable)
                                .alongWith(followPathCommand(Paths.getProcessorToAlgaePath22())).withTimeout(2)),

                new AcquireAlgaeCommand().raceWith(
                        followPathCommand(Paths.getReefTag22ToProcessor()))
                        .alongWith(new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.Processor,
                                FinishActions.NoDisable).withTimeout(2)),
                new DisposeAlgaeCommand());

    }
}
