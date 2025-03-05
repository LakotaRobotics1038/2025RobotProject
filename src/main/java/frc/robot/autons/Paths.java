package frc.robot.autons;

import java.io.IOException;
import org.json.simple.parser.ParseException;

import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.util.FileVersionException;

public class Paths {
    public static PathPlannerPath getStrightPath()
            throws IOException, ParseException, FileVersionException {
        return PathPlannerPath.fromPathFile("Example Path");
    }
}
