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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

class ApiUtils{

    private static final int MY_SOCKET_TIMEOUT_MS = 100000 ;
    Context context;
    String response_data="";

    public ApiUtils(Context context) {
        this.context = context;
    }

    Void offload_api(String data_send, String address) {

        RequestQueue queue = Volley.newRequestQueue(context);
        String url = address;
        Toast.makeText(context, url, Toast.LENGTH_SHORT).show();

        SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(context);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        sharedPreferencesHelper.SharedStoreResponseResult(true);
                        Toast.makeText(context,response, Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show();
                VolleyLog.d("Volley", "Error: " + error.getMessage());

                sharedPreferencesHelper.SharedStoreResponseResult(false);
                sharedPreferencesHelper.SharedStoreResponseData("");
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