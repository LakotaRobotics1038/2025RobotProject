package frc.robot.autons;

import java.io.IOException;
import java.util.Optional;

import org.json.simple.parser.ParseException;

import com.pathplanner.lib.util.FileVersionException;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.DisposeCommand;
import frc.robot.commands.SetAcquisitionPositionCommand;
import frc.robot.commands.SetAcquisitionPositionCommand.AcquisitionPositionSetpoint;

public class GetOutOfTheWayTopAuto extends Auton {
    GetOutOfTheWayTopAuto(Optional<Alliance> alliance) throws FileVersionException, IOException, ParseException {
        super(alliance);

        super.addCommands(
                followPathCommand(Paths.getTopPosToRightReefTag20Path())
                        .alongWith(new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.L4Coral)),
                new DisposeCommand(),
                followPathCommand(Paths.getRightReefTag20ToOutOfWayPath())
                        .alongWith(new SequentialCommandGroup(
                                new WaitCommand(1),
                                new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.Storage))));
    }
}
