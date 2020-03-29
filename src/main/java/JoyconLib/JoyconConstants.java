/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JoyconLib;

/**
 * <b>The class with all the constant value the library needs to work</b>
 *
 * @version 1.0
 * @author lakshbhambhani
 */
public class JoyconConstants {

    //Other Values
    public static final short VENDOR_ID = 0x057E;
    public static final String MANUFACTURER = "Nintendo";
    public static final short JOYCON_LEFT = 0x2006;
    public static final short JOYCON_RIGHT = 0x2007;

	/**
	 * HID Values recieved from the joycons. Shared recieved on byte 3. Sole messages recieved on byte 2.
	 */
    public static class HIDValues {
		
		// Shared
    	public static final int MINUS = 1;
    	public static final int PLUS = 2;
    	public static final int HOME = 16;
    	public static final int CAPTURE = 32;
    	public static final int L = 64;
    	public static final int ZL = 128;
    	public static final int R = 64;
    	public static final int ZR = 128;
    	public static final int LEFTJOYSTICKBUTTON = 4;
    	public static final int RIGHTJOYSTICKBUTTON = 8;
		
		// Left
    	public static final int UP = 4;
    	public static final int RIGHT = 8;
    	public static final int DOWN = 2;
    	public static final int LEFT = 1;
    	
    	public static final int SL = 16;
    	public static final int SR = 32;
		
		// Right
    	public static final int X = 2;
    	public static final int A = 1;
    	public static final int B = 4;
    	public static final int Y = 8;
    	
    }
	
	/**
	 * Values added to inputs to process inputs
	 */
    public static class Buttons {
		
		// Shared
    	public static final int MINUS = 1;
    	public static final int PLUS = 2;
    	public static final int HOME = 3;
    	public static final int CAPTURE = 4;
    	public static final int L = 5;
    	public static final int ZL = 6;
    	public static final int R = 7;
    	public static final int ZR = 8;
    	public static final int LEFTJOYSTICKBUTTON = 9;
    	public static final int RIGHTJOYSTICKBUTTON = 10;
		
		// Left
    	public static final int UP = 11;
    	public static final int RIGHT = 12;
    	public static final int DOWN = 13;
    	public static final int LEFT = 14;
    	
    	public static final int SL = 15;
    	public static final int SR = 16;
		
		// Right
    	public static final int X = 17;
    	public static final int A = 18;
    	public static final int B = 19;
    	public static final int Y = 20;
    	
    }
    
    /**
     * HID Values recieved from the left joycon joystick. Detected on Byte 3
     *
     */
    public class LeftJoystickHIDValues{
    	
    	public static final int DEGREES_0 = 0;
    	public static final int DEGREES_45 = 7;
    	public static final int DEGREES_90 = 6; 
    	public static final int DEGREES_135 = 5;
    	public static final int DEGREES_180 = 4; 
    	public static final int DEGREES_225 = 3;
    	public static final int DEGREES_270 = 2;
    	public static final int DEGREES_315 = 1;
    	public static final int NEUTRAL = 8;

    }
    
    /**
     * Values added to inputs to process them
     *
     */
    public class JoystickStates{
    	
    	public static final int DEGREES_0 = 22;
    	public static final int DEGREES_45 = 23;
    	public static final int DEGREES_90 = 24; 
    	public static final int DEGREES_135 = 25;
    	public static final int DEGREES_180 = 26; 
    	public static final int DEGREES_225 = 27;
    	public static final int DEGREES_270 = 28;
    	public static final int DEGREES_315 = 29;
    	public static final int NEUTRAL = 21;

    }
    
    /**
     * HID Values recieved from the right joycon joystick. Detected on Byte 3
     *
     */
    public class RightJoystickHIDValues{
    	public static final int DEGREES_180 = 0;
    	public static final int DEGREES_135 = 1;
    	public static final int DEGREES_90 = 2;
    	public static final int DEGREES_45 = 3;
    	public static final int DEGREES_0 = 4;
    	public static final int DEGREES_315 = 5;
    	public static final int DEGREES_270 = 6;
    	public static final int DEGREES_225 = 7;
    	public static final int NEUTRAL = 8;
    	
    }
}
