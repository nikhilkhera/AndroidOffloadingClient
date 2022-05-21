package com.example.clientapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.stealthcopter.networktools.SubnetDevices;
import com.stealthcopter.networktools.subnet.Device;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    MyThread myThread;
    EditText ipCloudlet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ipCloudlet = findViewById(R.id.editTextTextPersonName);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        myThread = new MyThread();
        new Thread(myThread).start();
        //findSubnetDevices();
    }

    private class MyThread implements Runnable
    {
        private String msg="Hello world";
        Socket socket;
        String ipaddress = "localhost";
        int flag = 0;

        @Override
        public void run() {
            if (flag == 0){
                checkConnection();
            }
            else{
                sendMsg();
            }
        }

        public void checkConnection(){
            try {
                socket = new Socket(ipaddress, 2337);
                PrintWriter pw = new PrintWriter(socket.getOutputStream(),true);
                Toast.makeText(MainActivity.this,"Connection Established",Toast.LENGTH_SHORT).show();
                pw.write("connection established");
                pw.close();
                socket.close();
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
    {
        String ipaddress = ipCloudlet.getText().toString();
        myThread.setIpAddress(ipaddress);
    }
/*
    public void findSubnetDevices(){

        final long startTimeMillis = System.currentTimeMillis();
        test.setText("Scanning");
        SubnetDevices subnetDevices = SubnetDevices.fromLocalAddress().findDevices(new SubnetDevices.OnSubnetDeviceFound() {
            @Override
            public void onDeviceFound(Device device) {

            }

            @Override
            public void onFinished(ArrayList<Device> devicesFound) {
                float timeTaken = (System.currentTimeMillis() - startTimeMillis) / 1000.0f;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        test.setText("Scan Finished");
                        for (Device device : devicesFound) {
                            test.append("Device " + device.hostname);
                            test.append("IP : " + device.ip + "\n");
                            test.append("Mack : " + device.mac + "\n");
                            test.append("\n");
                        }
                    }
                });
            }
        });
    }*/
}
