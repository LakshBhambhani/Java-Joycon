package JoyconLib;

/**
 * Represents a button on the joycon
 */
public class JoyconButton{
	
	private int id;
	
	private boolean isPressed = false;
	
	/**
	 * Used to create a button based on the id of the button. Use the id from JoyconConstant.Button
	 * @param id
	 */
	public JoyconButton(int id) {
		this.id = id;
	}
	
	/**
	 * Used to update the value of the button (isPressed)
	 * @param isPressed True if button is pressed
	 */
	public void setPressed(boolean isPressed) {
		this.isPressed = isPressed;
	}
	
	/**
	 * Used to check if the button is pressed
	 * @return boolean isPressed - true if the button is pressed
	 */
	public boolean isPressed() {
		return isPressed;
	}
	
	/**
	 * Used to get the id of button
	 * @return int id - the id of the button
	 */
	public int getId() {
		return id;
	}
}