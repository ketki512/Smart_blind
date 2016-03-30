package edu.rit.csci759.rspi.exercise;


import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

import edu.rit.csci759.jsonrpc.server.JsonRPCServer;
import edu.rit.csci759.rspi.utils.MCP3008ADCReader;

public class RpiIndicator implements RpiIndicatorInterface{

	final GpioPinDigitalOutput pin1, pin2, pin3;
	
	public RpiIndicator() {
//		gpio = GpioFactory.getInstance();
		pin1 = JsonRPCServer.gpio.provisionDigitalOutputPin(RaspiPin.GPIO_27, "MyLED", PinState.LOW); // Green
		pin2 = JsonRPCServer.gpio.provisionDigitalOutputPin(RaspiPin.GPIO_28, "MyLED", PinState.LOW); // Yellow
		pin3 = JsonRPCServer.gpio.provisionDigitalOutputPin(RaspiPin.GPIO_29, "MyLED", PinState.LOW); // Red
	}
	/*
	 * function to turn off all LEDs
	 */
	
	public void led_all_off() {
		pin1.low();
		pin2.low();
		pin3.low();
	}
	
	/*
	 * function to turn on all LEDs
	 */
	public void led_all_on() {
		pin1.high();
		pin2.high();
		pin3.high();
	}
	
	/*
	 * function to indicate error; normally blnking red LED
	 */
	public void led_error(int blink_count) throws InterruptedException {
		for (int i = 0; i < blink_count; i++) {
			pin3.toggle();
			Thread.sleep(300);
		}
		pin3.low();
	}

	
	/*
	 * Turn on a LED to indicate the value is low 
	 */
	public void led_when_low() {
		// Turn on RED
		pin3.toggle();
	}
	
	/*
	 * Turn on a LED to indicate the value is mid 
	 */
	public void led_when_mid() {
		// Turn on YELLOW
		pin2.toggle();
		
	}
	
	/*
	 * Turn on a LED to indicate the value is high 
	 */
	public void led_when_high() {
		// Turn on GREEN
		pin1.toggle();
	}

	
	
	/*
	 * read light intensity value from the photocell
	 */
	public int read_ambient_light_intensity() {
		/*
		 * Reading ambient light from the photocell sensor using the MCP3008 ADC 
		 */
		int adc_ambient = MCP3008ADCReader.readAdc(MCP3008ADCReader.MCP3008_input_channels.CH1.ch());
		// [0, 1023] ~ [0x0000, 0x03FF] ~ [0&0, 0&1111111111]
		// convert in the range of 1-100
		int ambient = (int)(adc_ambient / 10.24);
		return ambient;
	}
	
	
	/*
	 * read temperature value from the TMP36 sensor
	 */
	public int read_temperature() {
		/*
		 * Reading temperature from the TMP36 sensor using the MCP3008 ADC 
		 */
		int adc_temperature = MCP3008ADCReader.readAdc(MCP3008ADCReader.MCP3008_input_channels.CH0.ch());
		// [0, 1023] ~ [0x0000, 0x03FF] ~ [0&0, 0&1111111111]
		// convert in the range of 1-100
		int temperature = (int)(adc_temperature / 10.24);
		return temperature;
	}
	
	private int getAmbient(GpioController gpio) {
		/*
		 * Reading ambient light from the photocell sensor using the MCP3008 ADC 
		 */
		int adc_ambient = MCP3008ADCReader.readAdc(MCP3008ADCReader.MCP3008_input_channels.CH1.ch());
		// [0, 1023] ~ [0x0000, 0x03FF] ~ [0&0, 0&1111111111]
		// convert in the range of 1-100
		int ambient = (int)(adc_ambient / 10.24);
		return ambient;
	}
	
	private int getTemp(GpioController gpio) {
		/*
		 * Reading temperature from the TMP36 sensor using the MCP3008 ADC 
		 */
		int adc_temperature = MCP3008ADCReader.readAdc(MCP3008ADCReader.MCP3008_input_channels.CH0.ch());
		// [0, 1023] ~ [0x0000, 0x03FF] ~ [0&0, 0&1111111111]
		// convert in the range of 1-100
		int temperature = (int)(adc_temperature / 10.24);
		return temperature;
	}
	
//	public static void main(String[] args)  throws InterruptedException {
//		RpiIndicator indicator = new RpiIndicator();
//		MCP3008ADCReader.initSPI(indicator.gpio);
//		
//		int ambientValue = indicator.read_ambient_light_intensity();
//		int tempValue = indicator.read_temperature();
//		System.out.println("Ambient value : " + ambientValue);
//		System.out.println("Temperature : " + tempValue);
//		
//		if (ambientValue < RpiIndicatorInterface.AMBIENT_DARK || tempValue < RpiIndicatorInterface.TEMPERATURE_COLD) {
//			indicator.led_when_low();
//		}
//		if (ambientValue > RpiIndicatorInterface.AMBIENT_BRIGHT || tempValue > RpiIndicatorInterface.TEMPERATURE_HOT) {
//			indicator.led_when_high();
//		}
//		if (ambientValue < RpiIndicatorInterface.AMBIENT_BRIGHT && ambientValue > RpiIndicatorInterface.AMBIENT_DARK) {
//			indicator.led_when_mid();
//		}
//		
//		Thread.sleep(10000);
//		indicator.led_all_off();
////		if (tempValue < RpiIndicatorInterface.TEMPERATURE_HOT && tempValue > RpiIndicatorInterface.TEMPERATURE_COLD) {
////			indicator.led_when_mid();
////		}
//	}
	
}
