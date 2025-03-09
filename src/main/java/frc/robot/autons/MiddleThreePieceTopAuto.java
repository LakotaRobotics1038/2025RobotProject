package frc.robot.autons;

import java.io.IOException;
import java.util.Optional;

import org.json.simple.parser.ParseException;

import com.pathplanner.lib.util.FileVersionException;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.AcquireCoralCommand;
import frc.robot.commands.AcquireForL4Command;
import frc.robot.commands.DisposeCoral134Command;
import frc.robot.commands.SetAcquisitionPositionCommand;
import frc.robot.utils.AcquisitionPositionSetpoint;;

public class MiddleThreePieceTopAuto extends Auton {
    MiddleThreePieceTopAuto(Optional<Alliance> alliance) throws FileVersionException, IOException, ParseException {
        super(alliance);

        super.addCommands(
                new SequentialCommandGroup(
                        new ParallelCommandGroup(
                                followPathCommand(Paths.getMiddlePosToRightTag21Path())),
                        new WaitCommand(2),
                        // new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.L4Coral, true),
                        // new AcquireForL4Command()),
                        // ,
                        followPathCommand(Paths.getRightTag21ToTopFeederStationPath()),
                        new WaitCommand(2),
                        // .alongWith(new SequentialCommandGroup(
                        // new WaitCommand(1),
                        // new
                        // SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.FeederStation))),
                        // new AcquireCoralCommand()
                        // ,
                        followPathCommand(Paths.getTopFeederStationToLeftTag20Path()),
                        new WaitCommand(2),
                        // .alongWith(new
                        // SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.L4Coral)),
                        // new DisposeCoral134Command()
                        // ,
                        followPathCommand(Paths.getLeftTag20ToTopFeederStationPath()),
                        new WaitCommand(2),
                        // // .alongWith(new SequentialCommandGroup(
                        // // new WaitCommand(1),
                        // // new
                        // // SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.FeederStation)))
                        // ,
                        // new AcquireCoralCommand(),
                        followPathCommand(Paths.getTopFeederStationToRightTag20Path())
                // .alongWith(new
                // SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.L4Coral)),
                // new DisposeCoral134Command()
                ));
    }
}
