package frc.robot;

import frc.robot.utils.AcquisitionPositionSetpoint;

public class OperatorState {
    private static AcquisitionPositionSetpoint lastInput;
    private static boolean scoringFlipped;

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
                return true;
            default:
                return false;
        }
    }

    public boolean isBarge() {
        return getLastInput().equals(AcquisitionPositionSetpoint.Barge);
    }

    public void setLastInput(AcquisitionPositionSetpoint lastInput) {
        OperatorState.lastInput = lastInput;
    }

    public void setScoringFlipped(boolean scoringFlipped) {
        OperatorState.scoringFlipped = scoringFlipped;
    }

    public static boolean isScoringFlipped() {
        return scoringFlipped;
    }
}
