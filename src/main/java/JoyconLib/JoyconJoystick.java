package JoyconLib;

public class JoyconJoystick {
	
	public int currentState;
	
	public JoyconJoystick() {
		currentState = JoyconConstants.JoystickStates.NEUTRAL;
	}
	
	public void updateState(int joyconStateId) {
		this.currentState = joyconStateId;
	}
	
	public int getCurrentState() {
		return currentState;
	}

}
