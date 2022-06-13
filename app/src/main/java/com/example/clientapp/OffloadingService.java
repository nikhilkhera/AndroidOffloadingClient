package com.example.clientapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import java.io.IOException;

public class OffloadingService extends Service {
    public OffloadingService() {
    }

    @Override
    public IBinder onBind(Intent intent) {

        return myBinder;
    }

    OffloadingInterface.Stub myBinder = new OffloadingInterface.Stub() {
        @Override
        public int status() throws RemoteException {
            SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(OffloadingService.this);
            return sharedPreferencesHelper.SharedGetMode();
        }

        @Override
        public boolean get_status_connected() throws RemoteException {
            SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(OffloadingService.this);
            return sharedPreferencesHelper.SharedGetConnected();
        }

        @Override
        public String offload(String data) throws RemoteException {
            SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(OffloadingService.this);

            ApiUtils apiUtils = new ApiUtils(OffloadingService.this);
            String cloudlet_ipaddress = sharedPreferencesHelper.SharedGetCloudletIpAddress() + ":8080" + "/calculate";

            //String response_data = apiUtils.offload_api("hi", cloudlet_ipaddress);
            return "heeeee";
        }
    };
}