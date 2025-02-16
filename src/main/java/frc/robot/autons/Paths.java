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
        return PathPlannerPath.fromPathFile("LeftReefTag20 to TopFeederStation");
    }

    public static PathPlannerPath getMiddlePosToLeftTag21Path()
            throws IOException, ParseException, FileVersionException {
        return PathPlannerPath.fromPathFile("StartMiddlePos To LeftReefTag21");
    }

    public static PathPlannerPath getLeftTag21ToBottomFeederStationPath()
            throws IOException, ParseException, FileVersionException {
        return PathPlannerPath.fromPathFile("LeftReefTag21 To BottomFeederStation");
    }

    public static PathPlannerPath getBottomFeederStationToRightTag22Path()
            throws IOException, ParseException, FileVersionException {
        return PathPlannerPath.fromPathFile("BottomFeederStation To RightReefTag22");
    }

    public static PathPlannerPath getRightTag22ToBottomFeederStationPath()
            throws IOException, ParseException, FileVersionException {
        return PathPlannerPath.fromPathFile("RightReefTag22 To BottomFeederStation");
    }

    public static PathPlannerPath getBottomFeederStationToLeftTag22Path()
            throws IOException, ParseException, FileVersionException {
        return PathPlannerPath.fromPathFile("BottomFeederStation To LeftReefTag22");
    }
}