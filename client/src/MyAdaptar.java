package com.example.ajinkya.check_view;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Ajinkya on 3/12/2015.
 */
public class MyAdaptar extends BaseExpandableListAdapter {

    private Context context;

    ArrayList<String> Rule_Keys;

    HashMap<String, Rules> actual_rules;

    public MyAdaptar(){

           }

    public MyAdaptar(Context context) {


        this.context = context;

        this.Rule_Keys = new ArrayList<String>();
        this.actual_rules = new HashMap<String, Rules>();
        Rule_Keys.add("Rule_1");
        Rule_Keys.add("Rule_2");
        Rule_Keys.add("Rule_3");
        Rule_Keys.add("Rule_4");

        actual_rules.put(Rule_Keys.get(0),new Rules("If Temp is Hot and ambient is Dim THEN blind is CLOSED"));
        actual_rules.put(Rule_Keys.get(1),new Rules("If Temp is Cold and ambient is bright THEN blind is half"));
        actual_rules.put(Rule_Keys.get(2),new Rules("If Temp is warm and ambient is Dim THEN blind is half"));
        actual_rules.put(Rule_Keys.get(3),new Rules("If Temp is warm and ambient is Bright THEN blind is CLOSED"));

    }

    /**
     * This function add rule to rule set
     * @param rule_key
     * @param rule
     */

    public void add_rule(String rule_key, String rule){
        this.Rule_Keys.add(rule_key);
        this.actual_rules.put(rule_key,new Rules(rule));
    }

    public int get_index_of(String x){
        System.out.println(x);
        return this.Rule_Keys.indexOf(x);
    }


    public void delete_rule(String rule_name){
        int index = this.Rule_Keys.indexOf(rule_name);
        this.Rule_Keys.remove(rule_name);
        this.actual_rules.remove(rule_name);
        System.out.println(this.Rule_Keys.get(0));
        //move_array_elements(index);
    }

    private void move_array_elements(int index) {

        for (int i = index+1; i < this.Rule_Keys.size(); i++){
            this.Rule_Keys.set((i-1),this.Rule_Keys.get(i));
        }

    }


    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getGroupCount() {
        return Rule_Keys.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return Rule_Keys.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        Rules r = actual_rules.get(Rule_Keys.get(groupPosition));
        return r.rule;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        TextView t = new TextView(context);
        t.setText(Rule_Keys.get(groupPosition));
        t.setPadding(50,50,50,50);
        //System.out.println(Rule_Keys.get(groupPosition));
        return t;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        System.out.println(groupPosition);
        TextView t = new TextView(context);
        Rules r = actual_rules.get(Rule_Keys.get(groupPosition));
        t.setText(r.rule);
        return t;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {

    }

    @Override
    public void onGroupCollapsed(int groupPosition) {

    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }
}
