package com.example.ajinkya.check_view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.support.annotation.IdRes;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;


public class Manipulate_rules extends Activity  {

    int index = 0;
    ArrayList<String> ants_consq = new ArrayList<String>();
    static MyAdaptar apdapt;

    Button add ;
    Button then;
    Button done;
    Button Done_in_edit;
    Button Done_in_add;

    Spinner Temp;
    Spinner Amb;
    Spinner Conj;

    LinearLayout parent_layout;
    LayoutInflater layoutInflator;
    View view;
    RelativeLayout lin_view;

    int count=0;
    ArrayList<String> new_rules = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manipulate_rules);

        apdapt = MainActivity.adap;
        // viewDialog = new AlertDialog.Builder(this);

        add = (Button) findViewById(R.id.Add);
        then = (Button) findViewById(R.id.Then);
        done = (Button) findViewById(R.id.done);
        Done_in_edit = (Button) findViewById(R.id.edit_done);
        //Done_in_add; = (Button) findViewById(R.id.done);


//        Temp = (Spinner)lin_view.findViewById(R.id.temp_options);
        Intent i = getIntent();


        if (i != null) {
            int input = i.getIntExtra("classifier", 0);
            System.out.println(input);
            if (input == 1000000) {
                Done_in_edit.setVisibility(View.GONE);
                new_rules.clear();
                ants_consq.clear();
                add();

            } else {
                new_rules.clear();
                ants_consq.clear();
                done.setVisibility(View.GONE);
                add();

                // CLeaning the list for constraints

                for (String s : new_rules) {
                    s = s.toLowerCase();
                    if (s.equals("null")) {
                        continue;
                    } else {
                        ants_consq.add(s);

                        System.out.println(" =====>> " + s);
                    }
                }
            }


            Done_in_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("???????  EDIT BUTTON");

                    for (String s : new_rules) {
                        s = s.toLowerCase();
                        if (s.equals("null")) {
                            continue;
                        } else {
                            ants_consq.add(s);

                            System.out.println(" =====>> " + s);
                        }

                    }
                    int rule_num = ++MainActivity.number_of_rules;
                    String rule_key = "Rule_" + rule_num;
                    apdapt.Rule_Keys.add(rule_key);
                    apdapt.actual_rules.put(rule_key, new Rules(""));

                /*String rule="If";
                String temp = ants_consq.get(0);
                temp = temp.toLowerCase();

                *//*if(temp.equals("null")){

                }*//*
                if(temp.equals("Too Hot") || temp.equals("Hot") || temp.equals("Moderate") || temp.equals("Cold") || temp.equals("Too Cold")) {
                    rule = rule + "Temp Is" + temp;
                }
                else if(temp.equals("Dim") || temp.equals("Bright") || temp.equals("Dark") || temp.equals("null")){
                    rule = rule + "Ambience Is" + temp;
                }
                for(String temp_1: ants_consq) {
                   // rule = temp_1 +
                }
*/
                }
            });


            done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    for (String s : new_rules) {
                        s = s.toLowerCase();
                        if (s.equals("null")) {
                            continue;
                        } else {
                            ants_consq.add(s);

                            System.out.println(" =====>> " + s);
                        }
                    }

                    int rule_num = ++MainActivity.number_of_rules;
                    String rule_key = "Rule_" + rule_num;
                    apdapt.Rule_Keys.add(rule_key);
                    apdapt.actual_rules.put(rule_key, new Rules(""));

                }
            });


            then.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog_box_for_then();
                }
            });
        }
    }


    public void add(){


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                parent_layout = (LinearLayout) findViewById(R.id.inside_scroll_view_layout);
                layoutInflator = getLayoutInflater();
                view = layoutInflator.inflate(R.layout.contents, parent_layout, false);
                lin_view = (RelativeLayout) view.findViewById(R.id.contents);
                parent_layout.addView(lin_view);
                System.out.println(count);

                ScrollView y = (ScrollView)findViewById(R.id.scrollView);
                count = count+y.getChildCount();


                final Spinner temp = (Spinner)lin_view.findViewById(R.id.temp_options);
                final Spinner amb  = (Spinner)lin_view.findViewById(R.id.ambience_options);
                final Spinner opt = (Spinner)lin_view.findViewById(R.id.conjucts_options);
                final Spinner rule_conjucts = (Spinner)lin_view.findViewById(R.id.rule_conjuncts);


                temp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        System.out.println(temp.getSelectedItem().toString());
                        new_rules.add(temp.getSelectedItem().toString());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


                amb.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        System.out.println(amb.getSelectedItem().toString());
                        new_rules.add(amb.getSelectedItem().toString());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                System.out.println(amb.getSelectedItem().toString());

                opt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        System.out.println(opt.getSelectedItem().toString());
                        new_rules.add(opt.getSelectedItem().toString());
                        System.out.print("k");
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                rule_conjucts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                           new_rules.add(rule_conjucts.getSelectedItem().toString());
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


            }
        });

    }


        public void dialog_box_for_then(){
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            final String [] options = {"Open","Closed", "Half"};
            dialog.setTitle("Blind Position");
            dialog.setItems(options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String temp = options[which];
                    System.out.println(temp);
                    new_rules.add(temp);
                    System.out.println("**************************");
                    for(String j:new_rules){
                        System.out.println(j);
                    }
                }
            });

            AlertDialog alert_dialog = dialog.create();
            alert_dialog.show();
        }

}
