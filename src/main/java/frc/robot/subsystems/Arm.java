package frc.robot.subsystems;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkLimitSwitch;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.LimitSwitchConfig.Type;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkFlexConfig;

import au.grapplerobotics.LaserCan;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.ArmConstants;
import frc.robot.constants.ArmConstants.ArmSetpoint;
import frc.robot.constants.NeoMotorConstants;

public class Arm extends SubsystemBase {
    private SparkFlex armMotor = new SparkFlex(ArmConstants.kArmMotorPort, MotorType.kBrushless);
    private PIDController armController = new PIDController(ArmConstants.kP, ArmConstants.kI, ArmConstants.kD);
    private SparkLimitSwitch limitSwitch = armMotor.getReverseLimitSwitch();
    private LaserCan laser = new LaserCan(ArmConstants.kArmLaserPort);
    private static Arm instance;

    /**
     * Creates an instance of the Arm subsystem if it does not exist.
     *
     * @return An instance of the arm subsystem
     */
    public static Arm getInstance() {
        if (instance == null) {
            instance = new Arm();
        }
        return instance;
    }

    private Arm() {
        SparkFlexConfig armConfig = new SparkFlexConfig();
        armConfig.idleMode(IdleMode.kBrake)
                .smartCurrentLimit(NeoMotorConstants.kMaxVortexCurrent);

        armMotor.configure(armConfig, ResetMode.kResetSafeParameters,
                PersistMode.kPersistParameters);

        armConfig.limitSwitch
                .reverseLimitSwitchType(Type.kNormallyOpen)
                .reverseLimitSwitchEnabled(true);

        armController.setTolerance(ArmConstants.kTolerance);
        armController.disableContinuousInput();
    }

    protected void useOutput(double output, double setpoint) {
        double power = MathUtil.clamp(output, -ArmConstants.kMaxArmPower, ArmConstants.kMaxArmPower);
        armMotor.set(power);
    }

    /**
     * Returns current distance recieved by the Arm laser as a double in milimetres
     *
     * @return laser distance is milimetres
     */
    public double getPosition() {
        LaserCan.Measurement measurement = laser.getMeasurement();
        if (measurement != null && measurement.status == LaserCan.LASERCAN_STATUS_VALID_MEASUREMENT) {
            return laser.getMeasurement().distance_mm;
        }
        return 0.0;
    }

    /**
     * Returns whether or not the PID controller is on target as a boolean
     *
     * @return boolean whether or not the PID controller is at setpoint
     */
    public boolean onTarget() {
        return armController.atSetpoint();
    }

    /**
     * Returns whether or not the limit switch is pressed as a boolean
     *
     * @return boolean whether or not the limit switch is pressed
     */
    public boolean isPressed() {
        return limitSwitch.isPressed();
    }

    /**
     * Sets the setpoint for the subsystem.
     *
     * @param setpoint the setpoint for the subsystem
     */
    public void setSetpoint(ArmSetpoint setpoint) {
        setSetpoint(setpoint.position);
    }

    /**
     * Sets the setpoint for the subsystem.
     *
     * @param setpoint the setpoint for the subsystem
     */
    private final void setSetpoint(double setpoint) {
        armController.setSetpoint(setpoint);
    }

    /**
     * Sets the P for the PID controller.
     *
     * @param P the P to set to the PID controller
     */
    public void setP(double P) {
        armController.setP(P);
    }

    /**
     * Sets the I for the PID controller.
     *
     * @param I the I to set to the PID controller
     */
    public void setI(double I) {
        armController.setP(I);
    }

    /**
     * Sets the D for the PID controller.
     *
     * @param D the D to set to the PID controller
     */
    public void setD(double D) {
        armController.setP(D);
    }
}
