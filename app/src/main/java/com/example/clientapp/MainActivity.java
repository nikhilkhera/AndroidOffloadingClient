package com.example.clientapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText ipCloudlet;
    TextView testview;
    ImageView status;
    Switch aSwitch;
    SharedPreferencesHelper sharedPreferencesHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testview = findViewById(R.id.testview);
        ipCloudlet = findViewById(R.id.editTextTextPersonName);
        status = findViewById(R.id.imageView);
        aSwitch = findViewById(R.id.switch1);
        sharedPreferencesHelper = new SharedPreferencesHelper(MainActivity.this);


        if (sharedPreferencesHelper.SharedGetConnected()){
            aSwitch.setChecked(true);
        }


        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    sharedPreferencesHelper.SharedStoreStatus(true, 1);
                    Toast.makeText(MainActivity.this, ""+sharedPreferencesHelper.SharedGetConnected(), Toast.LENGTH_SHORT).show();
                }else{
                    sharedPreferencesHelper.SharedStoreStatus(false, 0);
                }
                sharedPreferencesHelper.SharedStoreCloudletIpAddress(ipCloudlet.getText().toString());
                Toast.makeText(MainActivity.this, ""+sharedPreferencesHelper.SharedGetCloudletIpAddress(),Toast.LENGTH_SHORT).show();
            }
        });

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public void btnClickSend(View v) {
        String msg = "hello this is nikhil's android";
    }

    public void btnConnect(View v) {
        ApiUtils apiUtils = new ApiUtils(MainActivity.this);
        apiUtils.offload_api("hello", "http://192.168.0.106:8080/calculate");

        myThread thread = new myThread();
        thread.setPriority(1);
        thread.start();
    }

    class myThread extends Thread{

        @Override
        public void run() {
                for(int i=0; i <100;i++){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
                    if (sharedPreferencesHelper.SharedGetResponseResult()){
                        ipCloudlet.post(new Runnable() {
                            @Override
                            public void run() {
                                ipCloudlet.setText("here");
                            }
                        });

                    }
                }
                ipCloudlet.post(new Runnable() {
                    @Override
                    public void run() {
                        ipCloudlet.setText("not here");
                    }
                });

        }
    }
}
