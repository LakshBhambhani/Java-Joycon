package JoyconLib;

public class JoyconButton{
	
	private int id;
	
	private boolean isPressed = false;
	
	public JoyconButton(int id) {
		this.id = id;
	}
	
	public void setPressed(boolean isPressed) {
		this.isPressed = isPressed;
	}
	
	public boolean isPressed() {
		return isPressed;
	}
	
	public int getId() {
		return id;
	}
}