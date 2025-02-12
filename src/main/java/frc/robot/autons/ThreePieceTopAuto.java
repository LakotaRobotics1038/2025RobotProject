package frc.robot.autons;

import java.nio.file.Paths;
import java.util.Optional;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

public class ThreePieceTopAuto extends Auton {
    ThreePieceTopAuto(Optional<Alliance> alliance) {
        super(alliance);

        super.addCommands(
        // followPathCommand()
        );
    }
}
