package frc.robot;

public class ControlPanelSetpoint {

    private static ControlPanelSetpoint instance;

    private AcquisitionPositionSetpoint lastInput;

    private boolean isLeft;

    private ControlPanelSetpoint() {
        this.lastInput = AcquisitionPositionSetpoint.Storage;
        OperatorPanel.getInstance().updateIsLeft();

    }

    public static ControlPanelSetpoint getInstance() {

        if (instance == null) {
            instance = new ControlPanelSetpoint();
        }

        return instance;
    }

    public AcquisitionPositionSetpoint getLastInput() {
        return this.lastInput;
    }

    public void setLastInput(AcquisitionPositionSetpoint lastInput) {
        this.lastInput = lastInput;
    }

    public void setIsLeft(boolean isLeft) {
        this.isLeft = isLeft;
    }

    public boolean getIsLeft() {
        return this.isLeft;
    }

}
