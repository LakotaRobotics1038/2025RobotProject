package frc.robot.constants;

public class AcquisitionConstants {
    // To get sin value: kSinLookupTable[Math.round((deg % (2 * Math.PI)) / (2 *
    // Math.PI) * 100)]
    public static final float[] kSinLookupTable = {
            0.0f,
            0.06279051952931337f,
            0.12533323356430426f,
            0.1873813145857246f,
            0.2486898871648548f,
            0.3090169943749474f,
            0.3681245526846779f,
            0.4257792915650727f,
            0.4817536741017153f,
            0.5358267949789967f,
            0.5877852522924731f,
            0.6374239897486896f,
            0.6845471059286886f,
            0.7289686274214116f,
            0.7705132427757893f,
            0.8090169943749475f,
            0.8443279255020151f,
            0.8763066800438637f,
            0.9048270524660196f,
            0.9297764858882513f,
            0.9510565162951535f,
            0.9685831611286311f,
            0.9822872507286886f,
            0.9921147013144779f,
            0.9980267284282716f,
            1.0f,
            0.9980267284282716f,
            0.9921147013144778f,
            0.9822872507286886f,
            0.9685831611286312f,
            0.9510565162951536f,
            0.9297764858882513f,
            0.9048270524660195f,
            0.8763066800438635f,
            0.844327925502015f,
            0.8090169943749475f,
            0.7705132427757893f,
            0.7289686274214114f,
            0.6845471059286888f,
            0.6374239897486899f,
            0.5877852522924732f,
            0.535826794978997f,
            0.4817536741017156f,
            0.4257792915650729f,
            0.36812455268467814f,
            0.3090169943749475f,
            0.24868988716485482f,
            0.18738131458572502f,
            0.12533323356430454f,
            0.06279051952931358f
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
        return rad > Math.PI ? kSinLookupTable[-(int) Math.round(rad / (2 * Math.PI) * kSinLookupTable.length) - 1]
                : kSinLookupTable[(int) Math.round(rad / (2 * Math.PI) * kSinLookupTable.length) - 1];
    }

    public static float getCosine(double radian) {
        return getSine(radian + Math.PI * 0.5);
    }
}
