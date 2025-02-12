package frc.robot;

public class OperatorState {
    private AcquisitionPositionSetpoint lastInput;
    private ScoringSide scoringSide;

    public enum ScoringSide {
        LEFT, RIGHT;
    }

    private OperatorState() {
    }

    private static OperatorState instance;

    public static OperatorState getInstance() {
        if (instance == null) {
            instance = new OperatorState();
        }

        return instance;
    }

    public AcquisitionPositionSetpoint getLastInput() {
        return this.lastInput;
    }

    public void setLastInput(AcquisitionPositionSetpoint lastInput) {
        this.lastInput = lastInput;
    }

    public void setScoringSide(ScoringSide scoringSide) {
        this.scoringSide = scoringSide;
    }

    public ScoringSide getScoringSide() {
        return this.scoringSide;
    }
}
