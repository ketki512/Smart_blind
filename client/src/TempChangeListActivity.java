//package com.example.ajinkya.check_view;
//
//import java.util.ArrayList;
//
//import edu.rit.csci759.pervasivemobile.logic.PollingIntentService;
//
//import android.app.Activity;
//import android.app.ListActivity;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.widget.ArrayAdapter;
//import android.widget.ListView;
//
//public class TempChangeListActivity extends Activity {
//
//	private static final String TAG = "Debug TempChangeListActivity";
//	ArrayList<String> tempList = new ArrayList<String>();
//	public static ArrayAdapter<String> adapter;
//	public static TempChangeListActivity thisInstance;
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		Log.d(TAG, "Entered onCreate");
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_temp_change_list);
//		adapter = new ArrayAdapter<>(this, R.layout.items, PollingIntentService.tempList);
//		ListView listView = (ListView)findViewById(R.id.temperatureListView);
//		listView.setAdapter(adapter);
//		thisInstance = this;
//	}
//
//	protected void onResume() {
//		Log.d(TAG, "Entered onResume");
////		tempList.add(newTempVal);
//
//		adapter.add(PollingIntentService.responseFromPi);
////		tempList.add(newTempVal);
//		thisInstance.runOnUiThread(new Runnable() {
//
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				adapter.notifyDataSetChanged();
//			}
//		});
////		this.mMessageReceiver.
//		super.onResume();
//	}
//
//	public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
//
//		@Override
//		public void onReceive(Context context, Intent intent) {
//			// TODO Auto-generated method stub
//			Log.d(TAG, "Entered Broadcast Receiver's onReceive");
//			String newTempVal = (String) intent.getCharSequenceExtra("tempChange");
//
//
//		}
//
//	};
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.temp_change_list, menu);
//		return true;
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		// Handle action bar item clicks here. The action bar will
//		// automatically handle clicks on the Home/Up button, so long
//		// as you specify a parent activity in AndroidManifest.xml.
//		int id = item.getItemId();
//		if (id == R.id.action_settings) {
//			return true;
//		}
//		return super.onOptionsItemSelected(item);
//	}
//}
