package frc.robot.autons;

import java.io.IOException;
import java.util.Optional;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.IdealStartingState;
import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.util.FileVersionException;
import com.pathplanner.lib.util.PathPlannerLogging;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.robot.subsystems.Dashboard;

import org.json.simple.parser.ParseException;

public class FollowAPath extends Auton {

    private Dashboard dashboard = Dashboard.getInstance();

    FollowAPath(Optional<Alliance> alliance) throws FileVersionException, IOException, ParseException {
        super(alliance);

        PathPlannerLogging.setLogActivePathCallback((activePath) -> {
            try {
                PathPlannerPath dashboardPath = Paths.getStrightPath();
                this.dashboard.addPath(dashboardPath);
                System.out.println("Doneerewr");
            } catch (Exception e) {
                System.out.println("Exception" + e);
            }
        });

        super.addCommands(
                AutoBuilder.followPath(Paths.getStrightPath()));
    }
}
