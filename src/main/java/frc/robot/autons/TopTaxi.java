package frc.robot.autons;

import java.io.IOException;
import java.util.Optional;

import org.json.simple.parser.ParseException;

import com.pathplanner.lib.util.FileVersionException;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.robot.commands.SetAcquisitionPositionEscapeCommand;

public class TopTaxi extends Auton {
    TopTaxi(Optional<Alliance> alliance) throws FileVersionException, IOException, ParseException {
        super(alliance);

        super.addCommands(
                new SetAcquisitionPositionEscapeCommand(SetAcquisitionPositionEscapeCommand.FinishActions.Default),
                followPathCommand(Paths.getTopPosTaxiPath()));
    }
}
