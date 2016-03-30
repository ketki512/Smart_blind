package edu.rit.csci759.jsonrpc.server;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;

import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import com.thetransactioncompany.jsonrpc2.client.JSONRPC2Session;
import edu.rit.csci759.rspi.utils.MCP3008ADCReader;

public class SendTempChange extends Thread {
	private URL clientUrl;
	private float temp_F;
	private final float INIT_VALUE = 100000;
	private static float global_temp = 100000;
	public SendTempChange (String url) {
		try {
			clientUrl = new URL("http://"+url+"8080");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void run() {
		
		while (true) {
			JSONRPC2Session mySession = new JSONRPC2Session(clientUrl);
			
			int adc_temperature = MCP3008ADCReader
					.readAdc(MCP3008ADCReader.MCP3008_input_channels.CH0
							.ch());
			int temperature = (int) (adc_temperature / 10.24);

			float tmp36_mVolts = (float) (adc_temperature * (3300.0 / 1024.0));
			// 10 mv per degree
			float temp_C = (float) (((tmp36_mVolts - 100.0) / 10.0) - 40.0);
			// convert celsius to fahrenheit
			temp_F = (float) ((temp_C * 9.0 / 5.0) + 32);
			
			if (global_temp == INIT_VALUE) {
				global_temp = temp_F;
			} else if (Math.abs(global_temp - temp_F) > 2) {
				DateFormat df = DateFormat.getTimeInstance();
				String time = df.format(new Date());
					int requestID = 1;
					JSONRPC2Request request = new JSONRPC2Request(time + ", " + temp_F, requestID);
//					mySession.send(request);
			}
		}

	}
}
