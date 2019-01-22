package com.tantransh.workshopapp.requestadapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.tantransh.workshopapp.appdata.AppConstants;
import com.tantransh.workshopapp.appdata.AppPreferences;
import com.tantransh.workshopapp.requestlistener.LoginRequestListener;
import com.tantransh.workshopapp.services.ServiceDispatcher;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginRequestAdapter implements LoginRequestListener {
    private ServiceDispatcher serviceDispatcher;
    @SuppressLint("StaticFieldLeak")
    private static LoginRequestAdapter instance;
    private Intent broadcastIntent;
    private Context context;
    private AppPreferences appPreferences;
    public static LoginRequestAdapter getInstance(Context context){

        if(instance==null){
            instance = new LoginRequestAdapter(context);
            instance.context = context;
            instance.appPreferences = AppPreferences.getInstance(context);
        }
        return instance;
    }
    private LoginRequestAdapter(Context context){
        serviceDispatcher = ServiceDispatcher.getInstance(context);
        broadcastIntent = new Intent();
    }

    /**
     * @param userId userid
     * @param password password
     */
    @Override
    public void login(String userId, final String password) {
        serviceDispatcher.login(userId, password, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject responseJson =  response;

                try {

                    switch (responseJson.getInt("result")){
                        case 200:
                            responseJson = responseJson.getJSONObject("data");
                            success();
                            AppPreferences ap = AppPreferences.getInstance(context);
                            ap.addUser(responseJson.getString("login_id"),responseJson.getString("auth"),responseJson.getString("user_type"),responseJson.getString("user_id"),password);
                            break;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error : "+error);
                if(error.networkResponse!=null){
                    System.out.println("Error Code : "+error.networkResponse.statusCode);

                }

                if(error.networkResponse!=null && error.networkResponse.statusCode==401){
                    failed("FAILED");
                }

            }
        });
    }

    @Override
    public void success() {
        broadcastIntent.setAction(AppConstants.ACTION_LOGIN_SUCCESS);
        context.sendBroadcast(broadcastIntent);
    }

    @Override
    public void failed(String message) {
        System.out.println(message);
        if(message.equals("FAILED")){
            broadcastIntent.setAction(AppConstants.ACTION_LOGIN_FAILED);
            context.sendBroadcast(broadcastIntent);
        }
    }

    @Override
    public void error(String message) {

    }


}
