package frc.robot.autons;

import java.io.IOException;
import java.util.Optional;

import org.json.simple.parser.ParseException;

import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.trajectory.PathPlannerTrajectory;
import com.pathplanner.lib.util.FileVersionException;

import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.subsystems.DriveTrain;
import frc.robot.constants.AutoConstants;

public class Trajectories {
    public static PathPlannerTrajectory getFromAmpToNote1Trajectory()
            throws IOException, ParseException, FileVersionException {
        PathPlannerPath path = PathPlannerPath.fromPathFile("From position 1 to amp");
        return new PathPlannerTrajectory(path, DriveTrain.getInstance().getChassisSpeeds(),
                Rotation2d.fromDegrees(DriveTrain.getInstance().getHeading()),
                AutoConstants.kRobotConfig.get());

    }
}