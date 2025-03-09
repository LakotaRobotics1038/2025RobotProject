package frc.robot.autons;

import java.io.IOException;
import java.util.Optional;

import org.json.simple.parser.ParseException;

import com.pathplanner.lib.util.FileVersionException;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.AcquireCoralCommand;
import frc.robot.commands.DisposeCoral134Command;
import frc.robot.commands.SetAcquisitionPositionCommand;
import frc.robot.utils.AcquisitionPositionSetpoint;

public class BottomThreePieceAuto extends Auton {
    BottomThreePieceAuto(Optional<Alliance> alliance) throws FileVersionException, IOException, ParseException {
        super(alliance);

        super.addCommands(
                followPathCommand(Paths.getBottomStartingPosToLeftReefTag22Path()),
                new WaitCommand(2),
                // .alongWith(new
                // SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.L4Coral)),
                // new DisposeCoral134Command(),
                followPathCommand(Paths.getLeftReefTag22ToBottomFeederStationPath())
        // .alongWith(new SequentialCommandGroup(
        // new WaitCommand(1),
        // new
        // SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.FeederStation))),
        // new AcquireCoralCommand(),
        // followPathCommand(Paths.getBottomFeederStationToRightTag22Path()),
        // .alongWith(new
        // SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.L4Coral)),
        // new DisposeCoral134Command(),
        // followPathCommand(Paths.getRightTag22ToBottomFeederStationPath()),
        // .alongWith(new SequentialCommandGroup(
        // new WaitCommand(1),
        // new
        // SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.FeederStation))),
        // new AcquireCoralCommand(),
        // followPathCommand(Paths.getBottomFeederStationToLeftTag22Path())
        // .alongWith(new
        // SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.L4Coral)),
        // new DisposeCoral134Command()
        );
    }
}
