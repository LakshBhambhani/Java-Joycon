package JoyconLib;

import java.util.HashMap;

/**
 * Represents the right joycon
 * @author lakshbhambhani
 */
public abstract class RightJoycon extends Joycon{
	
	public JoyconButton plus = new JoyconButton(JoyconConstants.Buttons.PLUS);
	public JoyconButton r = new JoyconButton(JoyconConstants.Buttons.R);
	public JoyconButton zR = new JoyconButton(JoyconConstants.Buttons.ZR);
	public JoyconButton joystickButton = new JoyconButton(JoyconConstants.Buttons.RIGHTJOYSTICKBUTTON);
	public JoyconButton x = new JoyconButton(JoyconConstants.Buttons.X);
	public JoyconButton a = new JoyconButton(JoyconConstants.Buttons.A);
	public JoyconButton b = new JoyconButton(JoyconConstants.Buttons.B);
	public JoyconButton y = new JoyconButton(JoyconConstants.Buttons.Y);
	public JoyconButton home = new JoyconButton(JoyconConstants.Buttons.HOME);
	public JoyconButton sL = new JoyconButton(JoyconConstants.Buttons.SL);
	public JoyconButton sR = new JoyconButton(JoyconConstants.Buttons.SR);
	
	public JoyconJoystick joystick = new JoyconJoystick();

	/**
	 * Used to create a right joycon
	 */
	public RightJoycon() {
		super(JoyconConstants.JOYCON_RIGHT);
	}
	
	/**
	 * Used to update the values of the buttons and joysticks when an input is recieved
 	 * @param map The map that contains the id of the input and whether it is pressed
	 */
	@Override
	public void updateValues(HashMap<String, Boolean> map) {
		 map.forEach((id, isPressed) -> {
			 int buttonId = Integer.parseInt(id);
			 	if(buttonId == plus.getId()) {
			 		plus.setPressed(isPressed);
				}
				else if(buttonId == r.getId()) {
					r.setPressed(isPressed);
				}
				else if(buttonId == zR.getId()) {
					zR.setPressed(isPressed);
				}
				else if(buttonId == joystickButton.getId()) {
					joystickButton.setPressed(isPressed);
				}
				else if(buttonId == x.getId()) {
					x.setPressed(isPressed);
				}
				else if(buttonId == a.getId()) {
					a.setPressed(isPressed);
				}
				else if(buttonId == b.getId()) {
					b.setPressed(isPressed);
				}
				else if(buttonId == y.getId()) {
					y.setPressed(isPressed);
				}
				else if(buttonId == home.getId()) {
					home.setPressed(isPressed);
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
