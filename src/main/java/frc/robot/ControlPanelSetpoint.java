package frc.robot;

public class ControlPanelSetpoint {

    private static ControlPanelSetpoint instance;

    private AcquisitionPositionSetpoint lastInput;

    private ControlPanelSetpoint() {

    }

    public static ControlPanelSetpoint getInstance() {

        if (instance == null) {
            instance = new ControlPanelSetpoint();
        }

        return instance;
    }

    public AcquisitionPositionSetpoint getLastInput() {
        return lastInput;
    }

    public void setLastInput(AcquisitionPositionSetpoint lastInput) {
        this.lastInput = lastInput;
    }

}
