package frc.robot.autons;

import java.io.IOException;
import java.util.Optional;

import org.json.simple.parser.ParseException;

import com.pathplanner.lib.util.FileVersionException;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.AcquireCoralCommand;
import frc.robot.commands.AcquireForL4Command;
import frc.robot.commands.DisposeCoral134Command;
import frc.robot.commands.SetAcquisitionPositionCommand;
import frc.robot.commands.SetAcquisitionPositionCommand.FinishActions;
import frc.robot.utils.AcquisitionPositionSetpoint;;

public class TopThreePieceAuto extends Auton {
    TopThreePieceAuto(Optional<Alliance> alliance) throws FileVersionException,
            IOException, ParseException {
        super(alliance);

        super.addCommands(
                new AcquireForL4Command(),
                new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.L4Coral, 3)
                        .alongWith(new WaitCommand(0.5)
                                .andThen(followPathCommand(Paths.getTopPosToRightReefTag20Path()))),
                new DisposeCoral134Command(1),

                followPathCommand(Paths.getRightReefTag20ToTopFeederStationPath())
                        .alongWith(new WaitCommand(0.5).andThen(
                                new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.FeederStation, 4))),
                new AcquireCoralCommand(),

                new AcquireForL4Command(),
                followPathCommand(Paths.getTopFeederStationToLeftTag20Path())
                        .alongWith(new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.L4Coral, 3)),
                new DisposeCoral134Command(1),

                followPathCommand(Paths.getLeftTag20ToTopFeederStationPath())
                        .alongWith(new WaitCommand(0.5).andThen(
                                new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.FeederStation, 3))),
                new AcquireCoralCommand(),

                new AcquireForL4Command(),
                followPathCommand(Paths.getTopFeederStationToLeftTag20Path())
                        .alongWith(new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.L4Coral, 3)),
                new DisposeCoral134Command(1));
    }
}
