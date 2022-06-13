package com.example.clientapp;

import androidx.appcompat.app.AppCompatActivity;

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

    MyThread myThread;
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

        myThread = new MyThread();
        new Thread(myThread).start();
    }

    private class MyThread implements Runnable
    {
        private String msg="Hello world";
        Socket socket;
        String ipaddress = "localhost";
        int flag = 0;

        @Override
        public void run() {
                checkConnection();
        }

        public void checkConnection(){
            try {
                ApiUtils apiUtils = new ApiUtils(MainActivity.this);
                apiUtils.offload_api("hello", "192.168.0.105:8080/calculate");
            }
            catch (Exception e){
                e.printStackTrace();


            }
        }

        public void sendMsg(){
            try {
                socket = new Socket(ipaddress, 2337);
                Toast.makeText(MainActivity.this, "Message Sent",Toast.LENGTH_SHORT).show();
                PrintWriter pw = new PrintWriter(socket.getOutputStream(),true);
                pw.write(msg);
                pw.close();
                socket.close();
            }
            catch (Exception e){
                e.printStackTrace();

            }
        }

        public void sendMsgParam(String msg) {
            this.msg = msg;
            this.flag = 1;
            if (!this.ipaddress.equals("localhost")){
                run();
            }
            else{
                Toast.makeText(MainActivity.this,"Please connect to a cloudlet first",Toast.LENGTH_SHORT).show();
            }

        }
        public void setIpAddress(String ipaddress){
            this.ipaddress = ipaddress;
            this.flag=0;
            run();

        }
    }

    public void btnClickSend(View v)
    {
        String msg = "hello this is nikhil's android";
        myThread.sendMsgParam(msg);
    }
    public void btnConnect(View v)
    {   /*
        String ipaddress = ipCloudlet.getText().toString();
        myThread.setIpAddress(ipaddress);

*/
        ApiUtils apiUtils = new ApiUtils(MainActivity.this);
        apiUtils.offload_api("hello", "http://192.168.147.166:8080/calculate");
        ipCloudlet.setText(sharedPreferencesHelper.SharedGetResponseData());

    }
}
