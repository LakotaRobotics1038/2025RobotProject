package frc.robot;

import frc.robot.utils.AcquisitionPositionSetpoint;

public class OperatorState {
    private AcquisitionPositionSetpoint lastInput;
    private boolean scoringFlipped;

    private OperatorState() {
        this.lastInput = AcquisitionPositionSetpoint.Storage;
    }

    private static OperatorState instance;

    public static OperatorState getInstance() {
        if (instance == null) {
            instance = new OperatorState();
        }

        return instance;
    }

    public AcquisitionPositionSetpoint getLastInput() {
        return lastInput;
    }

    public boolean isAlgae() {
        switch (getLastInput()) {
            case L23Algae:
            case L34Algae:
            case Processor:
            case GroundAlgae:
                return true;
            default:
                return false;
        }
    }

    public boolean isGroundAlgae() {
        return getLastInput() == AcquisitionPositionSetpoint.GroundAlgae;
    }

    public boolean isBarge() {
        return getLastInput().equals(AcquisitionPositionSetpoint.Barge);
    }

    public void setLastInput(AcquisitionPositionSetpoint lastInput) {
        this.lastInput = lastInput;
    }

    public void setScoringFlipped(boolean scoringFlipped) {
        this.scoringFlipped = scoringFlipped;
    }

    public boolean isScoringFlipped() {
        return scoringFlipped;
    }
}
