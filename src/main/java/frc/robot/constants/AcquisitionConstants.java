package frc.robot.constants;

public class AcquisitionConstants {
    public static final short[] kSinLookupTable = {
            0,
            628,
            1253,
            1874,
            2487,
            3090,
            3681,
            4258,
            4818,
            5358,
            5878,
            6374,
            6845,
            7290,
            7705,
            8090,
            8443,
            8763,
            9048,
            9298,
            9511,
            9686,
            9823,
            9921,
            9980,
            10000,
            9980,
            9921,
            9823,
            9686,
            9511,
            9298,
            9048,
            8763,
            8443,
            8090,
            7705,
            7290,
            6845,
            6374,
            5878,
            5358,
            4818,
            4258,
            3681,
            3090,
            2487,
            1874,
            1253,
            628
    };

    public static final int kTopLaserPort = 1;
    public static final int kBottomLaserPort = 0;
    public static final int kAcquisitionMotorPort = 4;
    public static final double kAcquireCoralSpeed = 1.0;
    public static final double kAcquireCoralSpeedL4 = -0.1;
    public static final double kAcquireAlgaeSpeed = 1.0;
    public static final double kDisposeCoralSpeed134 = 0.5;
    public static final double kDisposeCoralSpeed2 = -0.5;
    public static final double kDisposeAlgaeSpeed = -1.0;
    public static final double kShootAlgaeSpeed = -1.0;

    public static float getSine(double radian) {
        double rad = radian % (Math.PI * 2);
        return rad > Math.PI
                ? kSinLookupTable[(int) Math.round(rad / (2 * Math.PI) * kSinLookupTable.length) - 1] / -10000
                : kSinLookupTable[(int) Math.round(rad / (2 * Math.PI) * kSinLookupTable.length) - 1] / 10000;
    }

    public static float getCosine(double radian) {
        return getSine(radian + Math.PI * 0.5);
    }
}
