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
import frc.robot.commands.SetAcquisitionPositionEscapeCommand;
import frc.robot.commands.SetAcquisitionPositionCommand.FinishActions;
import frc.robot.utils.AcquisitionPositionSetpoint;

public class ProcessorAuto extends Auton {
    ProcessorAuto(Optional<Alliance> alliance) throws FileVersionException, IOException, ParseException {
        super(alliance);

        super.addCommands(
                new SetAcquisitionPositionEscapeCommand(SetAcquisitionPositionEscapeCommand.FinishActions.Default),
                new AcquireAlgaeCommand()
                        .raceWith(
                                new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.L23Algae,
                                        FinishActions.NoDisable)
                                        .alongWith(followPathCommand(Paths.getMidPoseToTag21Algae()))
                                        .withTimeout(2)),
                new AcquireAlgaeCommand()
                        .raceWith(followPathCommand(Paths.getReefTag21ToProcessor())
                                .alongWith(new WaitCommand(0.5)
                                        .andThen(new SetAcquisitionPositionCommand(
                                                AcquisitionPositionSetpoint.Processor,
                                                FinishActions.NoDisable)
                                                .withTimeout(2)))),
                new DisposeAlgaeCommand()
                        .withTimeout(0.5),

                new AcquireAlgaeCommand()
                        .raceWith(
                                new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.L34Algae,
                                        FinishActions.NoDisable)
                                        .alongWith(followPathCommand(Paths.getProcessorToAlgaePath22()))
                                        .withTimeout(2)),

                new AcquireAlgaeCommand()
                        .raceWith(followPathCommand(Paths.getReefTag22ToProcessor())
                                .alongWith(new WaitCommand(0.5)
                                        .andThen(new SetAcquisitionPositionCommand(
                                                AcquisitionPositionSetpoint.Processor,
                                                FinishActions.NoDisable)
                                                .withTimeout(2)))),
                new DisposeAlgaeCommand());

    }
}
