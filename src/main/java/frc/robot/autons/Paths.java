package frc.robot.autons;

import java.io.IOException;

import org.json.simple.parser.ParseException;

import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.util.FileVersionException;

public class Paths {

    public static PathPlannerPath getMiddlePosToRightTag21Path()
            throws IOException, ParseException, FileVersionException {
        return PathPlannerPath.fromPathFile("StartMiddlePos to RightReefTag21");
    }

    public static PathPlannerPath getRightTag21ToTopFeederStationPath()
            throws IOException, ParseException, FileVersionException {
        return PathPlannerPath.fromPathFile("RightReefTag21 To TopFeederStation");
    }

    public static PathPlannerPath getTopFeederStationToLeftTag20Path()
            throws IOException, ParseException, FileVersionException {
        return PathPlannerPath.fromPathFile("TopFeederStation to LeftReefTag20");
    }

    public static PathPlannerPath getLeftTag20ToTopFeederStationPath()
            throws IOException, ParseException, FileVersionException {
        return PathPlannerPath.fromPathFile("LeftReefTag20 to TopFeederStation");
    }

    public static PathPlannerPath getTopFeederStationToRightTag20Path()
            throws IOException, ParseException, FileVersionException {
        return PathPlannerPath.fromPathFile("TopFeederStation to RightReefTag20");
    }

    public static PathPlannerPath getMiddlePosToLeftTag21Path()
            throws IOException, ParseException, FileVersionException {
        return PathPlannerPath.fromPathFile("StartMiddlePos To LeftReefTag21");
    }

    public static PathPlannerPath getLeftTag21ToBottomFeederStationPath()
            throws IOException, ParseException, FileVersionException {
        return PathPlannerPath.fromPathFile("LeftReefTag21 To BottomFeederStation");
    }

    public static PathPlannerPath getBottomFeederStationToRightTag17Path()
            throws IOException, ParseException, FileVersionException {
        return PathPlannerPath.fromPathFile("BottomFeederStation To RightReefTag17");
    }

    public static PathPlannerPath getRightTag17ToBottomFeederStationPath()
            throws IOException, ParseException, FileVersionException {
        return PathPlannerPath.fromPathFile("RightReefTag17 To BottomFeederStation");
    }

    public static PathPlannerPath getBottomFeederStationToLeftTag17Path()
            throws IOException, ParseException, FileVersionException {
        return PathPlannerPath.fromPathFile("BottomFeederStation To LeftReefTag17");
    }

    public static PathPlannerPath getTopPosToRightReefTag20Path()
            throws IOException, ParseException, FileVersionException {
        return PathPlannerPath.fromPathFile("StartTopPos to RightReefTag20");
    }

    public static PathPlannerPath getRightReefTag20ToTopFeederStationPath()
            throws IOException, ParseException, FileVersionException {
        return PathPlannerPath.fromPathFile("RightReefTag20 To TopFeederStation");
    }

    public static PathPlannerPath getBottomStartingPosToLeftReefTag22Path()
            throws IOException, ParseException, FileVersionException {
        return PathPlannerPath.fromPathFile("BottomStartingPos to LeftReefTag22");
    }

    public static PathPlannerPath getLeftReefTag22ToBottomFeederStationPath()
            throws IOException, ParseException, FileVersionException {
        return PathPlannerPath.fromPathFile("LeftReefTag22 To BottomFeederStation");
    }

    public static PathPlannerPath getRightReefTag20ToOutOfWayPath()
            throws IOException, ParseException, FileVersionException {
        return PathPlannerPath.fromPathFile("RightReefTag20 To OutOfWay");
    }

    public static PathPlannerPath getLeftReefTag22ToOutOfWayPath()
            throws IOException, ParseException, FileVersionException {
        return PathPlannerPath.fromPathFile("LeftReefTag22 To OutOfWay");
    }

    public static PathPlannerPath getrightReefTag21ToOutOfWayPath()
            throws IOException, ParseException, FileVersionException {
        return PathPlannerPath.fromPathFile("RightReefTag21 to outOfWay");
    }

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

    public static PathPlannerPath getLeftReefTag21ToProcessorPath()
            throws IOException, ParseException, FileVersionException {
        return PathPlannerPath.fromPathFile("LeftReefTag21 to Processor");
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

    public static PathPlannerPath getTopPathToTag20Algae()
            throws IOException, ParseException, FileVersionException {
        return PathPlannerPath.fromPathFile("Top Pos to Tag 20 Algae");
    }

    public static PathPlannerPath getReefTag21ToNet()
            throws IOException, ParseException, FileVersionException {
        return PathPlannerPath.fromPathFile("Reef Tag 21 To Net");
    }

    public static PathPlannerPath getNetToReefTag21Algae()
            throws IOException, ParseException, FileVersionException {
        return PathPlannerPath.fromPathFile("Net to Reef Tag 21 Algae");
    }

    public static PathPlannerPath getReefTag20ToNet()
            throws IOException, ParseException, FileVersionException {
        return PathPlannerPath.fromPathFile("Reef Tag 20 To Net");
    }
}