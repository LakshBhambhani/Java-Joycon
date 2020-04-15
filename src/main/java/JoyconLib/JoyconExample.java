package JoyconLib;

import java.util.Map;


import JoyconLib.Joycon;
import JoyconLib.JoyconConstants;
import JoyconLib.JoyconEvent;
import JoyconLib.JoyconListener;
import JoyconLib.LeftJoycon;
import JoyconLib.RightJoycon;

public class JoyconExample {

    public static void main(String[] args){
        run();
    }
	
    public static void run() {
        //Create a new Joycon with the identifier of the left joycon
        LJoycon lJoycon = new LJoycon();
        RJoycon rJoycon = new RJoycon();
        //Set the listener for the Joycon and create a new Listener on the go
        System.out.println("Listening to inputs now");
        lJoycon.setListener(new JoyconListener() {
            //Override the method to do what you want with the inputs
            @Override
            public void handleNewInput(JoyconEvent je) {
                //Navigate in the inputs map
                for (Map.Entry<String, Boolean> entry : je.getNewInputs().entrySet()) {
                    //Print to the console the name of the button and his state
//                    System.out.print("Button: " + entry.getKey() + " is " + (entry.getValue() ? "ON \t" : "OFF\t"));
                    //If the button is the capture button it will stop the progam
                    if (entry.getKey().equals(JoyconConstants.Buttons.CAPTURE)) {
                        System.exit(0);
                        //If the button is the minus button it will close the connection with the joycon
                    } else if (entry.getKey().equals(JoyconConstants.Buttons.MINUS)) {
                    	lJoycon.close();
                    }
                }
                //Print to the console the position of the joystick
//                System.out.println("Joystick\tX: " + je.getHorizontal() + "\tY: " + je.getVertical());

//                System.out.println("Battery: " + je.getBattery());
            }
        });
        
        rJoycon.setListener(new JoyconListener() {
            //Override the method to do what you want with the inputs
            @Override
            public void handleNewInput(JoyconEvent je) {
                //Navigate in the inputs map
                for (Map.Entry<String, Boolean> entry : je.getNewInputs().entrySet()) {
                    //Print to the console the name of the button and his state
//                    System.out.print("Button: " + entry.getKey() + " is " + (entry.getValue() ? "ON \t" : "OFF\t"));
                    //If the button is the capture button it will stop the progam
                    if (entry.getKey().equals(JoyconConstants.Buttons.HOME)) {
                        System.exit(0);
                        //If the button is the minus button it will close the connection with the joycon
                    } else if (entry.getKey().equals(JoyconConstants.Buttons.PLUS)) {
                    	rJoycon.close();
                    }
                }
                //Print to the console the position of the joystick
//                System.out.println("Joystick\tX: " + je.getHorizontal() + "\tY: " + je.getVertical());

//                System.out.println("Battery: " + je.getBattery());
            }
        });
    }

}
