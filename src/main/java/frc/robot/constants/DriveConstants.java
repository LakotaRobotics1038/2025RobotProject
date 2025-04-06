package frc.robot.constants;

import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.RotationsPerSecond;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N3;

public final class DriveConstants {
    public static final Matrix<N3, N1> kOdometryStdDev = VecBuilder.fill(0.1, 0.1, 0.1);
    public static final Matrix<N3, N1> kVisionStdDevs = VecBuilder.fill(1, 1, 1);

    public static final double defaultMaxPower = 0.75;
    public static final double overdrivePower = 1.0;

    // Chassis configuration
    // Distance between centers of right and left wheels on robot
    public static final double kTrackWidth = 25; // Inches
    // Distance between front and back wheels on robot
    public static final double kWheelBase = 24; // Inches

    public static final double kFineAdjustmentPercent = 0.2;

    public static final double MaxSpeed = SwerveConstants.kSpeedAt12Volts.in(MetersPerSecond); // kSpeedAt12Volts
                                                                                               // desired top
    // speed
    public static final double MaxAngularRate = RotationsPerSecond.of(0.75).in(RadiansPerSecond); // 3/4 of a rotation
                                                                                                  // per
    // second
    // max angular velocity
}