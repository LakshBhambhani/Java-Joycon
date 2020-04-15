/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JoyconLib;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import purejavahidapi.DeviceRemovalListener;
import purejavahidapi.HidDevice;
import purejavahidapi.HidDeviceInfo;
import purejavahidapi.InputReportListener;
import purejavahidapi.PureJavaHidApi;

/**
 * Abstract Joycon Class. Contains methods to initialize a joystick and do
 * certain things when it disconnects or when an input is reported
 *
 * @author lakshbhambhani
 * @version 1.0
 */
public abstract class Joycon {

    protected JoyconListener j_Listener;

    private HidDeviceInfo joyconInfo;

    protected HidDevice joycon;

    private Translator leftTranslator;
    private Translator rightTranslator;

    private int[] factory_stick_cal = new int[18];
    private int[] stick_cal_x_l = new int[3];
    private int[] stick_cal_y_l = new int[3];
    private int[] stick_cal_x_r = new int[3];
    private int[] stick_cal_y_r = new int[3];

    private int[] gyr = new int[10];
    private int[] acc = new int[10];

    private Timer timer;

    private int count = 0; // FOR DEBUGGING

    public int packetNum = 1;
    private int joyconId;

    public boolean isConnected = false;
    private boolean isVibrationEnabled = false;
    private boolean isIMUEnabled = false;
    private boolean lowBatteryWarningGiven = false;
    protected boolean j_Open;

    private double batteryPercentage = 100;

    private TimerTask getLatestBatteryLevels = new TimerTask() {
        @Override
        public void run() {
            reportBattery();
        }
    };

    /**
     * <b>Constructor of the Joycon.</b>
     *
     * <p>
     * To specify a correct identifier just use <b>JoyconConstant.JOYCON_LEFT</b> to
     * use the left Joycon or <b>JoyconConstant.JOYCON_RIGHT</b>
     * </p>
     *
     *
     * @param joyconId Identifier of the joycon you'll use
     */
    public Joycon(short joyconId) {
        if ((joyconId == JoyconConstants.JOYCON_LEFT) || (joyconId == JoyconConstants.JOYCON_RIGHT)) {
            this.joyconId = joyconId;
            initialize(joyconId);
        } else {
            throw new IllegalArgumentException(
                    "Wrong joycon id!\nPlease use 'JoyconConstant.JOYCON_RIGHT' or 'JoyconConstant.JOYCON_LEFT'");
        }
        timer = new Timer();
        // timer.schedule(getLatestBatteryLevels, 0, 5000);
    }

    /**
     * Used to reconnect the joycon
     * 
     * @param joyconId
     */
    public void reconnect(short joyconId) {
        if ((joyconId == JoyconConstants.JOYCON_LEFT) || (joyconId == JoyconConstants.JOYCON_RIGHT)) {
            initialize(joyconId);
        } else {
            System.out.println(
                    "Wrong joycon id!\nPlease use 'JoyconConstant.JOYCON_RIGHT' or 'JoyconConstant.JOYCON_LEFT'");
        }
    }

    /**
     * <b>Set the listener of the Joycon to handle his input</b>
     *
     * @param li The listener, specify null to remove it
     */
    public void setListener(JoyconListener li) {
        j_Listener = li;
    }

    /**
     * <b>Close the connection with the Joycon</b>
     *
     * @return True or false if closed correctly
     */
    public boolean close() {
        boolean isClosed = false;
        try {
            joycon.close();
            isClosed = true;
        } catch (IllegalStateException e) {
            System.out.println("Error while closing conection to the joycon!");
            isClosed = false;
        }
        return isClosed;
    }

