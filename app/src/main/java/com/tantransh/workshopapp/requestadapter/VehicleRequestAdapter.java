package com.tantransh.workshopapp.requestadapter;

import android.content.Context;
import android.content.Intent;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.tantransh.workshopapp.appdata.AppConstants;
import com.tantransh.workshopapp.appdata.Validator;
import com.tantransh.workshopapp.jobbooking.data.VehicleInformation;
import com.tantransh.workshopapp.requestlistener.VehicleRequestListener;
import com.tantransh.workshopapp.services.ServiceDispatcher;

import org.json.JSONException;
import org.json.JSONObject;

public class VehicleRequestAdapter implements VehicleRequestListener {
    ServiceDispatcher serviceDispatcher;

    private static VehicleRequestAdapter instance;
    private Context context;
    private Intent broadcastIntent;
    public static VehicleRequestAdapter getInstance(Context context){
        if(instance==null){
            instance = new VehicleRequestAdapter(context);
        }
        return instance;
    }

    private VehicleRequestAdapter(Context context){
        this.context = context;
        broadcastIntent = new Intent();
        serviceDispatcher = ServiceDispatcher.getInstance(context);
    }

    @Override
    public void registerVehicle(VehicleInformation vehicleInformation) {

        try {
            serviceDispatcher.registerVehicle(vehicleInformation, new Response.Listener() {
                @Override
                public void onResponse(Object response) {
                    System.out.println("Response : "+response);
                    JSONObject json = (JSONObject) response;
                    try {
                        String msg = json.getString("msg");
                        if(msg.equals("SUCCESS")){
                            success("SUCCESS");
                        }
                        else{
                            failed(msg);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error(error);

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void searchVehicle(String vehicleNo) {
        serviceDispatcher.searchVehicle(vehicleNo, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                System.out.println(response);
                JSONObject json = (JSONObject) response;
                if(json.has("msg")){
                    try {
                        failed(json.getString("msg"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    Gson gson = new Gson();
                    VehicleInformation vehicleInformation = gson.fromJson(json.toString(),VehicleInformation.class);
                    success(vehicleInformation);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
                error(error);
            }
        });
    }

    @Override
    public void updateVehicle(VehicleInformation vehicleInformation) {
        serviceDispatcher.updateVehicle(vehicleInformation, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                System.out.println(response);
                JSONObject json = (JSONObject) response;
                try {
                    if(json.getString("msg").equals("UPDATED")){
                        success(json.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
                error(error);
            }
        });
    }

    @Override
    public void success(String message) {
        if(message.equals("SUCCESS")){
            broadcastIntent.setAction(AppConstants.ACTION_ADD_VEHICLE_SUCCESS);

            context.sendBroadcast(broadcastIntent);
        }
        if(message.equals("UPDATED")){
            broadcastIntent.setAction(AppConstants.ACTION_VEHICLE_INFO_UPDATED);
            context.sendBroadcast(broadcastIntent);
        }
    }

    @Override
    public void failed(String msg) {
        if(msg.equals("FAILED")){
            broadcastIntent.setAction(AppConstants.ACTION_ADD_VEHICLE_FAILED);
            context.sendBroadcast(broadcastIntent);
        }
        if(msg.equals("NO RECORDS")){
            broadcastIntent.setAction(AppConstants.ACTION_NO_RECORDS);
            context.sendBroadcast(broadcastIntent);
        }

        if(msg.equals("INVALID VEHICLE NO")){
            broadcastIntent.setAction(AppConstants.ACTION_INVALID_VEHICLE_NO);
            context.sendBroadcast(broadcastIntent);
        }
    }

    @Override
    public void error(VolleyError error) {
        broadcastIntent.setAction(AppConstants.ACTION_SERVER_ERROR);
        broadcastIntent.putExtra("msg",Validator.getErrorMessage(error));
        context.sendBroadcast(broadcastIntent);
    }

    public void success(VehicleInformation vehicleInformation){
        broadcastIntent.setAction(AppConstants.ACTION_VEHICLE_INFO_LOADED);
        broadcastIntent.putExtra(AppConstants.EXTRA_VEHICLE_INFO,vehicleInformation);
        context.sendBroadcast(broadcastIntent);
    }
}
