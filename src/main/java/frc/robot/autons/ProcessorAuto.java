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
import frc.robot.commands.SetAcquisitionPositionEscape;
import frc.robot.commands.SetAcquisitionPositionCommand.FinishActions;
import frc.robot.utils.AcquisitionPositionSetpoint;

public class ProcessorAuto extends Auton {
    ProcessorAuto(Optional<Alliance> alliance) throws FileVersionException, IOException, ParseException {
        super(alliance);

        super.addCommands(
                new SetAcquisitionPositionEscape(SetAcquisitionPositionEscape.FinishActions.Default),
                new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.L23Algae, FinishActions.NoDisable)
                        .alongWith(followPathCommand(Paths.getMidPoseToTag21Algae())),
                new AcquireAlgaeCommand()
                        .withDeadline(
                                new WaitCommand(1).andThen(followPathCommand(Paths.getLeftReefTag21ToProcessorPath())))
                        .alongWith(new WaitCommand(0.5)
                                .andThen(new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.Processor,
                                        FinishActions.NoFinish).withTimeout(1))),
                new DisposeAlgaeCommand().withTimeout(0.5),

                new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.L34Algae,
                        FinishActions.NoDisable).withDeadline(followPathCommand(Paths.getProcessorToAlgaePath22())),

                new AcquireAlgaeCommand().withDeadline(
                        followPathCommand(Paths.getReefTag22ToProcessor()))
                        .alongWith(new WaitCommand(0.5)
                                .andThen(new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.Processor,
                                        FinishActions.NoDisable).withTimeout(1))),
                new DisposeAlgaeCommand());
    }
}
