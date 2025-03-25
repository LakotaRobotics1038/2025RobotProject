package frc.robot.autons;

import java.io.IOException;
import java.util.Optional;

import org.json.simple.parser.ParseException;

import com.pathplanner.lib.util.FileVersionException;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.robot.commands.SetAcquisitionPositionEscape;

public class BottomTaxi extends Auton {
    BottomTaxi(Optional<Alliance> alliance) throws FileVersionException, IOException, ParseException {
        super(alliance);

        super.addCommands(
                new SetAcquisitionPositionEscape(SetAcquisitionPositionEscape.FinishActions.Default),
                followPathCommand(Paths.getBottomPosTaxiPath()));
    }
}
