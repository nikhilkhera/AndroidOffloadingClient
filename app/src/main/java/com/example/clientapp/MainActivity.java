package com.example.clientapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.stealthcopter.networktools.SubnetDevices;
import com.stealthcopter.networktools.subnet.Device;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        test = findViewById(R.id.textView4);

        findSubnetDevices();
    }

    public void findSubnetDevices(){

        final long startTimeMillis = System.currentTimeMillis();
        SubnetDevices.fromLocalAddress().findDevices(new SubnetDevices.OnSubnetDeviceFound() {
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
                            test.appent("name: "+device.)
                            test.append("\n");
                        }
                    }
                });
            }
        });
    }
}
