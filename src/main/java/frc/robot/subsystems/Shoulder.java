package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.AbsoluteEncoder;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.constants.NeoMotorConstants;
import frc.robot.constants.ShoulderConstants;

public class Shoulder extends SubsystemBase {
    public enum ShoulderSetpoints {
        L1Coral(0.0),
        L2Coral(0.0),
        L3Coral(0.0),
        L4Coral(0.0),
        L23Algae(0.0),
        L34Algae(0.0),
        Processor(0.0),
        GroundAlgae(0.0),
        Storage(0.0),
        FeederStation(0.0);

        public final double setpoint;

        private ShoulderSetpoints(double setpoint) {
            this.setpoint = setpoint;
        }

        public double getSetpoint() {
            return setpoint;
        }
    }

    private SparkMax shoulderLeft = new SparkMax(ShoulderConstants.kLeftMotorPort, MotorType.kBrushless);
    private SparkMax shoulderRight = new SparkMax(ShoulderConstants.kRightMotorPort, MotorType.kBrushless);
    private AbsoluteEncoder shoulderEncoder = shoulderLeft.getAbsoluteEncoder();
    private PIDController shoulderController = new PIDController(ShoulderConstants.kP, ShoulderConstants.kI,
            ShoulderConstants.kD);
    private static Shoulder instance;

    private Shoulder() {

        SparkMaxConfig leftShoulderConfig = new SparkMaxConfig();
        leftShoulderConfig.idleMode(IdleMode.kBrake)
                .smartCurrentLimit(NeoMotorConstants.kMaxNeoCurrent);

        SparkMaxConfig rightShoulderConfig = new SparkMaxConfig();
        rightShoulderConfig.idleMode(IdleMode.kBrake)
                .smartCurrentLimit(NeoMotorConstants.kMaxNeoCurrent);

        rightShoulderConfig.follow(shoulderLeft, true);

        shoulderLeft.configure(leftShoulderConfig, ResetMode.kResetSafeParameters,
                PersistMode.kPersistParameters);
        shoulderRight.configure(rightShoulderConfig, ResetMode.kResetSafeParameters,
                PersistMode.kPersistParameters);

        shoulderController.setTolerance(ShoulderConstants.kTolerance);
        shoulderController.enableContinuousInput(0, ShoulderConstants.kEncoderConversion);
    }

    public static Shoulder getInstance() {
        if (instance == null) {
            instance = new Shoulder();
        }

        return instance;
    }

    public void useOutput(double output, double setpoint) {
        double power = MathUtil.clamp(output, -ShoulderConstants.kMaxPower, ShoulderConstants.kMaxPower);
        setSetpoint(power);
    }

    public double getPosition() {
        return shoulderEncoder.getPosition();
    }

    public double getMeasurement() {
        return getPosition();
    }

    public boolean onTarget() {
        return shoulderController.atSetpoint();
    }

    private void setSetpoint(double setpoint) {
        setpoint = MathUtil.clamp(setpoint, 0, ShoulderConstants.kMaxDistance);
    }

    public void setSetpoint(ShoulderSetpoints setpoint) {
        shoulderController.setSetpoint(setpoint.getSetpoint());
    }

    public void setP(double p) {
        shoulderController.setP(p);
    }

    public void setI(double i) {
        shoulderController.setI(i);
    }

    public void setD(double d) {
        shoulderController.setD(d);
    }

}
