package frc.robot;

import frc.robot.utils.AcquisitionPositionSetpoint;

public class OperatorState {
    private static AcquisitionPositionSetpoint lastInput;
    private static ScoringSide scoringSide;

    public enum ScoringSide {
        LEFT, RIGHT;
    }

    private OperatorState() {
        lastInput = AcquisitionPositionSetpoint.Storage;
    }

    private static OperatorState instance;

    public static OperatorState getInstance() {
        if (instance == null) {
            instance = new OperatorState();
        }

        return instance;
    }

    public static AcquisitionPositionSetpoint getLastInput() {
        return lastInput;
    }

    public boolean isCoral() {
        switch (getLastInput()) {
            case L1Coral:
            case L2Coral:
            case L3Coral:
            case L4Coral:
            case FeederStation:
                return true;
            default:
                return false;
        }
    }

    public boolean isAlgae() {
        switch (getLastInput()) {
            case L23Algae:
            case L34Algae:
            case Processor:
            case Barge:
                return true;
            default:
                return false;
        }
    }

    public void setLastInput(AcquisitionPositionSetpoint lastInput) {
        this.lastInput = lastInput;
    }

    public void setScoringSide(ScoringSide scoringSide) {
        OperatorState.scoringSide = scoringSide;
    }

    public static ScoringSide getScoringSide() {
        return scoringSide;
    }
}
