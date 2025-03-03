package frc.robot.autons;

import java.io.IOException;
import java.util.Optional;

import org.json.simple.parser.ParseException;

import com.pathplanner.lib.util.FileVersionException;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class MiddleThreePieceTopAuto extends Auton {
    MiddleThreePieceTopAuto(Optional<Alliance> alliance) throws FileVersionException, IOException, ParseException {
        super(alliance);

        super.addCommands(
                followPathCommand(Paths.getMiddlePosToRightTag21Path()),
                followPathCommand(Paths.getRightTag21ToTopFeederStationPath()),
                followPathCommand(Paths.getTopFeederStationToLeftTag20Path()));
    }
}
