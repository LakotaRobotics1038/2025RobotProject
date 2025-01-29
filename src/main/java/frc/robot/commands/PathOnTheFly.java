package frc.robot.commands;

import com.pathplanner.lib.commands.PathfindingCommand;
import com.pathplanner.lib.config.ModuleConfig;
import com.pathplanner.lib.config.PIDConstants;
import com.pathplanner.lib.config.RobotConfig;
import com.pathplanner.lib.controllers.PPHolonomicDriveController;
import com.pathplanner.lib.path.GoalEndState;
import com.pathplanner.lib.path.IdealStartingState;
import com.pathplanner.lib.path.PathConstraints;
import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.pathfinding.LocalADStar;
import com.pathplanner.lib.pathfinding.Pathfinding;
import com.pathplanner.lib.util.PathPlannerLogging;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.AutoConstants;
import frc.robot.constants.DriveConstants;
import frc.robot.subsystems.Dashboard;
import frc.robot.subsystems.DriveTrain;

import static edu.wpi.first.units.Units.Rotation;

import com.pathplanner.lib.commands.FollowPathCommand;

public class PathOnTheFly extends Command {
    private PathPlannerPath path;
    private PathConstraints constraints;
    private IdealStartingState idealStartingState;
    private GoalEndState goalEndState;
    private Dashboard dashboard;
    private DriveTrain driveTrain;

    public PathOnTheFly() {
        this.driveTrain = DriveTrain.getInstance();
        this.dashboard = Dashboard.getInstance();
        super.addRequirements(this.driveTrain, this.dashboard);
    }

    @Override
    public void initialize() {
        this.constraints = new PathConstraints(DriveConstants.kMaxSpeedMetersPerSecond,
                AutoConstants.kMaxAccelerationMetersPerSecondSquared,
                AutoConstants.kMaxAngularSpeedRadiansPerSecond,
                AutoConstants.kMaxAngularSpeedRadiansPerSecondSquared);
        this.idealStartingState = new IdealStartingState(driveTrain.getChassisSpeeds().vxMetersPerSecond,
                Rotation2d.fromDegrees(driveTrain.getHeading()));
        this.goalEndState = new GoalEndState(0, new Rotation2d(0));
        PathPlannerLogging.setLogActivePathCallback((activePath) -> {
            if (activePath.size() >= 2) {
                this.path = new PathPlannerPath(PathPlannerPath.waypointsFromPoses(activePath), constraints,
                        idealStartingState, goalEndState);
                dashboard.addPath(path);
            }
        });
    }

    @Override
    public void execute() {
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void end(boolean interrupted) {
    }
}
