package com.example.ajinkya.check_view;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.SortedMap;


public class MainActivity extends Activity {

    private Button Edit;
    private Button Add;
    private Button delete;
    private Button Output;

    Button btn_send_request;
    static EditText et_server_url;
    static EditText et_requst_method;
    EditText et_Rule_default;
    TextView tv_response;
    TextView temperature, ambience;
    ArrayList<String> tempList = new ArrayList<String>();




    private AlertDialog.Builder dialog_builder;
    public static List temp = new ArrayList();
    public static HashMap<String,String> rules = new HashMap<String, String>();
    ExpandableListView exv;
    static MyAdaptar adap;


    static int number_of_rules;


    private void dialog_box(){
        dialog_builder = new AlertDialog.Builder(this);
        final EditText te = new EditText(this);

        dialog_builder.setTitle("Enter rule number");
        dialog_builder.setMessage("Please enter number only");
        dialog_builder.setView(te);

        dialog_builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                int temp_var = Integer.parseInt(te.getText().toString());
                Intent i = new Intent(MainActivity.this, Manipulate_rules.class);
                i.putExtra("rule_number",temp_var);
                startActivity(i);

            }
        });
        AlertDialog  dialog_for_input = dialog_builder.create();
        dialog_for_input.show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        number_of_rules = 4;
        adap = new MyAdaptar(this);
        exv = (ExpandableListView) findViewById(R.id.expandableListView_1);
        exv.setAdapter(adap);

        Edit = (Button) findViewById(R.id.Edit_button);
        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_box();
            }
        });

        Add = (Button) findViewById(R.id.add_button);
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Manipulate_rules.class);
                i.putExtra("classifier", 1000000);
                startActivity(i);
            }
        });

        delete = (Button) findViewById(R.id.ok_button);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_box_for_delete();
            }
        });

        Output = (Button)findViewById(R.id.output_button);
        Output.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

        /*et_server_url = (EditText) findViewById(R.id.et_serverURL);
        et_requst_method = (EditText) findViewById(R.id.et_requestMethod);
        et_Rule_default = (EditText) findViewById(R.id.et_Rules);
        tv_response = (TextView) findViewById(R.id.tv_response);
        temperature = (TextView) findViewById(R.id.temperatureView);
        ambience = (TextView) findViewById(R.id.ambienceView);

        View.OnClickListener buttonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SendJSONRequest().execute();
//		    	Intent mServiceIntent = new Intent(MainActivity.this, PollingIntentService.class);
//		    	Log.d("Debug MainActivity", "Executed Line 1 : " + et_server_url.getText().toString());
//		    	mServiceIntent.setData(Uri.parse(et_server_url.getText().toString()));
//		    	Log.d("Debug MainActivity", "Executed Line 2");
//		    	MainActivity.this.startService(mServiceIntent);
//		    	Log.d("Debug MainActivity", "Executed Line 3");
            }
        };
        btn_send_request = (Button) findViewById(R.id.btn_sendRequest);
        btn_send_request.setOnClickListener(buttonListener);


    }





    public void onClickGetValues (View view) {
        new SendGetTimeRequest().execute();
    }

    public void onClickShowTempList (View v) {
        Intent i = new Intent(this, TempChangeListActivity.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);





    class SendJSONRequest extends AsyncTask<Void, String, String> {
        String response_txt;

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(Void... params) {
            String serverURL_text = et_server_url.getText().toString();
            String request_method = et_requst_method.getText().toString();
            List<Object> list = new ArrayList<Object>();
            list.add(et_Rule_default.getText().toString());
            CustomJSONHandler.sendJSONRequest(serverURL_text,request_method, list);

            return response_txt;
        }

        protected void onProgressUpdate(Integer... progress) {
            //setProgressPercent(progress[0]);
        }

        protected void onPostExecute(String result) {
//	    	 Log.d("debug", result);
//	    	 Log.d("debug", response_txt);
            tv_response.setText(result);
        }

    }


    class SendGetTimeRequest extends AsyncTask<Void, String, String> {
        String response_txt;
        ArrayList<String> responseList = new ArrayList<String>();
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(Void... params) {
            String serverURL_text = et_server_url.getText().toString();
            String[] method = {"getTemp","getAmbience"};
            for (int i = 0; i < method.length; i++){
                response_txt = JSONHandler.testJSONRequest(serverURL_text, method[i]);
                responseList.add(response_txt);
            }
            return response_txt;
        }

        protected void onProgressUpdate(Integer... progress) {
            //setProgressPercent(progress[0]);
        }

        protected void onPostExecute(String result) {
            temperature.setText(responseList.get(0));
            ambience.setText(responseList.get(1));
        }

    }


*/
    public void dialog_box_for_delete(){


            dialog_builder = new AlertDialog.Builder(this);
            final EditText te = new EditText(this);

            dialog_builder.setTitle("Enter rule number");
            dialog_builder.setMessage("Please enter number only");
            dialog_builder.setView(te);

            dialog_builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    int temp_var = Integer.parseInt(te.getText().toString());
                    if(temp_var >0 && temp_var < adap.Rule_Keys.size()){
                    delete(temp_var);
                    }
                    else{
                        return;
                    }
                }
            });
            AlertDialog  dialog_for_input = dialog_builder.create();
            dialog_for_input.show();

        }

    public boolean delete(int n){
        if(adap.Rule_Keys.size() !=0)
        {
            String temp_rule = adap.Rule_Keys.get(n-1);
            adap.actual_rules.remove(temp_rule);
            adap.Rule_Keys.remove(n-1);
            return true;
        }
        else
            return false;
    }


}
