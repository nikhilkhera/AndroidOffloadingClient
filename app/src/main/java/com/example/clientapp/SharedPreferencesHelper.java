package com.example.clientapp;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONObject;

public class SharedPreferencesHelper {

    Context context;
    String details_text = "details";
    String cloudlet_ip_address_text= "cloudlet_ip_address";
    String cloud_ip_address_text = "cloud_ip_address";
    String status_text = "status";
    String connected_text = "connected";
    String mode_text = "mode";
    String response_text = "response";
    String response_result_text = "response_result";
    String response_data_text = "response_data";


    public SharedPreferencesHelper(Context context) {
        this.context = context;
    }

    public void SharedStoreCloudletIpAddress(String ipaddress){

        SharedPreferences preferences = context.getSharedPreferences(details_text, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(cloudlet_ip_address_text, ipaddress);
        editor.apply();

    }

    public void SharedStoreCloudIpAddress(String ipaddress) {

        SharedPreferences preferences = context.getSharedPreferences(details_text, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(cloud_ip_address_text, ipaddress);
        editor.apply();

    }

    public void  SharedStoreStatus(boolean n, int mode){

        SharedPreferences preferences = context.getSharedPreferences(status_text, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(connected_text,n);
        editor.putInt(mode_text,mode);
        editor.apply();
    }

    public void SharedStoreResponseResult(boolean response_result){
        SharedPreferences preferences = context.getSharedPreferences(response_text, Context.MODE_PRIVATE );
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(response_result_text, response_result);
        editor.apply();
    }

    public void SharedStoreResponseData(String response_data){
        SharedPreferences preferences = context.getSharedPreferences(response_text, Context.MODE_PRIVATE );
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(response_result_text, response_data);
        editor.apply();
    }
    public boolean SharedGetResponseResult(){
        SharedPreferences preferences = context.getSharedPreferences(response_text, Context.MODE_PRIVATE);
        boolean response = preferences.getBoolean(response_result_text,false);
        return response;
    }

    public String SharedGetResponseData(){
        SharedPreferences preferences = context.getSharedPreferences(response_text, Context.MODE_PRIVATE);
        String response = preferences.getString(response_data_text,"");
        return response;
    }



    public boolean SharedGetConnected(){

        SharedPreferences preferences = context.getSharedPreferences(status_text, Context.MODE_PRIVATE);
        boolean connected = preferences.getBoolean(connected_text,false);
        return connected;
    }

    public int SharedGetMode(){

        SharedPreferences preferences = context.getSharedPreferences(status_text, Context.MODE_PRIVATE);
        int mode = preferences.getInt(mode_text,0);
        return mode ;
    }

    public String SharedGetCloudletIpAddress(){

        SharedPreferences preferences = context.getSharedPreferences(details_text, Context.MODE_PRIVATE);
        String address = preferences.getString(cloudlet_ip_address_text, "");
        return address;
    }

    public String SharedGetCloudIPAddress(){
        SharedPreferences preferences = context.getSharedPreferences(details_text, Context.MODE_PRIVATE);
        String address = preferences.getString(cloud_ip_address_text, "");
        return address;
    }



}
