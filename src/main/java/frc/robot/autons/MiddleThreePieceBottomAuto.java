package frc.robot.autons;

import java.io.IOException;
import java.util.Optional;

import org.json.simple.parser.ParseException;

import com.pathplanner.lib.util.FileVersionException;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.AcquireCommand;
import frc.robot.commands.DisposeCommand;
import frc.robot.commands.SetAcquisitionPositionCommand;
import frc.robot.commands.SetAcquisitionPositionCommand.AcquisitionPositionSetpoint;

public class MiddleThreePieceBottomAuto extends Auton {
    MiddleThreePieceBottomAuto(Optional<Alliance> alliance) throws FileVersionException, IOException, ParseException {
        super(alliance);

        super.addCommands(
                followPathCommand(Paths.getMiddlePosToLeftTag21Path())
                        .alongWith(new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.L4Coral)),
                new DisposeCommand(),
                followPathCommand(Paths.getLeftTag21ToBottomFeederStationPath())
                        .alongWith(new SequentialCommandGroup(
                                new WaitCommand(1),
                                new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.FeederStation))),
                new AcquireCommand(),
                followPathCommand(Paths.getBottomFeederStationToRightTag22Path())
                        .alongWith(new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.L4Coral)),
                new DisposeCommand(),
                followPathCommand(Paths.getRightTag22ToBottomFeederStationPath())
                        .alongWith(new SequentialCommandGroup(
                                new WaitCommand(1),
                                new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.FeederStation))),
                new AcquireCommand(),
                followPathCommand(Paths.getBottomFeederStationToLeftTag22Path())
                        .alongWith(new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.L4Coral)),
                new DisposeCommand());
    }
}
