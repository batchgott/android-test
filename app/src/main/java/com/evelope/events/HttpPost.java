package com.evelope.events;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HttpPost extends Service{

    RequestQueue r;
    String domain="localhost:8082";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public Long LoginRequest(String email,String password){

        r=Volley.newRequestQueue(this);

        String url=email+":"+password+"@"+domain+"/users/getUserID/"+email+"/"+password;


        JsonArrayRequest jo=new JsonArrayRequest(Request.Method.POST,url,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONArray id=response;
                for (int i=0;i<id.length();i++) {
                    try {
                        JSONObject idO=id.getJSONObject(i);
                        Long idL=idO.getLong("userID");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        );
    }

}
