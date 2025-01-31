package frc.robot.autons;

import java.io.IOException;

import org.json.simple.parser.ParseException;

import com.ctre.phoenix6.swerve.SwerveDrivetrain.SwerveDriveState;
import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.trajectory.PathPlannerTrajectory;
import com.pathplanner.lib.util.FileVersionException;

import frc.robot.subsystems.DriveTrain;
import frc.robot.constants.AutoConstants;

public class Trajectories {
    public static PathPlannerTrajectory getFromAmpToNote1Trajectory()
            throws IOException, ParseException, FileVersionException {
        PathPlannerPath path = PathPlannerPath.fromPathFile("From position 1 to amp");
        SwerveDriveState driveState = DriveTrain.getInstance().getState();
        return new PathPlannerTrajectory(path, driveState.Speeds,
                driveState.RawHeading,
                AutoConstants.kRobotConfig.get());

    }
}