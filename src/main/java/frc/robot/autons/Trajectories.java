package frc.robot.autons;

import java.io.IOException;

import org.json.simple.parser.ParseException;

import com.ctre.phoenix6.swerve.SwerveDrivetrain.SwerveDriveState;
import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.trajectory.PathPlannerTrajectory;
import com.pathplanner.lib.util.FileVersionException;

import frc.robot.constants.AutoConstants;
import frc.robot.subsystems.DriveTrain;

public class Trajectories {
    private static PathPlannerTrajectory getTrajectory(String pathName)
            throws IOException, ParseException, FileVersionException {
        PathPlannerPath path = PathPlannerPath.fromPathFile(pathName);
        SwerveDriveState driveState = DriveTrain.getInstance().getState();
        return new PathPlannerTrajectory(path, driveState.Speeds,
                driveState.RawHeading,
                AutoConstants.kRobotConfig.get());

    }

    public static PathPlannerTrajectory getMiddlePosToRightTag21Trajectory()
            throws IOException, ParseException, FileVersionException {
        return Trajectories.getTrajectory("StartMiddlePos to RightReefTag21");
    }

    public static PathPlannerTrajectory getRightTag21ToTopFeederStationTrajectory()
            throws IOException, ParseException, FileVersionException {
        return Trajectories.getTrajectory("RightReefTag21 To TopFeederStation");
    }

    public static PathPlannerTrajectory getTopFeederStationToLeftTag20Trajectory()
            throws IOException, ParseException, FileVersionException {
        return Trajectories.getTrajectory("TopFeederStation to LeftReefTag20");
    }

    public static PathPlannerTrajectory getLeftTag20ToTopFeederStationTrajectory()
            throws IOException, ParseException, FileVersionException {
        return Trajectories.getTrajectory("LeftReefTag20 to TopFeederStation");
    }

    public static PathPlannerTrajectory getTopFeederStationToRightTag20Trajectory()
            throws IOException, ParseException, FileVersionException {
        return Trajectories.getTrajectory("TopFeederStation to RightReefTag20");
    }
}