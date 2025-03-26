package frc.robot.autons;

import java.io.IOException;

import org.json.simple.parser.ParseException;

import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.util.FileVersionException;

public class Paths {
    public static PathPlannerPath getTopPosTaxiPath()
            throws IOException, ParseException, FileVersionException {
        return PathPlannerPath.fromPathFile("TopStartingPos Taxi");
    }

    public static PathPlannerPath getBottomPosTaxiPath()
            throws IOException, ParseException, FileVersionException {
        return PathPlannerPath.fromPathFile("BottomStartingPos Taxi");
    }

    public static PathPlannerPath getLeftReefTag21ForwardsPath()
            throws IOException, ParseException, FileVersionException {
        return PathPlannerPath.fromPathFile("LeftReefTag21 Forwards");
    }

    public static PathPlannerPath getLeftReefTag21BackwardsPath()
            throws IOException, ParseException, FileVersionException {
        return PathPlannerPath.fromPathFile("LeftReefTag21 Backwards");
    }

    public static PathPlannerPath getReefTag21ToProcessor()
            throws IOException, ParseException, FileVersionException {
        return PathPlannerPath.fromPathFile("Reef 21 to processor");
    }

    public static PathPlannerPath getReefTag22ToProcessor()
            throws IOException, ParseException, FileVersionException {
        return PathPlannerPath.fromPathFile("Reef Tag 22 Algae To Processor");
    }

    public static PathPlannerPath getProcessorToAlgaePath22()
            throws IOException, ParseException, FileVersionException {
        return PathPlannerPath.fromPathFile("Processor to Algae Tag 22");
    }

    public static PathPlannerPath getMidPoseToTag21Algae()
            throws IOException, ParseException, FileVersionException {
        return PathPlannerPath.fromPathFile("Mid Pose to 21 Algae");
    }

    public static PathPlannerPath getNetToTag20Algae()
            throws IOException, ParseException, FileVersionException {
        return PathPlannerPath.fromPathFile("Net to Tag 20 Algae");
    }

    public static PathPlannerPath getReefTag21ToNet()
            throws IOException, ParseException, FileVersionException {
        return PathPlannerPath.fromPathFile("Reef Tag 21 To Net");
    }

    public static PathPlannerPath getNetToReefTag22Algae()
            throws IOException, ParseException, FileVersionException {
        return PathPlannerPath.fromPathFile("Net to Reef Tag 22 Algae");
    }

    public static PathPlannerPath getReefTag20ToNet()
            throws IOException, ParseException, FileVersionException {
        return PathPlannerPath.fromPathFile("Reef Tag 20 To Net");
    }
}