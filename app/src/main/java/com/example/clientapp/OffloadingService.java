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
        public void offload(String data) throws RemoteException {
            SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(OffloadingService.this);
            String cloudlet_ipaddress = sharedPreferencesHelper.SharedGetCloudletIpAddress() + ":8080" + "/calculate";

            ApiUtils apiUtils = new ApiUtils(OffloadingService.this);
            apiUtils.offload_api(data, "http://192.168.0.106:8080/calculate/");
            return ;
        }

        @Override
        public boolean get_response_result() throws RemoteException{
            SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(OffloadingService.this);
            return sharedPreferencesHelper.SharedGetResponseResult();
        }

        @Override
        public void set_response_result_false() throws RemoteException {
            SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper( OffloadingService.this);
            sharedPreferencesHelper.SharedStoreResponseResult(false);
        }

        @Override
        public String get_response_data() throws RemoteException {
            SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(OffloadingService.this);
            return sharedPreferencesHelper.SharedGetResponseData();
        }

    };
}