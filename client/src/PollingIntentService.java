//package com.example.ajinkya.check_view;
//
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.ArrayList;
//
//import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
//import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;
//import com.thetransactioncompany.jsonrpc2.client.JSONRPC2Session;
//import com.thetransactioncompany.jsonrpc2.client.JSONRPC2SessionException;
//
//import edu.rit.csci759.pervasivemobile.TempChangeListActivity;
//
//import android.R;
//import android.app.IntentService;
//import android.app.Notification;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.app.TaskStackBuilder;
//import android.content.Context;
//import android.content.Intent;
//
//import android.net.Uri;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.os.ResultReceiver;
//import android.support.v4.app.NotificationCompat;
//import android.text.TextUtils;
//import android.util.Log;
//
//public class PollingIntentService extends IntentService {
//	public static final int STATUS_RUNNING = 0;
//	public static final int STATUS_FINISHED = 1;
//	public static final int STATUS_ERROR = 2;
//	private static final String TAG = "Debug PollingIntentService";
//	float retrievedTemp;
//	public static String responseFromPi;
//	private Context serviceContext = this;
//	public static ArrayList<String> tempList = new ArrayList<String>();
//
//	private static int temperature = 9999999, ambience = 0;
//
//	public PollingIntentService() {
//		super(PollingIntentService.class.getName());
//	}
//
//	@Override
//	protected void onHandleIntent(Intent intent) {
//		// TODO Auto-generated method stub
//		Log.d(TAG, "Service Started");
//		final ResultReceiver receiver = intent.getParcelableExtra("receiver");
//		String serverUrl = intent.getStringExtra("url");
//		String uri = intent.getDataString();
//		Bundle bundle = new Bundle();
//		Log.d(TAG, "Server URL : " + serverUrl);
//		keepPolling(uri);
//
//		Log.d(TAG, "Service Stopping");
////		this.stopSelf();
//
//	}
//
//	private void keepPolling(String url) {
//
//		URL serverURL = null;
//		try {
//			Log.d(TAG, "Keep Polling URL : " + url);
//			serverURL = new URL("http://" + url);
//
//		} catch (MalformedURLException mfe) {
//			mfe.printStackTrace();
//		}
//		JSONRPC2Session mySession = new JSONRPC2Session(serverURL);
//		while (true) {
//			JSONRPC2Request request = new JSONRPC2Request("getTempChange", 1);
//			JSONRPC2Response response = null;
//
//			try {
//				response = mySession.send(request);
//
//			} catch (JSONRPC2SessionException e) {
//
//				Log.e("error", e.getMessage().toString());
//				// handle exception...
//			}
//			if (response.indicatesSuccess()) {
//				responseFromPi = response.getResult().toString();
//				Log.d(TAG, responseFromPi);
//				String[] responseArray = responseFromPi.split(",");
//				retrievedTemp = Float.parseFloat(responseArray[1].trim());
//				if (temperature == 9999999) {
//					temperature = (int) retrievedTemp;
//					Log.d(TAG,
//							"Retrieved Temperature : "
//									+ Math.abs(retrievedTemp));
//				} else if (Math.abs(temperature - (int) retrievedTemp) >= 2) {
//					temperature = (int) retrievedTemp;
//					tempList.add(responseFromPi.replaceAll(",", "   "));
////					TempChangeListActivity.adapter.add(PollingIntentService.responseFromPi);
////					if (TempChangeListActivity.thisInstance != null) {
////						TempChangeListActivity.thisInstance.runOnUiThread(new Runnable() {
////
////							@Override
////							public void run() {
////								// TODO Auto-generated method stub
////								TempChangeListActivity.adapter.notifyDataSetChanged();
////							}
////						});
////					}
//
//					sendOutBroadCast();
//					new GetTemperatureChange().execute();
//				}
//
//			} else
//				Log.e("error", response.getError().getMessage().toString());
//		}
//	}
//
//	public void sendOutBroadCast() {
//		Log.d(TAG, "Entered sendOutBroadCast");
//		Intent i = new Intent();
//		i.setAction("com.edu.rit.csci759.tempChangeBroadcast");
//		i.putExtra("tempChange", responseFromPi);
//		sendBroadcast(i);
//	}
//
//	class GetTemperatureChange extends AsyncTask<Void, String, String> {
//
//		@Override
//		protected void onPreExecute() {
//		}
//
//		@Override
//		protected String doInBackground(Void... params) {
//			// TODO Auto-generated method stub
//			Intent resultIntent = new Intent(serviceContext, TempChangeListActivity.class);
//			PendingIntent resultPendingIntent = PendingIntent.getActivity(serviceContext, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//			NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(serviceContext);
//			mBuilder.setSmallIcon(R.drawable.alert_dark_frame);
//			mBuilder.setContentTitle("Pervasive Mobile");
//			mBuilder.setContentText(responseFromPi);
//			mBuilder.setContentIntent(resultPendingIntent);
//			mBuilder.setAutoCancel(true);
//			mBuilder.getNotification().flags |= Notification.FLAG_AUTO_CANCEL;
//			NotificationManager mNotifyMgr = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
//			mNotifyMgr.notify(002,mBuilder.build());
//			return null;
//		}
//
//		protected void onProgressUpdate(Integer... progress) {
//	         //setProgressPercent(progress[0]);
//	     }
//
//	     protected void onPostExecute(String result) {
//
//	     }
//
//	}
//
//
//
//}
