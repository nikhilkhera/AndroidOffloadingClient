package com.example.clientapp;

import android.content.Context;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

class ApiUtils{

    private static final int MY_SOCKET_TIMEOUT_MS = 100000 ;
    Context context;

    public ApiUtils(Context context) {

        this.context = context;
    }

    Void offload_api(String data_send, String address) {
        try {
            JSONObject jsonObject = new JSONObject(data_send);

            RequestQueue queue = Volley.newRequestQueue(context);
            String url = address + "?func=" + jsonObject.getString("func") + "&para=" + jsonObject.getString("para");


            SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(context);

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            sharedPreferencesHelper.SharedStoreResponseResult(true);
                            sharedPreferencesHelper.SharedStoreResponseData(response);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d("Volley", "Error: " + error.getMessage());
                    sharedPreferencesHelper.SharedStoreResponseResult(false);

                }
            });
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    MY_SOCKET_TIMEOUT_MS,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(stringRequest);
            queue.start();

        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    Void health_check(String address) {

        RequestQueue queue = Volley.newRequestQueue(context);
        String url = address;

        SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(context);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        sharedPreferencesHelper.SharedStoreHealth(true);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Volley", "Error: " + error.getMessage());
                sharedPreferencesHelper.SharedStoreHealth(false);


            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
        queue.start();
        return null;
    }
}