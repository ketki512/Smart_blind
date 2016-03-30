package edu.rit.csci759.jsonrpc.server;

/**
* Demonstration of the JSON-RPC 2.0 Server framework usage. The request
* handlers are implemented as static nested classes for convenience, but in 
* real life applications may be defined as regular classes within their old 
* source files.
*
* @author Vladimir Dzhuvinov
* @version 2011-03-05
*/ 

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.sourceforge.jFuzzyLogic.rule.Rule;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;

import edu.rit.csci759.fuzzylogic.MyTipperClass;
import edu.rit.csci759.rspi.exercise.RpiIndicator;
import edu.rit.csci759.rspi.utils.MCP3008ADCReader;

import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;
import com.thetransactioncompany.jsonrpc2.server.MessageContext;
import com.thetransactioncompany.jsonrpc2.server.RequestHandler;

public class JsonHandler {
	static double temp;
	static double light;
	
	// Implements a handler for an "echo" JSON-RPC method
	 public static class EchoHandler implements RequestHandler {
		

	     // Reports the method names of the handled requests
	     public String[] handledRequests() {
			
	         return new String[]{"echo"};
	     }
			
			
	      // Processes the requests
	      public JSONRPC2Response process(JSONRPC2Request req, MessageContext ctx) {
				
	          if (req.getMethod().equals("echo")) {
					
	              // Echo first parameter
					
	              List params = (List)req.getParams();
		 
		         Object input = params.get(0);
		 
		         return new JSONRPC2Response(input, req.getID());
	         }
	         else {
		
	             // Method name not supported
					
	             return new JSONRPC2Response(JSONRPC2Error.METHOD_NOT_FOUND, req.getID());
		    }
	     }
	 }
		
		
	 
	 
	 // Implements a handler for "getDate" and "getTime" JSON-RPC methods
	 // that return the current date and time
	 public static class DateTimeHandler implements RequestHandler {
		
		
	     // Reports the method names of the handled requests
		public String[] handledRequests() {
		
		    return new String[]{"getDate", "getTime"};
		}
		
		
		// Processes the requests
		public JSONRPC2Response process(JSONRPC2Request req, MessageContext ctx) {
		
			String hostname="unknown";
			try {
				hostname=InetAddress.getLocalHost().getHostName();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		    if (req.getMethod().equals("getDate")) {
		    
		        DateFormat df = DateFormat.getDateInstance();
			
			String date = df.format(new Date());
			
			return new JSONRPC2Response(hostname+" "+date, req.getID());

	         }
	         else if (req.getMethod().equals("getTime")) {
	        	
		        DateFormat df = DateFormat.getTimeInstance();
			
			String time = df.format(new Date());
			
			return new JSONRPC2Response(hostname+" "+time, req.getID());
	         }
		    else {
		    
		        // Method name not supported
			
			return new JSONRPC2Response(JSONRPC2Error.METHOD_NOT_FOUND, req.getID());
	         }
	     }
	 }
	 
	 public static class Handlers implements RequestHandler {
			static int counter = 0;

	     // Reports the method names of the handled requests
	     public String[] handledRequests() {
			
	         return new String[]{"getTemp", "getAmbience"};
	     }
			
			
	      // Processes the requests
	      public JSONRPC2Response process(JSONRPC2Request req, MessageContext ctx) {
	    	  String hostname="unknown";
	    	  
				try {
					hostname=InetAddress.getLocalHost().getHostName();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
//			System.out.println("We are here");
	          if (req.getMethod().equals("getTemp")) {
					
	        	  /*
	      		 * Reading temperature from the TMP36 sensor using the MCP3008 ADC 
	      		 */
	      		int adc_temperature = MCP3008ADCReader.readAdc(MCP3008ADCReader.MCP3008_input_channels.CH0.ch());
	      		// [0, 1023] ~ [0x0000, 0x03FF] ~ [0&0, 0&1111111111]
	      		// convert in the range of 1-100
	      		int temperature = (int)(adc_temperature / 10.24);
	      		
	      		float tmp36_mVolts =(float) (adc_temperature * (3300.0/1024.0));
				// 10 mv per degree
		        float temp_C = (float) (((tmp36_mVolts - 100.0) / 10.0) - 40.0);
		        // convert celsius to fahrenheit
		        float temp_F = (float) ((temp_C * 9.0 / 5.0) + 32);
		        temp = temp_F;
//		         return new JSONRPC2Response(hostname + " " + temp_F, req.getID());
		        return new JSONRPC2Response((int)temp_F, req.getID());
	         }
	          
	          else if (req.getMethod().equals("getAmbience")){
	        	  
	        	  int adc_ambient = MCP3008ADCReader.readAdc(MCP3008ADCReader.MCP3008_input_channels.CH1.ch());
		  			// [0, 1023] ~ [0x0000, 0x03FF] ~ [0&0, 0&1111111111]
		  			// convert in the range of 1-100
		  			int ambient = (int)(adc_ambient / 10.24);
//			         return new JSONRPC2Response(hostname + " " + ambient, req.getID());
		  			light = ambient;
			         return new JSONRPC2Response(ambient, req.getID());
	          }
	         else {
		
	             // Method name not supported
					
	             return new JSONRPC2Response(JSONRPC2Error.METHOD_NOT_FOUND, req.getID());
		    }
	     }
	 }
	 
