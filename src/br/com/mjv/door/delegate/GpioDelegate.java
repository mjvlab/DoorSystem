package br.com.mjv.door.delegate;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinMode;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

public class GpioDelegate {

    private GpioController gpio;
	private GpioPinDigitalOutput greenLed;
	private GpioPinDigitalOutput blueLed;
	private GpioPinDigitalOutput openDoor;
    
    private static GpioDelegate instance;

    private GpioDelegate(){
    	
    	gpio = GpioFactory.getInstance();
    	
    	greenLed = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_06);
    	greenLed.setMode(PinMode.DIGITAL_OUTPUT);
    	
    	blueLed = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_05);
    	blueLed.setMode(PinMode.DIGITAL_OUTPUT);
    	
    	openDoor = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_08);
    	openDoor.setMode(PinMode.DIGITAL_OUTPUT);
    	
    }
    
    public static GpioDelegate getInstance(){
    	
    	if (instance == null){
    		instance = new GpioDelegate();
    	}
    	
    	return instance;
    	
    }
    
    public void changeGreenLedState(PinState pinState){
    	greenLed.setState(pinState);
    }
    
    
    	public void changeBlueLedState(PinState pinState){
    	blueLed.setState(pinState);
    }
    	
    	public void changeDoorState(PinState pinState){
    	openDoor.setState(pinState);	
    }
    	
    	public void startRfidReader(){
        	gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00).low();
        	gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02).high();
    	}
    
}
