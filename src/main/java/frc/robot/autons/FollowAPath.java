package frc.robot.autons;

import java.io.IOException;
import java.util.Optional;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.util.FileVersionException;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import org.json.simple.parser.ParseException;

public class FollowAPath extends Auton {
    FollowAPath(Optional<Alliance> alliance) throws FileVersionException, IOException, ParseException {
        super(alliance);

        super.addCommands(
                AutoBuilder.followPath(Paths.getStrightPath()));
    }
}
