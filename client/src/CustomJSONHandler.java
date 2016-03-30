//package com.example.ajinkya.check_view;
//
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.List;
//
//import android.util.Log;
//
//import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
//import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;
//import com.thetransactioncompany.jsonrpc2.client.JSONRPC2Session;
//import com.thetransactioncompany.jsonrpc2.client.JSONRPC2SessionException;
//import org.json.*;
//public class CustomJSONHandler {
//	private static final String DEBUG_TAG = "Debug CustomJSONHandler";
//	private static final String ERROR_TAG = "Error CustomJSONHandler";
//	private static final String method = "sendRulesToServer";
//
//	public static void sendJSONRequest(String url,String method, List<Object> rulesList) {
//		Log.d(DEBUG_TAG, "Started sendJSONRequest");
//		URL serverURL = null;
//
//		try {
//			serverURL = new URL("http://"+ url);
//
//		} catch (MalformedURLException e) {
//			Log.d(DEBUG_TAG, "MalformedURLException occured");
//		// handle exception...
//		}
//		Log.d(DEBUG_TAG, "Create JSON rules");
//		JSONObject obj = new JSONObject();
//		JSONArray jArray = new JSONArray();
//		try {
//			for (int i = 0; i < rulesList.size(); i++) {
//				Log.d(DEBUG_TAG, rulesList.get(i).toString());
//				jArray.put(i, rulesList.get(i));
//			}
//			obj.put("rules", jArray);
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			Log.d(DEBUG_TAG, "JSONException occured");
//			e.printStackTrace();
//		}
//		JSONRPC2Session mySession = new JSONRPC2Session(serverURL);
//		int requestID = 1;
//		JSONRPC2Request request = new JSONRPC2Request(method, rulesList, requestID);
//
//		Log.d(DEBUG_TAG, "sending request");
//		JSONRPC2Response response = null;
//		try {
//			response = mySession.send(request);
//			Log.d(DEBUG_TAG,response.toString());
//
//		} catch (NullPointerException npe) {
//			Log.d(DEBUG_TAG," Holy Cow NullPointerException !!!");
//		} catch (JSONRPC2SessionException e) {
//
//			Log.d(DEBUG_TAG," Holy Cow JSONRPC2SessionException !!!");
//			Log.d(DEBUG_TAG, e.getMessage());
//		// handle exception...
//		}
//
//		if (response.indicatesSuccess())
//			Log.d(DEBUG_TAG, response.getResult().toString());
//		else
//			Log.e(ERROR_TAG, response.getError().getMessage().toString());
//
//	}
//
//}
