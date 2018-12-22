package com.tantransh.workshopapp.requestadapter;

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
    private static LoginRequestAdapter instance;
    private Intent broadcastIntent;
    private Context context;
    public static LoginRequestAdapter getInstance(Context context){

        if(instance==null){
            instance = new LoginRequestAdapter(context);
            instance.context = context;
        }
        return instance;
    }
    private LoginRequestAdapter(Context context){
        serviceDispatcher = ServiceDispatcher.getInstance(context);
        broadcastIntent = new Intent();
    }

    /**
     * @param userId
     * @param password
     */
    @Override
    public void login(String userId, String password) {
        serviceDispatcher.login(userId, password, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                JSONObject responseJson = (JSONObject) response;
                try {
                    if(responseJson.getString("msg").equals("SUCCESS")){
                        success();
                        AppPreferences ap = AppPreferences.getInstance(context);
                        ap.addToken(responseJson.getString("token"));
                    }
                    else if(responseJson.getString("msg").equals("FAILED")){
                        failed("FAILED");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
                if(error.networkResponse!=null){
                    System.out.println("Error Code : "+error.networkResponse.statusCode);
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
