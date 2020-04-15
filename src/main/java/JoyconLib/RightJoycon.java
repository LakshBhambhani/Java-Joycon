package JoyconLib;

import java.util.HashMap;

/**
 * Represents the right joycon
 * 
 * @author lakshbhambhani
 */
public abstract class RightJoycon extends Joycon {

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

	public HomeLight homeLight = new HomeLight();

	/**
	 * Used to create a right joycon
	 */
	public RightJoycon() {
		super(JoyconConstants.JOYCON_RIGHT);
		// System.out.print(homeLight.set(0, 175, 100, 5));
		homeLight.toggle(true);
		toggleVibration(true);
		rumble();
	}

	/**
	 * Used to update the values of the buttons and joysticks when an input is
	 * recieved
	 * 
	 * @param map The map that contains the id of the input and whether it is
	 *            pressed
	 */
	@Override
	public void updateValues(HashMap<String, Boolean> map) {
		homeLight.toggle(true);
		map.forEach((id, isPressed) -> {
			int buttonId = Integer.parseInt(id);
			if (buttonId == plus.getId()) {
				plus.setPressed(isPressed);
			} else if (buttonId == r.getId()) {
				r.setPressed(isPressed);
			} else if (buttonId == zR.getId()) {
				zR.setPressed(isPressed);
			} else if (buttonId == joystickButton.getId()) {
				joystickButton.setPressed(isPressed);
			} else if (buttonId == x.getId()) {
				x.setPressed(isPressed);
			} else if (buttonId == a.getId()) {
				a.setPressed(isPressed);
			} else if (buttonId == b.getId()) {
				b.setPressed(isPressed);
			} else if (buttonId == y.getId()) {
				y.setPressed(isPressed);
			} else if (buttonId == home.getId()) {
				home.setPressed(isPressed);
			} else if (buttonId == sL.getId()) {
				sL.setPressed(isPressed);
			} else if (buttonId == sR.getId()) {
				sR.setPressed(isPressed);
			} else if (buttonId == JoyconConstants.JoystickStates.DEGREES_0) {
				joystick.updateState(JoyconConstants.JoystickStates.DEGREES_0);
			} else if (buttonId == JoyconConstants.JoystickStates.DEGREES_45) {
				joystick.updateState(JoyconConstants.JoystickStates.DEGREES_45);
			} else if (buttonId == JoyconConstants.JoystickStates.DEGREES_90) {
				joystick.updateState(JoyconConstants.JoystickStates.DEGREES_90);
			} else if (buttonId == JoyconConstants.JoystickStates.DEGREES_135) {
				joystick.updateState(JoyconConstants.JoystickStates.DEGREES_135);
			} else if (buttonId == JoyconConstants.JoystickStates.DEGREES_180) {
				joystick.updateState(JoyconConstants.JoystickStates.DEGREES_180);
			} else if (buttonId == JoyconConstants.JoystickStates.DEGREES_225) {
				joystick.updateState(JoyconConstants.JoystickStates.DEGREES_225);
			} else if (buttonId == JoyconConstants.JoystickStates.DEGREES_270) {
				joystick.updateState(JoyconConstants.JoystickStates.DEGREES_270);
			} else if (buttonId == JoyconConstants.JoystickStates.DEGREES_315) {
				joystick.updateState(JoyconConstants.JoystickStates.DEGREES_315);
			} else if (buttonId == JoyconConstants.JoystickStates.NEUTRAL) {
				joystick.updateState(JoyconConstants.JoystickStates.NEUTRAL);
			}
		});
	}

	public class HomeLight {

		private int clamp(int value, int min, int max, int minValue, int maxValue) {
			if (value == minValue) {
				return min;
			} else if (value == maxValue) {
				return max;
			} else {
				return value / (maxValue / max);
			}
		}

		/**
		 * Used to turn the Light on the home button on or off
		 * 
		 * @param turnedOn true if the light has to be turned on
		 * @return true if the data was scheduled to be sent to the joycon
		 */
		public boolean toggle(boolean turnedOn) {
			if (turnedOn) {
				return set(0, 10, 100, 0);
			} else {
				return set(0, 0, 100, 0);
			}
		}

		/**
		 * Used to blink the Light on the home button on or off
		 * 
		 * @return true if the data was scheduled to be sent to the joycon
		 */
		public boolean blink() {
			return blink(175);
		}

		/**
		 * Used to blink the Light on the home button on or off
		 * @param duration The duration of the blink. Duration must be greater than equal to 8 and less than equal to 175
		 * @return true if the data was scheduled to be sent to the joycon
		 */
		public boolean blink(int duration) {
			if(duration < 8){
				throw new IllegalArgumentException("HomeLight Precondition was Violated");
			}
			return set(5, duration, 100, 1);
		}
	
		/**
		 * Used to control the Light on the home button
		 * @param numMiniCycles Number of mini cycles per a complete cycle. 0 if light has to be turned on forever. numMiniCycles must be greater than equal to 0 and less than equal to 15
		 * @param miniCycleDuration The duration of each mini cycle. 0 if off, else miniCycleDuration must be greater than equal to 8 and less than equal to 175
		 * @param brightness The brightness percentage of the light during this cycle. numCycles must be greater than equal to 0 and less than equal to 100
		 * @param numCycles Number of complete cycles. numCycles must be greater than equal to 0 and less than equal to 15
		 * @return True if the packet was scheduled to be sent
		 */
		public boolean set(int numMiniCycles, int miniCycleDuration, int brightness, int numCycles) {
			if(numMiniCycles < 0 || numMiniCycles > 15 || miniCycleDuration < 0 || miniCycleDuration > 175 || brightness < 0 || brightness > 100 || numMiniCycles < 0 || numMiniCycles > 15){
				throw new IllegalArgumentException("HomeLight Precondition was Violated");
			}
			packetNum = (packetNum == 15) ? 1 : (packetNum+1);
			byte ids = 0x01; // confirmed
			byte[] datat = new byte[40];
			datat[0] = 1;
			datat[1] = (byte) packetNum;
			datat[10] = 0x38;
	
			int x = numMiniCycles << 4 | ((miniCycleDuration>=15) ? clamp(miniCycleDuration, 0, 15, 15, 175) : miniCycleDuration);
			int y =  clamp(brightness, 0, 15, 0, 100) << 4 | numCycles; 
			datat[11] = (byte) x; 
			datat[12] = (byte) y; 
			int numPacketsSent = setOutputReport(ids, datat, datat.length);
			return (numPacketsSent==-1) ? false : true;
		}

	}
	
	/**
	 * Used to implement user-specific code that runs when an input is recieved
	 * @param map The map that contains the id of the input and whether it is pressed
	 */
    public abstract void onInputReportDoThis(HashMap<String, Boolean> map);
	

}
