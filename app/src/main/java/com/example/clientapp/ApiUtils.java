package com.example.clientapp;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

class ApiUtils{

    private static final int MY_SOCKET_TIMEOUT_MS = 100000 ;
    Context context;

    public ApiUtils(Context context) {
        this.context = context;
    }

    void offload_api(String data, String address){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = address;
        Toast.makeText(context,url, Toast.LENGTH_SHORT).show();

        SharedPreferencesHelper sharedPreferencesHelper= new SharedPreferencesHelper(context);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(context, response, Toast.LENGTH_SHORT).show();

                        sharedPreferencesHelper.SharedStoreResponseResult(true);
                        sharedPreferencesHelper.SharedStoreResponseData(response);
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
    }
}