    /**
     * @param joyconId the id of the joycon. Ex: JoyconConstants.JOYCON_LEFT for left joycon.
     */
    private void initialize(short joyconId) {
        joyconInfo = null;
        joycon = null;
        leftTranslator = new Translator(JoyconConstants.JOYCON_LEFT);
        rightTranslator = new Translator(JoyconConstants.JOYCON_RIGHT);
        List<HidDeviceInfo> list = PureJavaHidApi.enumerateDevices();
        for(HidDeviceInfo x : list){
            System.out.println("xInfo: " + x.getProductId());
        }
        for (HidDeviceInfo info : list) {
            try {
                if ((info.getVendorId() == JoyconConstants.VENDOR_ID) && (info.getProductId() == joyconId)) {   //(info.getManufacturerString().equals(JoyconConstants.MANUFACTURER)
                    //|| info.getManufacturerString().equals("Unknown") || info.getManufacturerString().equals("null"))
                   // && 
                    System.out.println("Found a Nintendo gear!\nConecting...");
                    System.out.println("INFO: " + info);
                    joyconInfo = info;
                }

            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        if (joyconInfo != null) {
            try {
                System.out.println("info is not null");
                joycon = PureJavaHidApi.openDevice(joyconInfo);
                System.out.print("Connected to Joy-Con ");
                if (joyconInfo.getProductId() == JoyconConstants.JOYCON_LEFT) {
                    System.out.println("Left!");
                } else if (joyconInfo.getProductId() == JoyconConstants.JOYCON_RIGHT) {
                    System.out.println("Right!");
                }
                isConnected = true;

                Thread.sleep(100);

                // Set the joycon player light to blinking
                boolean x= setPlayerLight(true, false, false, false, false);
                System.out.println("BOOL: " + x);
                // toggleIMU(true);
                setInputReportMode();
                // toggleIMU(true);
                // toggleVibration(true);
                // rumble();

                Thread.sleep(16);

                joycon.setInputReportListener(new InputReportListener() {

                    private float horizontal = 0f;
                    private float vertical = 0f;
                    private byte battery = 0;

                    @Override
                    public void onInputReport(HidDevice source, byte id, byte[] data, int len) {
                        // Input code case
                        // for(int i = 0; i < 40; i++){
                        //     System.out.println(i + ": " + data[i]);
                        // }
                        // System.out.println(id);
                        HashMap<String, Boolean> newInputs = new HashMap<>();
                        if (id == 33) {
                            processBatteryInformation(data);
                            return;
                        }
                        if (id == 48) {
                            extractIMUValues(data);
                            return;
                        }
                        if (joyconInfo.getProductId() == JoyconConstants.JOYCON_LEFT) {
                            HashMap<String, Boolean> input = leftTranslator.translate(data);
                            newInputs = leftTranslator.getInputs();
                        } else if (joyconInfo.getProductId() == JoyconConstants.JOYCON_RIGHT) {
                            HashMap<String, Boolean> input = rightTranslator.translate(data);
                            newInputs = rightTranslator.getInputs();
                        }
                        if (j_Listener != null) {
                            if (!newInputs.isEmpty()) {
                                j_Listener.handleNewInput(new JoyconEvent(newInputs));
                            }
                        }
                        updateValues(newInputs);
                        onInputReportDoThis(newInputs);
                    }
                });
                joycon.setDeviceRemovalListener(new DeviceRemovalListener() {
                    @Override
                    public void onDeviceRemoval(HidDevice source) {
                        System.out.println("Joy-Con disconnected!");
                        isConnected = false;
                    }
                });

                // // Get joystick calibration info
                // datat = new byte[16];
                // datat[9] = 0x10;
                // datat[10] = 0x3D;
                // datat[11] = 0x60;
                // datat[14] = 0x12;
                // joycon.setOutputReport(ids, datat, 16);

                Thread.sleep(100);

                if (joyconInfo.getProductId() == JoyconConstants.JOYCON_LEFT) {
                    stick_cal_x_l[1] = (factory_stick_cal[4] << 8) & 0xF00 | factory_stick_cal[3];
                    stick_cal_y_l[1] = (factory_stick_cal[5] << 4) | (factory_stick_cal[4] >> 4);
                    stick_cal_x_l[0] = stick_cal_x_l[1] - ((factory_stick_cal[7] << 8) & 0xF00 | factory_stick_cal[6]);
                    stick_cal_y_l[0] = stick_cal_y_l[1] - ((factory_stick_cal[8] << 4) | (factory_stick_cal[7] >> 4));
                    stick_cal_x_l[2] = stick_cal_x_l[1] + ((factory_stick_cal[1] << 8) & 0xF00 | factory_stick_cal[0]);
                    stick_cal_y_l[2] = stick_cal_y_l[1] + ((factory_stick_cal[2] << 4) | (factory_stick_cal[2] >> 4));
                } else if (joyconInfo.getProductId() == JoyconConstants.JOYCON_RIGHT) {
                    stick_cal_x_r[1] = (factory_stick_cal[10] << 8) & 0xF00 | factory_stick_cal[9];
                    stick_cal_y_r[1] = (factory_stick_cal[11] << 4) | (factory_stick_cal[10] >> 4);
                    stick_cal_x_r[0] = stick_cal_x_r[1]
                            - ((factory_stick_cal[13] << 8) & 0xF00 | factory_stick_cal[12]);
                    stick_cal_y_r[0] = stick_cal_y_r[1] - ((factory_stick_cal[14] << 4) | (factory_stick_cal[13] >> 4));
                    stick_cal_x_r[2] = stick_cal_x_r[1]
                            + ((factory_stick_cal[16] << 8) & 0xF00 | factory_stick_cal[15]);
                    stick_cal_y_r[2] = stick_cal_y_r[1] + ((factory_stick_cal[17] << 4) | (factory_stick_cal[16] >> 4));
                }

                // Set to normal input mode
                // datat = new byte[16];
                // datat[9] = 0x03;
                // datat[10] = 0x30;
                // joycon.setOutputReport(ids, datat, 16);

                Thread.sleep(16);

            } catch (IOException ex) {
                System.out.println(
                        "Error while opening connection to the Joy-Con!\nPlease try to close all software that could communicate with it and retry.");
                isConnected = false;
            } catch (InterruptedException ex) {
                System.out.println("Error, the Joy-Con is not connected anymore!");
                isConnected = false;
            }
        } else {
            System.out.println("No Joy-Con was found :(\nTry to conect a Joy-Con and launch the software again.");
            isConnected = false;
        }
    }

    /**
     * Tests if the joycon is still connected and updated isConnected based on that
     */
    public void testConnection(short joyconId) {
        List<HidDeviceInfo> list = PureJavaHidApi.enumerateDevices();
        for (HidDeviceInfo info : list) {
            try {
                if ((info.getManufacturerString().equals(JoyconConstants.MANUFACTURER)
                        || info.getManufacturerString().equals("Unknown"))
                        && (info.getVendorId() == JoyconConstants.VENDOR_ID) && (info.getProductId() == joyconId)) {
                    isConnected = true;
                } else {
                    isConnected = false;
                }

            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Abstract Method used to update the values of the buttons or joystick when an
     * input is reported
     * 
     * @param map The map containing the id of the button and isPressed
     */
    public abstract void updateValues(HashMap<String, Boolean> map);

    /**
     * This method can be used to do something user-specific when an input is
     * reported. Such as user wanting to log what inputs are reported
     * 
     * @param map The map containing the id of the button and isPressed
     */
    public abstract void onInputReportDoThis(HashMap<String, Boolean> map);

    /**
     * Used to check if a certain button is pressed
     * 
     * @param button The button that has to be checked if it is pressed
     * @return True if it is pressed
     */
    public boolean isButtonPressed(JoyconButton button) {
        return button.isPressed();
    }

    /**
     * Used to set the state of the player lights
     * 
     * @param first      True if the first light is on. The first light is the first
     *                   light from the top of the joycon
     * @param second     True if the second light is on.
     * @param third      True if the third light is on.
     * @param fourth     True if the fourth linke is on.
     * @param isBlinking True if you want the light to blink. False if light is
     *                   supposed to be stable
     * @return True if the packet to be sent was scheduled successfully
     */
    public boolean setPlayerLight(boolean first, boolean second, boolean third, boolean fourth, boolean isBlinking) {
        int firstValue = first ? 1 : 0;
        int secondValue = second ? 1 : 0;
        int thirdValue = third ? 1 : 0;
        int fourthValue = fourth ? 1 : 0;
        packetNum = (packetNum == 15) ? 1 : (packetNum + 1);
        byte ids = 0x01; // confirmed
        byte[] datat = new byte[40];
        datat[0] = 1;
        datat[1] = (byte) packetNum;
        datat[10] = 0x30;
        int i = fourthValue << 3 | thirdValue << 2 | secondValue << 1 | firstValue << 0;
        if (isBlinking) {
            i = i << 4;
        }
        datat[11] = (byte) i;
        int numPacketsSent = joycon.setOutputReport(ids, datat, datat.length);
        return (numPacketsSent == -1) ? false : true;
    }

    /**
     * Used to rumble
     * 
     * @return True if the packets to be sent were scheduled
     */
    public boolean rumble() {
        if (!isVibrationEnabled) {
            System.out.println("Vibration has to be enabled. Use toggleVibration() to enable.");
            return false;
        }
        packetNum = (packetNum == 15) ? 1 : (packetNum + 1);
        byte ids = 0x10; // confirmed
        byte[] datat = new byte[40];
        datat[0] = 1;
        datat[1] = (byte) packetNum;

        int hf = (byte) 200; // Set H.Frequency
        int hf_amp = (byte) 18000; // Set H.Frequency amplitude 0x98

        // Byte swapping
        datat[2] = (byte) ((byte) hf & 0xFF);
        datat[3] = (byte) (hf_amp + ((hf >> 8) & 0xFF)); // Add amp + 1st byte of frequency to amplitude byte

        byte lf = (byte) 180; // Set L.Frequency 0x15E
        int lf_amp = (byte) 20000; // Set L.Frequency amplitude 0x304d
        // Byte swapping
        datat[4] = (byte) (lf + ((lf_amp >> 8) & 0xFF)); // Add freq + 1st byte of LF amplitude to the frequency byte
        datat[5] = (byte) (lf_amp & 0xFF);

        for (int i = 2; i < 6; i++) {
            datat[4 + i] = datat[i];
        }

        int numPacketsSent = joycon.setOutputReport(ids, datat, datat.length);
        return (numPacketsSent != -1);
    }

    private boolean reportBattery() {
        packetNum = (packetNum == 15) ? 1 : (packetNum + 1);
        byte ids = 0x01; // confirmed
        byte[] datat = new byte[40];
        datat[0] = 1;
        datat[1] = (byte) packetNum;
        datat[10] = 0x50;
        int numPacketsSent = joycon.setOutputReport(ids, datat, datat.length);
        return (numPacketsSent != -1);
    }

    private void processBatteryInformation(byte[] data) {
        int currentBatteryLevel = Math.abs(data[2] >> 4);
        this.batteryPercentage = (currentBatteryLevel / 8.0 * 100);
        if (getBatteryPercentage() < 50) {
            if (!lowBatteryWarningGiven) {
                toggleVibration(false);
                System.out.println("Charge now! Battery is less than 50%. Disabled vibrations.");
                lowBatteryWarningGiven = true;
            }
        }
    }

    /**
     * Used to get the battery percentage of the joycon
     * 
     * @return batteyPercent battery percentage levels
     */
    public double getBatteryPercentage() {
        return (Math.round(batteryPercentage * 10) / 10.0);
    }

    /**
     * Used to enable or disable vibrations (HD Rumble).
     * 
     * @param isOn true if vibrations have to be enabled
     * @return true if the packet was scheduled to be sent
     */
    public boolean toggleVibration(boolean isOn) {
        if (getBatteryPercentage() < 50) {
            System.out.println("Battery is less than 50%. Could not enable vibration.");
            return false;
        }
        packetNum = (packetNum == 15) ? 1 : (packetNum + 1);
        byte ids = 0x01; // confirmed
        byte[] datat = new byte[40];
        datat[0] = 1;
        datat[1] = (byte) packetNum;
        datat[10] = 0x48;
        datat[11] = isOn ? (byte) 0x01 : 0x00;
        int numPacketsSent = joycon.setOutputReport(ids, datat, datat.length);
        isVibrationEnabled = isOn;
        return (numPacketsSent != -1);
    }

    /**
     * Used to set input report mode
     * 
     * @return true if the packet was scheduled to be sent
     */
    public boolean setInputReportMode() {
        packetNum = (packetNum == 15) ? 1 : (packetNum + 1);
        byte ids = 0x01; // confirmed
        byte[] datat = new byte[40];
        datat[0] = 1;
        datat[1] = (byte) packetNum;
        datat[10] = 0x03;
        datat[11] = 0x3F;
        int numPacketsSent = joycon.setOutputReport(ids, datat, datat.length);
        return (numPacketsSent != -1);
    }

    /**
     * Used to enable or disable IMU (6 axis sensor).
     * 
     * @param isOn true if vibrations have to be enabled
     * @return true if the packet was scheduled to be sent
     */
    public boolean toggleIMU(boolean isOn) {
        if (getBatteryPercentage() < 50) {
            System.out.println("Battery is less than 50%. Could not enable IMU.");
            return false;
        }
        packetNum = (packetNum == 15) ? 1 : (packetNum + 1);
        byte ids = 0x01; // confirmed
        byte[] datat = new byte[40];
        datat[0] = 1;
        datat[1] = (byte) packetNum;
        datat[10] = 0x40;
        datat[11] = isOn ? (byte) 0x01 : 0x00;
        int numPacketsSent = joycon.setOutputReport(ids, datat, datat.length);
        isIMUEnabled = isOn && (numPacketsSent != -1);
        return (numPacketsSent != -1);
    }

    /**
     * Used to enable or disable IMU (6 axis sensor).
     * 
     * @param data data packet recieved
     */
    public void extractIMUValues(byte[] data) {
        int n = 0;
        gyr[0] = (data[19] | data[20] << 8);//((data[20] << 8) & 0xff00));
        gyr[1] = (data[21] | data[22]<< 8);//((data[22] << 8) & 0xff00));
        gyr[2] = (data[23] | data[24]<< 8);//((data[24] << 8) & 0xff00));
        acc[0] = (data[13] | data[14]<< 8);//((data[14] << 8) & 0xff00));
        acc[1] = (data[15] | data[16]<< 8);//((data[16] << 8) & 0xff00));
        acc[2] = (data[17] | data[18]<< 8);//((data[18] << 8) & 0xff00));
        acc[3] = (data[29] | data[30]<< 8);//((data[18] << 8) & 0xff00));
        // int av = (acc[2])/2;
        count++;
        if(count==60){
            System.out.println(acc[2]);
            // System.out.println("GYR_X: " + gyr[0] + " GYR_Y: " + gyr[1] + " GYR_Z: " + gyr[2] + " ACC_X: " + acc[0] + " ACC_Y: " + acc[1] + " ACC_Z: " + acc[2]);
            count=0;
        }
    }

    /**
     * Used to send output report to the joycon
     * 
     * @param reportID the report number if numbered reports are used else pass 0
     * @param data     a byte array containing the data to be sent
     * @param length   the number of bytes to send from the data array
     * @return number bytes actually sent or -1 if the send failed
     */
    public int setOutputReport(byte reportID, byte[] data, int length) {
        return joycon.setOutputReport(reportID, data, length);
    }

}
