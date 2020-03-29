package JoyconLib;

import java.util.HashMap;

/**
 * Represents the left Joycon. 
 * @author lakshbhambhani
 */
public abstract class LeftJoycon extends Joycon{
	
	public JoyconButton minus = new JoyconButton(JoyconConstants.Buttons.MINUS);
	public JoyconButton l = new JoyconButton(JoyconConstants.Buttons.L);
	public JoyconButton zL = new JoyconButton(JoyconConstants.Buttons.ZL);
	public JoyconButton joystickButton = new JoyconButton(JoyconConstants.Buttons.LEFTJOYSTICKBUTTON);
	public JoyconButton up = new JoyconButton(JoyconConstants.Buttons.UP);
	public JoyconButton down = new JoyconButton(JoyconConstants.Buttons.DOWN);
	public JoyconButton left = new JoyconButton(JoyconConstants.Buttons.LEFT);
	public JoyconButton right = new JoyconButton(JoyconConstants.Buttons.RIGHT);
	public JoyconButton capture = new JoyconButton(JoyconConstants.Buttons.CAPTURE);
	public JoyconButton sL = new JoyconButton(JoyconConstants.Buttons.SL);
	public JoyconButton sR = new JoyconButton(JoyconConstants.Buttons.SR);
	
	public JoyconJoystick joystick = new JoyconJoystick();

	/**
	 * Used to create a left joycon
	 */
	public LeftJoycon() {
		super(JoyconConstants.JOYCON_LEFT);
	}
	
	/**
	 * Used to update the values of the buttons and joysticks when an input is recieved
 	 * @param map The map that contains the id of the input and whether it is pressed
	 */
	@Override
	public void updateValues(HashMap<String, Boolean> map) {
		 map.forEach((id, isPressed) -> {
			 int buttonId = Integer.parseInt(id);
			 	if(buttonId == minus.getId()) {
					minus.setPressed(isPressed);
				}
				else if(buttonId == l.getId()) {
					l.setPressed(isPressed);
				}
				else if(buttonId == zL.getId()) {
					zL.setPressed(isPressed);
				}
				else if(buttonId == joystickButton.getId()) {
					joystickButton.setPressed(isPressed);
				}
				else if(buttonId == up.getId()) {
					up.setPressed(isPressed);
				}
				else if(buttonId == down.getId()) {
					down.setPressed(isPressed);
				}
				else if(buttonId == left.getId()) {
					left.setPressed(isPressed);
				}
				else if(buttonId == right.getId()) {
					right.setPressed(isPressed);
				}
				else if(buttonId == capture.getId()) {
					capture.setPressed(isPressed);
				}
				else if(buttonId == sL.getId()) {
					sL.setPressed(isPressed);
				}
				else if(buttonId == sR.getId()) {
					sR.setPressed(isPressed);
				}
				else if(buttonId == JoyconConstants.JoystickStates.DEGREES_0) {
					joystick.updateState(JoyconConstants.JoystickStates.DEGREES_0);
				}
				else if(buttonId == JoyconConstants.JoystickStates.DEGREES_45) {
					joystick.updateState(JoyconConstants.JoystickStates.DEGREES_45);
				}
				else if(buttonId == JoyconConstants.JoystickStates.DEGREES_90) {
					joystick.updateState(JoyconConstants.JoystickStates.DEGREES_90);
				}
				else if(buttonId == JoyconConstants.JoystickStates.DEGREES_135) {
					joystick.updateState(JoyconConstants.JoystickStates.DEGREES_135);
				}
				else if(buttonId == JoyconConstants.JoystickStates.DEGREES_180) {
					joystick.updateState(JoyconConstants.JoystickStates.DEGREES_180);
				}
				else if(buttonId == JoyconConstants.JoystickStates.DEGREES_225) {
					joystick.updateState(JoyconConstants.JoystickStates.DEGREES_225);
				}
				else if(buttonId == JoyconConstants.JoystickStates.DEGREES_270) {
					joystick.updateState(JoyconConstants.JoystickStates.DEGREES_270);
				}
				else if(buttonId == JoyconConstants.JoystickStates.DEGREES_315) {
					joystick.updateState(JoyconConstants.JoystickStates.DEGREES_315);
				}
				else if(buttonId == JoyconConstants.JoystickStates.NEUTRAL) {
					joystick.updateState(JoyconConstants.JoystickStates.NEUTRAL);
				}
		});
	}
	
	/**
	 * Used to implement user-specific code that runs when an input is recieved
	 * @param map The map that contains the id of the input and whether it is pressed
	 */
    public abstract void onInputReportDoThis(HashMap<String, Boolean> map);	
	

}
