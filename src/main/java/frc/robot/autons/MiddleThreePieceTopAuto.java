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

public class MiddleThreePieceTopAuto extends Auton {
    MiddleThreePieceTopAuto(Optional<Alliance> alliance) throws FileVersionException, IOException, ParseException {
        super(alliance);

        super.addCommands(
                followPathCommand(Paths.getMiddlePosToRightTag21Path())
                        .alongWith(new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.L4Coral)),
                new DisposeCommand(),
                followPathCommand(Paths.getRightTag21ToTopFeederStationPath())
                        .alongWith(new SequentialCommandGroup(
                                new WaitCommand(1),
                                new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.FeederStation))),
                new AcquireCommand(),
                followPathCommand(Paths.getTopFeederStationToLeftTag20Path())
                        .alongWith(new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.L4Coral)),
                new DisposeCommand(),
                followPathCommand(Paths.getLeftTag20ToTopFeederStationPath())
                        .alongWith(new SequentialCommandGroup(
                                new WaitCommand(1),
                                new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.FeederStation))),
                new AcquireCommand(),
                followPathCommand(Paths.getTopFeederStationToRightTag20Path())
                        .alongWith(new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.L4Coral)),
                new DisposeCommand());
    }
}
