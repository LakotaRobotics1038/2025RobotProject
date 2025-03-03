package frc.robot.autons;

import java.io.IOException;
import java.util.Optional;

import org.json.simple.parser.ParseException;

import com.pathplanner.lib.util.FileVersionException;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.DisposeCoralCommand;
import frc.robot.commands.SetAcquisitionPositionCommand;
import frc.robot.utils.AcquisitionPositionSetpoint;

public class GetOutOfTheWayMiddleAuto extends Auton {
    GetOutOfTheWayMiddleAuto(Optional<Alliance> alliance) throws FileVersionException, IOException, ParseException {
        super(alliance);

        super.addCommands(
                followPathCommand(Paths.getMiddlePosToRightTag21Path())
                        .alongWith(new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.L4Coral)),
                new DisposeCoralCommand(),
                followPathCommand(Paths.getrightReefTag21ToOutOfWayPath())
                        .alongWith(new SequentialCommandGroup(
                                new WaitCommand(1),
                                new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.Storage))));
    }
}