	// Implements a handler for "sendRulesToServer" JSON-RPC method
	 public static class RulesHandler implements RequestHandler {
		 RpiIndicator rpi = new RpiIndicator();
			MyTipperClass tipper = new MyTipperClass();
	     // Reports the method names of the handled requests
		public String[] handledRequests() {
		
		    return new String[]{"sendRulesToServer"};
		}
		
		
		// Processes the requests
		@SuppressWarnings("deprecation")
		public JSONRPC2Response process(JSONRPC2Request req, MessageContext ctx) {
		
			String hostname="unknown";
			try {
				hostname=InetAddress.getLocalHost().getHostName();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			if (req.getMethod().equals("sendRulesToServer")) {
				JSONArray rulesArray = (JSONArray) req.getParams();
				
				setFuzzyRules(rulesArray.toArray());
				return new JSONRPC2Response("Success", 1);
			} else {
				// Method name not supported
				return new JSONRPC2Response(JSONRPC2Error.METHOD_NOT_FOUND,
						req.getID());
			}
	     }
		
		private void setFuzzyRules(Object[] rulesStrArray) {
			System.out.println("Entered setFuzzyRules");
			tipper.resetRulesBlock();
			for (int i = 0; i< rulesStrArray.length; i++) {
				System.out.println(rulesStrArray[i]);
				String rule = rulesStrArray[i].toString().replace("IF ", "").replace(" IS", "");
				String[] arry = rule.split(" ");
				tipper.setRule(arry);
			}
			
			List<Rule> currentRules = tipper.getRules();
			for (int i = 0; i< currentRules.size(); i++) {
				System.out.println( "RULE " + i + " " +currentRules.get(i).getName());
			}
			tipper.setAmbience(light);
			tipper.setTemp(temp);
			double fuzzyValue = tipper.deFuzzify();
			if (fuzzyValue < 35) {
				rpi.led_when_high();
			} else if (fuzzyValue < 55) {
				rpi.led_when_mid();
			} else {
				rpi.led_when_low();
			}
		}
	 }
	 
	 // Implements a handler for Temperature change
	 public static class ChangeHandler implements RequestHandler {
		 private final float INIT_VALUE = 100000;
		private static float global_temp = 100000;
		static int counter = 0;
		
	     // Reports the method names of the handled requests
		public String[] handledRequests() {
		
		    return new String[]{"getTempChange"};
		}
		
		
		// Processes the requests
		public JSONRPC2Response process(JSONRPC2Request req, MessageContext ctx) {
		
			String hostname="unknown";
			if (counter == 0) {
	    		  counter++;
	    		  
	    	  }
			try {
				hostname=InetAddress.getLocalHost().getHostName();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			if (req.getMethod().equals("getTempChange")) {
				float temp_F;
//				while (true) {
					int adc_temperature = MCP3008ADCReader
							.readAdc(MCP3008ADCReader.MCP3008_input_channels.CH0
									.ch());
					// [0, 1023] ~ [0x0000, 0x03FF] ~ [0&0, 0&1111111111]
					// convert in the range of 1-100
					int temperature = (int) (adc_temperature / 10.24);

					float tmp36_mVolts = (float) (adc_temperature * (3300.0 / 1024.0));
					// 10 mv per degree
					float temp_C = (float) (((tmp36_mVolts - 100.0) / 10.0) - 40.0);
					// convert celsius to fahrenheit
					temp_F = (float) ((temp_C * 9.0 / 5.0) + 32);

//					if (global_temp == INIT_VALUE) {
//						global_temp = temp_F;
//					} else if (Math.abs(global_temp - temp_F) > 2) {
//						
//					}
//				}
				DateFormat df = DateFormat.getTimeInstance();
				String time = df.format(new Date());
				return new JSONRPC2Response(time + ", " + temp_F, req.getID());

			} else {
				// Method name not supported
				return new JSONRPC2Response(JSONRPC2Error.METHOD_NOT_FOUND,
						req.getID());
			}
	     }
	 }
}
