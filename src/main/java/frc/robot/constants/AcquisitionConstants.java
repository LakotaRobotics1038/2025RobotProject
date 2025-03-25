package frc.robot.constants;

public class AcquisitionConstants {
    public static final short[] kSinLookupTable = {
            0,
            314,
            628,
            941,
            1253,
            1564,
            1874,
            2181,
            2487,
            2790,
            3090,
            3387,
            3681,
            3971,
            4258,
            4540,
            4818,
            5090,
            5358,
            5621,
            5878,
            6129,
            6374,
            6613,
            6845,
            7071,
            7290,
            7501,
            7705,
            7902,
            8090,
            8271,
            8443,
            8607,
            8763,
            8910,
            9048,
            9178,
            9298,
            9409,
            9511,
            9603,
            9686,
            9759,
            9823,
            9877,
            9921,
            9956,
            9980,
            9995
    };

    public static final int kTopLaserPort = 1;
    public static final int kBottomLaserPort = 0;
    public static final int kAcquisitionMotorPort = 4;
    public static final double kAcquireAlgaeSpeed = 1.0;
    public static final double kDisposeAlgaeSpeed = -1.0;
    public static final double kShootAlgaeSpeed = -1.0;

    public static float getSine(double radian) {
        double rad = radian % (Math.PI * 2);
        short sine;
        if ((Math.abs(rad) > Math.PI * 0.5 && Math.abs(rad) < Math.PI) || Math.abs(rad) > Math.PI * 1.5) {
            sine = kSinLookupTable[(int) Math
                    .round((1 - Math.abs(rad) / (Math.PI * 0.5) % 1) * (kSinLookupTable.length - 1))];
        } else {
            sine = kSinLookupTable[(int) Math
                    .round(Math.abs(rad) / (Math.PI * 0.5) % 1 * (kSinLookupTable.length - 1))];
        }
        if ((rad >= 0 && rad < Math.PI) || rad < -Math.PI) {
            return sine / 10000.f;
        } else {
            return -sine / 10000.f;
        }
    }

    public static float getCosine(double radian) {
        return getSine(radian + Math.PI * 0.5);
    }
}