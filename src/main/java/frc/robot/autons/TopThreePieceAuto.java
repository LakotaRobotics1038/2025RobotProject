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
                new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.L4Coral, FinishActions.NoDisable)
                        .alongWith(new WaitCommand(0.5)
                                .andThen(followPathCommand(Paths.getTopPosToRightReefTag20Path()))),
                new DisposeCoral134Command(),

                followPathCommand(Paths.getRightReefTag20ToTopFeederStationPath())
                        .alongWith(new WaitCommand(0.5).andThen(
                                new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.FeederStation,
                                        FinishActions.NoDisable))),
                new AcquireCoralCommand(),
                new WaitCommand(1),

                new AcquireForL4Command(),
                followPathCommand(Paths.getTopFeederStationToLeftTag20Path())
                        .alongWith(new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.L4Coral,
                                FinishActions.NoDisable)),
                new DisposeCoral134Command(),

                followPathCommand(Paths.getLeftTag20ToTopFeederStationPath())
                        .alongWith(new WaitCommand(0.5).andThen(
                                new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.FeederStation,
                                        FinishActions.NoDisable))),
                new AcquireCoralCommand(),
                new WaitCommand(1),

                new AcquireForL4Command(),
                followPathCommand(Paths.getTopFeederStationToRightTag20Path())
                        .alongWith(new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.L4Coral,
                                FinishActions.NoDisable)),
                new DisposeCoral134Command());
    }
}
