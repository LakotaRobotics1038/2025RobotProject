package frc.robot.autons;

import java.io.IOException;

import org.json.simple.parser.ParseException;

import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.util.FileVersionException;

public class Paths {
    public static PathPlannerPath getLeftPosTaxiPath()
            throws IOException, ParseException, FileVersionException {
        return PathPlannerPath.fromPathFile("TopStartingPos Taxi");
    }

    public static PathPlannerPath getRightPosTaxiPath()
            throws IOException, ParseException, FileVersionException {
        return PathPlannerPath.fromPathFile("BottomStartingPos Taxi");
    }

    public static PathPlannerPath getMidPoseToTag21Algae()
            throws IOException, ParseException, FileVersionException {
        return PathPlannerPath.fromPathFile("Mid Pose to 21 Algae");
    }

    public static PathPlannerPath getReefTag21ToNet()
            throws IOException, ParseException, FileVersionException {
        return PathPlannerPath.fromPathFile("Reef Tag 21 To Net");
    }

    public static PathPlannerPath getNetToTag20Algae()
            throws IOException, ParseException, FileVersionException {
        return PathPlannerPath.fromPathFile("Net to Tag 20 Algae");
    }

    public static PathPlannerPath getReefTag20ToNet()
            throws IOException, ParseException, FileVersionException {
        return PathPlannerPath.fromPathFile("Reef Tag 20 To Net");
    }
}