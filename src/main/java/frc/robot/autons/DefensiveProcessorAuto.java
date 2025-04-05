package frc.robot.autons;

import java.io.IOException;
import java.util.Optional;

import org.json.simple.parser.ParseException;

import com.pathplanner.lib.util.FileVersionException;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.robot.commands.AcquireAlgaeCommand;
import frc.robot.commands.DisposeAlgaeCommand;
import frc.robot.commands.SetAcquisitionPositionCommand;
import frc.robot.commands.SetAcquisitionPositionCommand.FinishActions;
import frc.robot.utils.AcquisitionPositionSetpoint;

public class DefensiveProcessorAuto extends Auton {
    DefensiveProcessorAuto(Optional<Alliance> alliance) throws FileVersionException, IOException, ParseException {
        super(alliance);

        super.addCommands(
                new AcquireAlgaeCommand()
                        .raceWith(
                                new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.L23Algae,
                                        FinishActions.NoDisable)
                                        .alongWith(followPathCommand(Paths.getMidPoseToTag21Algae()))),
                new AcquireAlgaeCommand()
                        .raceWith(followPathCommand(Paths.getReefTag21ToProcessor()))
                        .alongWith(new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.Processor,
                                FinishActions.NoDisable).withTimeout(2)),
                new DisposeAlgaeCommand()
                        .withTimeout(0.5),

                new SetAcquisitionPositionCommand(AcquisitionPositionSetpoint.StartingConfig,
                        SetAcquisitionPositionCommand.FinishActions.NoFinish)
                        .raceWith(followPathCommand(Paths.getProcessorTaxi())));
    }

}
