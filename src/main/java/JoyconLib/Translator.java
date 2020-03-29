/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JoyconLib;

import java.util.Base64;
import java.util.HashMap;

/**
 * The translator for the joycon. Will translate the raw value recieved by the joycon to JoyconButton and it's state (Pressed/ Not Pressed)
 *
 * @author lakshbhambhani
 */
public class Translator {

    private HashMap<String, Boolean> inputs;
    private HashMap<String, Boolean> oldInputs;
    private byte battery;
    
    private int lastShared = 0;
    private int lastSole = 0;
    
    private int lastJoystick = 0;
    
    private int id;

    /**
     * Used to create a Translator Object based on left or right Joycon
     * @param joyconId The Joycon ID of the specific Joycon (Left or Right Joycon) Use Joycon ID Constant from JoyconConstants.
     */
    public Translator(int joyconId) {
        inputs = new HashMap<>();
        this.id = joyconId;
    }

    /**
     * Translates the raw value to JoystickButtonID and it's state
     * @param data Original data recieved from the Joycon
     * @return HashMap containing the Id of the button/joystick and it's state
     */
    public HashMap<String, Boolean> translate(byte[] data) {
        //Clearing the inputs
        inputs.clear();
            
        //Getting input change
        int shared = data[2];
        int sole = data[1];
        
        if (data[2] < 0) {
            shared = data[2] + 256;
        }
        if (data[1] < 0) {
            sole = data[1] + 256;
        }        
                
        if(shared != 0) {
        	if(id == JoyconConstants.JOYCON_LEFT) {
        		switch(shared) {
        			case JoyconConstants.HIDValues.MINUS:
        		        inputs.put(Integer.toString(JoyconConstants.Buttons.MINUS), true);
        		        System.out.println("Minus Pressed");
        		        break;        			
        			case JoyconConstants.HIDValues.CAPTURE:
        		        inputs.put(Integer.toString(JoyconConstants.Buttons.CAPTURE), true);
        		        System.out.println("Capture Pressed");
        		        break;
        		    case JoyconConstants.HIDValues.L:
        		        inputs.put(Integer.toString(JoyconConstants.Buttons.L), true);
        		        System.out.println("L Pressed");
        		        break;
        		    case JoyconConstants.HIDValues.ZL:
        		        inputs.put(Integer.toString(JoyconConstants.Buttons.ZL), true);
        		        System.out.println("ZL Pressed");
        		        break;
        		    case JoyconConstants.HIDValues.LEFTJOYSTICKBUTTON:
        		        inputs.put(Integer.toString(JoyconConstants.Buttons.LEFTJOYSTICKBUTTON), true);
        		        System.out.println("LeftJoystickButton Pressed");
        		        break;
        		}
        	}
        	else if(id == JoyconConstants.JOYCON_RIGHT) {
        		switch(shared) {
    			case JoyconConstants.HIDValues.PLUS:
    		        inputs.put(Integer.toString(JoyconConstants.Buttons.PLUS), true);
    		        System.out.println("Plus Pressed");
    		        break;
    		    case JoyconConstants.HIDValues.HOME:
    		        inputs.put(Integer.toString(JoyconConstants.Buttons.HOME), true);
    		        System.out.println("Home Pressed");
    		        break;
    		    case JoyconConstants.HIDValues.R:
    		        inputs.put(Integer.toString(JoyconConstants.Buttons.R), true);
    		        System.out.println("R Pressed");
    		        break;
    		    case JoyconConstants.HIDValues.ZR:
    		        inputs.put(Integer.toString(JoyconConstants.Buttons.ZR), true);
    		        System.out.println("ZR Pressed");
    		        break;
    			case JoyconConstants.HIDValues.RIGHTJOYSTICKBUTTON:
    		        inputs.put(Integer.toString(JoyconConstants.Buttons.RIGHTJOYSTICKBUTTON), true);
    		        System.out.println("RightJoystickButton Pressed");
    		        break;
        		}
        	}
        }
        else {
        	if(id == JoyconConstants.JOYCON_LEFT) {
        		switch(lastShared) {
        			case JoyconConstants.HIDValues.MINUS:
        		        inputs.put(Integer.toString(JoyconConstants.Buttons.MINUS), false);
        		        System.out.println("Minus Released");
        		        break;
        			case JoyconConstants.HIDValues.CAPTURE:
        		        inputs.put(Integer.toString(JoyconConstants.Buttons.CAPTURE), false);
        		        System.out.println("Capture Released");
        		        break;
        			case JoyconConstants.HIDValues.L:
        		        inputs.put(Integer.toString(JoyconConstants.Buttons.L), false);
        		        System.out.println("L Released");
        		        break;
        			case JoyconConstants.HIDValues.ZL:
        		        inputs.put(Integer.toString(JoyconConstants.Buttons.ZL), false);
        		        System.out.println("ZL Released");
        		        break;
        			case JoyconConstants.HIDValues.LEFTJOYSTICKBUTTON:
        		        inputs.put(Integer.toString(JoyconConstants.Buttons.LEFTJOYSTICKBUTTON), false);
        		        System.out.println("LeftJoystickButton Released");
        		        break;
        		}
        	}
        	else if(id == JoyconConstants.JOYCON_RIGHT) {
        		switch(lastShared) {
    			case JoyconConstants.HIDValues.PLUS:
    		        inputs.put(Integer.toString(JoyconConstants.Buttons.PLUS), false);
    		        System.out.println("Plus Released");
    		        break;
    			case JoyconConstants.HIDValues.HOME:
    		        inputs.put(Integer.toString(JoyconConstants.Buttons.HOME), false);
    		        System.out.println("Home Released");
    		        break;
    			case JoyconConstants.HIDValues.R:
    		        inputs.put(Integer.toString(JoyconConstants.Buttons.R), false);
    		        System.out.println("R Released");
    		        break;
    			case JoyconConstants.HIDValues.ZR:
    		        inputs.put(Integer.toString(JoyconConstants.Buttons.ZR), false);
    		        System.out.println("ZR Released");
    		        break;
    			case JoyconConstants.HIDValues.RIGHTJOYSTICKBUTTON:
    		        inputs.put(Integer.toString(JoyconConstants.Buttons.RIGHTJOYSTICKBUTTON), false);
    		        System.out.println("RightJoystickButton Released");
    		        break;
        		}
        	}
        }
        
        if(sole != 0) {
        	if(id == JoyconConstants.JOYCON_LEFT) {
        		switch(sole) {
        			case JoyconConstants.HIDValues.UP:
        		        inputs.put(Integer.toString(JoyconConstants.Buttons.UP), true);
        		        System.out.println("Up Pressed");
        		        break;        			
        			case JoyconConstants.HIDValues.RIGHT:
        		        inputs.put(Integer.toString(JoyconConstants.Buttons.RIGHT), true);
        		        System.out.println("Right Pressed");
        		        break;
        		    case JoyconConstants.HIDValues.DOWN:
        		        inputs.put(Integer.toString(JoyconConstants.Buttons.DOWN), true);
        		        System.out.println("Down Pressed");
        		        break;
        		    case JoyconConstants.HIDValues.LEFT:
        		        inputs.put(Integer.toString(JoyconConstants.Buttons.LEFT), true);
        		        System.out.println("Left Pressed");
        		        break;
        		    case JoyconConstants.HIDValues.SL:
        		        inputs.put(Integer.toString(JoyconConstants.Buttons.SL), true);
        		        System.out.println("Left SL Pressed");
        		        break;
        		    case JoyconConstants.HIDValues.SR:
        		        inputs.put(Integer.toString(JoyconConstants.Buttons.SR), true);
        		        System.out.println("Left SR Pressed");
        		        break;
        		}
        	}
        	else if(id == JoyconConstants.JOYCON_RIGHT) {
        		switch(sole) {
        			case JoyconConstants.HIDValues.X:
        				inputs.put(Integer.toString(JoyconConstants.Buttons.X), true);
        		        System.out.println("X Pressed");
        				break;        			
        			case JoyconConstants.HIDValues.A:
        				inputs.put(Integer.toString(JoyconConstants.Buttons.A), true);
        		        System.out.println("A Pressed");
        				break;
        			case JoyconConstants.HIDValues.B:
        				inputs.put(Integer.toString(JoyconConstants.Buttons.B), true);
        		        System.out.println("B Pressed");
        				break;
        			case JoyconConstants.HIDValues.Y:
        				inputs.put(Integer.toString(JoyconConstants.Buttons.Y), true);
        		        System.out.println("Y Pressed");
        				break;
        			case JoyconConstants.HIDValues.SL:
        				inputs.put(Integer.toString(JoyconConstants.Buttons.SL), true);
        		        System.out.println("Right SL Pressed");
        				break;
        			case JoyconConstants.HIDValues.SR:
        				inputs.put(Integer.toString(JoyconConstants.Buttons.SR), true);
        		        System.out.println("Right SR Pressed");
        				break;
        		}
        	}
        }
        else {
        	if(id == JoyconConstants.JOYCON_LEFT) {
        		switch(lastSole) {
        		case JoyconConstants.HIDValues.UP:
    		        inputs.put(Integer.toString(JoyconConstants.Buttons.UP), false);
    		        System.out.println("Up Released");
    		        break;        			
    			case JoyconConstants.HIDValues.RIGHT:
    		        inputs.put(Integer.toString(JoyconConstants.Buttons.RIGHT), false);
    		        System.out.println("Right Released");
    		        break;
    		    case JoyconConstants.HIDValues.DOWN:
    		        inputs.put(Integer.toString(JoyconConstants.Buttons.DOWN), false);
    		        System.out.println("Down Released");
    		        break;
    		    case JoyconConstants.HIDValues.LEFT:
    		        inputs.put(Integer.toString(JoyconConstants.Buttons.LEFT), false);
    		        System.out.println("Left Released");
    		        break;
    		    case JoyconConstants.HIDValues.SL:
    		        inputs.put(Integer.toString(JoyconConstants.Buttons.SL), false);
    		        System.out.println("Left SL Released");
    		        break;
    		    case JoyconConstants.HIDValues.SR:
    		        inputs.put(Integer.toString(JoyconConstants.Buttons.SR), false);
    		        System.out.println("Left SR Released");
    		        break;
        		}
        	}
        	else if(id == JoyconConstants.JOYCON_RIGHT) {
        		switch(lastSole) {
        		case JoyconConstants.HIDValues.X:
    				inputs.put(Integer.toString(JoyconConstants.Buttons.X), false);
    		        System.out.println("X Released");
    				break;        			
    			case JoyconConstants.HIDValues.A:
    				inputs.put(Integer.toString(JoyconConstants.Buttons.A), false);
    		        System.out.println("A Released");
    				break;
    			case JoyconConstants.HIDValues.B:
    				inputs.put(Integer.toString(JoyconConstants.Buttons.B), false);
    		        System.out.println("B Released");
    				break;
    			case JoyconConstants.HIDValues.Y:
    				inputs.put(Integer.toString(JoyconConstants.Buttons.Y), false);
    		        System.out.println("Y Released");
    				break;
    			case JoyconConstants.HIDValues.SL:
    				inputs.put(Integer.toString(JoyconConstants.Buttons.SL), false);
    		        System.out.println("Right SL Released");
    				break;
    			case JoyconConstants.HIDValues.SR:
    				inputs.put(Integer.toString(JoyconConstants.Buttons.SR), false);
    		        System.out.println("Right SR Released");
    				break;
        		}
        	}
        }
        
        lastShared = shared;
        lastSole = sole;
        
        //Getting Joystick change
        int joystick = data[3];
        
        if(joystick == 8 && lastJoystick != 8) {
        	if(id == JoyconConstants.JOYCON_LEFT) {
        		inputs.put(Integer.toString(JoyconConstants.JoystickStates.NEUTRAL), true);
    	        System.out.println("Left Joystick at NEUTRAL");
        	}
        	else if(id == JoyconConstants.JOYCON_RIGHT) {
        		inputs.put(Integer.toString(JoyconConstants.JoystickStates.NEUTRAL), true);
    	        System.out.println("Right Joystick at NEUTRAL");
        	}
        	
        }
        else {
        	if(id == JoyconConstants.JOYCON_LEFT) {
        		switch(joystick) {
        		case JoyconConstants.LeftJoystickHIDValues.DEGREES_0:
    				inputs.put(Integer.toString(JoyconConstants.JoystickStates.DEGREES_0), true);
    		        System.out.println("Left Joystick at 0");
    				break;        			
        		case JoyconConstants.LeftJoystickHIDValues.DEGREES_45:
    				inputs.put(Integer.toString(JoyconConstants.JoystickStates.DEGREES_45), true);
    		        System.out.println("Left Joystick at 45");
    				break;  
        		case JoyconConstants.LeftJoystickHIDValues.DEGREES_90:
    				inputs.put(Integer.toString(JoyconConstants.JoystickStates.DEGREES_90), true);
    		        System.out.println("Left Joystick at 90");
    				break;  
        		case JoyconConstants.LeftJoystickHIDValues.DEGREES_135:
    				inputs.put(Integer.toString(JoyconConstants.JoystickStates.DEGREES_135), true);
    		        System.out.println("Left Joystick at 135");
    				break;  
        		case JoyconConstants.LeftJoystickHIDValues.DEGREES_180:
    				inputs.put(Integer.toString(JoyconConstants.JoystickStates.DEGREES_180), true);
    		        System.out.println("Left Joystick at 180");
    				break;  
        		case JoyconConstants.LeftJoystickHIDValues.DEGREES_225:
    				inputs.put(Integer.toString(JoyconConstants.JoystickStates.DEGREES_225), true);
    		        System.out.println("Left Joystick at 225");
    				break;  
        		case JoyconConstants.LeftJoystickHIDValues.DEGREES_270:
    				inputs.put(Integer.toString(JoyconConstants.JoystickStates.DEGREES_270), true);
    		        System.out.println("Left Joystick at 270");
    				break;  
        		case JoyconConstants.LeftJoystickHIDValues.DEGREES_315:
    				inputs.put(Integer.toString(JoyconConstants.JoystickStates.DEGREES_315), true);
    		        System.out.println("Left Joystick at 315");
    				break;  
        		}
        	}
        	else if(id == JoyconConstants.JOYCON_RIGHT) {
        		switch(joystick) {
        		case JoyconConstants.RightJoystickHIDValues.DEGREES_0:
    				inputs.put(Integer.toString(JoyconConstants.JoystickStates.DEGREES_0), true);
    		        System.out.println("Right Joystick at 0");
    				break;        			
        		case JoyconConstants.RightJoystickHIDValues.DEGREES_45:
    				inputs.put(Integer.toString(JoyconConstants.JoystickStates.DEGREES_45), true);
    		        System.out.println("Right Joystick at 45");
    				break;  
        		case JoyconConstants.RightJoystickHIDValues.DEGREES_90:
    				inputs.put(Integer.toString(JoyconConstants.JoystickStates.DEGREES_90), true);
    		        System.out.println("Right Joystick at 90");
    				break;  
        		case JoyconConstants.RightJoystickHIDValues.DEGREES_135:
    				inputs.put(Integer.toString(JoyconConstants.JoystickStates.DEGREES_135), true);
    		        System.out.println("Right Joystick at 135");
    				break;  
        		case JoyconConstants.RightJoystickHIDValues.DEGREES_180:
    				inputs.put(Integer.toString(JoyconConstants.JoystickStates.DEGREES_180), true);
    		        System.out.println("Right Joystick at 180");
    				break;  
        		case JoyconConstants.RightJoystickHIDValues.DEGREES_225:
    				inputs.put(Integer.toString(JoyconConstants.JoystickStates.DEGREES_225), true);
    		        System.out.println("Right Joystick at 225");
    				break;  
        		case JoyconConstants.RightJoystickHIDValues.DEGREES_270:
    				inputs.put(Integer.toString(JoyconConstants.JoystickStates.DEGREES_270), true);
    		        System.out.println("Right Joystick at 270");
    				break;  
        		case JoyconConstants.RightJoystickHIDValues.DEGREES_315:
    				inputs.put(Integer.toString(JoyconConstants.JoystickStates.DEGREES_315), true);
    		        System.out.println("Right Joystick at 315");
    				break;  
        		}
        	}
        }
        
        lastJoystick = joystick;
        
        return inputs;
    }
    
    /**
     * Used to get the inputs
     * @return The inputs
     */
    public HashMap<String, Boolean> getInputs() {
        return inputs;
    }

}
