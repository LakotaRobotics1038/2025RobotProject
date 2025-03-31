package frc.robot.constants;

public class AcquisitionConstants {
    public static final int kTopLaserPort = 1;
    public static final int kBottomLaserPort = 0;
    public static final int kAcquisitionMotorPort = 4;
    public static final double kAcquireAlgaeSpeed = 1.0;
    public static final double kDisposeAlgaeSpeed = -1.0;
    public static final double kShootAlgaeSpeed = -1.0;

    public static double cos(double radian) {
        radian *= 0.15915494309189535;
        radian -= 0.25 + Math.floor(radian + 0.25);
        radian *= 16.0 * (Math.abs(radian) - 0.5);
        return radian;
    }

    public static double sin(double radian) {
        return cos(radian - 1.5707963267948966);
    }

    public static double acos(double radian) {
        return (-0.798325 * radian * radian - 0.686357) * radian + 1.570796;
    }

    public static double asin(double radian) {
        return -acos(radian) + 1.5707963267948966;
    }
}