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

public class MiddleL4CoralProcessor extends Auton {
    MiddleL4CoralProcessor(Optional<Alliance> alliance) throws FileVersionException, IOException, ParseException {
        super(alliance);

        super.addCommands(
                new AcquireForL4Command(),
                new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.L4Coral, FinishActions.NoDisable)
                        .alongWith(new WaitCommand(0.5)
                                .andThen(followPathCommand(Paths.getMiddlePosToLeftTag21Path()))),
                new DisposeCoral134Command().withTimeout(1),

                followPathCommand(Paths.getLeftReefTag21BackwardsPath())
                        .alongWith(new WaitCommand(0.5).andThen(
                                new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.L23Algae,
                                        FinishActions.NoDisable))),
                new AcquireAlgaeCommand(),
                followPathCommand(Paths.getLeftReefTag21ForwardsPath()),
                followPathCommand(Paths.getLeftReefTag21ToProcessorPath())
                        .alongWith(new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.Processor,
                                FinishActions.NoDisable)),
                new DisposeAlgaeCommand());
    }
}
