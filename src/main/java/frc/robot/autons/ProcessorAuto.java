package frc.robot.autons;

import java.io.IOException;
import java.util.Optional;

import org.json.simple.parser.ParseException;

import com.pathplanner.lib.util.FileVersionException;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.AcquireAlgaeCommand;
import frc.robot.commands.AcquireForL4Command;
import frc.robot.commands.DisposeAlgaeCommand;
import frc.robot.commands.DisposeCoral134Command;
import frc.robot.commands.SetAcquisitionPositionCommand;
import frc.robot.commands.SetAcquisitionPositionCommand.FinishActions;
import frc.robot.utils.AcquisitionPositionSetpoint;;

public class ProcessorAuto extends Auton {
    ProcessorAuto(Optional<Alliance> alliance) throws FileVersionException, IOException, ParseException {
        super(alliance);

        super.addCommands(
                new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.L23Algae, FinishActions.NoDisable)
                        .andThen(followPathCommand(Paths.getTopPathToTag20Algae())),
                new AcquireAlgaeCommand().withDeadline(followPathCommand(Paths.getReefTag21ToNet())),

                followPathCommand(Paths.getReefTag20ToNet())
                        .alongWith(new WaitCommand(0.5).andThen(
                                new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.Barge,
                                        FinishActions.NoDisable)))
                        .withTimeout(3),

                new DisposeAlgaeCommand().withTimeout(0.5),
                new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.Storage, FinishActions.NoDisable),

                new AcquireAlgaeCommand().withDeadline(
                        followPathCommand(Paths.getNetToReefTag21Algae())).withTimeout(1),
                new AcquireAlgaeCommand().withDeadline(
                        followPathCommand(Paths.getLeftReefTag21ToProcessorPath())
                                .alongWith(new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.Processor,
                                        FinishActions.NoDisable))),
                new DisposeAlgaeCommand());
    }
}
