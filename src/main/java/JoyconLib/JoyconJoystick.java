package JoyconLib;

/**
 * Represents a joystick on a joycon
 * @author lakshbhambhani
 */
public class JoyconJoystick {
	
	private int currentState;
	
	/**
	 * Used to instantiate a joystick for a joycon. Initial state set to netural
	 */
	public JoyconJoystick() {
		currentState = JoyconConstants.JoystickStates.NEUTRAL;
	}
	
	/**
	 * Used to update the state of the joycon (The new angle).
	 * @param joyconStateId - The new angle of the joystick. Use JoyconConstants.JoystickStates
	 */
	public void updateState(int joyconStateId) {
		this.currentState = joyconStateId;
	}
	
	/**
	 * Used to get the current state of the joystick (The current angle)
	 * @return int joystickState - The id of the new Joystick Angle
	 */
	public int getCurrentState() {
		return currentState;
	}

}
