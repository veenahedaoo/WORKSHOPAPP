package com.tantransh.workshopapp.requestadapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import com.android.volley.ParseError;
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
    @SuppressLint("StaticFieldLeak")
    private static VehicleRequestAdapter instance;
    private ServiceDispatcher serviceDispatcher;
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
            serviceDispatcher.registerVehicle(vehicleInformation, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    System.out.println("Response : "+response);

                    try {
                        switch(response.getInt("result")){
                            case 200:
                            case 201:
                                success("SUCCESS");
                                break;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if(error.networkResponse != null && error.networkResponse.data != null){
                        switch (error.networkResponse.statusCode){
                            case 400:
                                failed("FAILED");
                        }
                    }
                    else if(error instanceof ParseError){
                        if(error.getMessage().trim().equalsIgnoreCase("org.json.JSONException: End of input at character 0 of")){
                            error(error);
                        }
                    }
                    else{
                        error(error);
                    }

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();

        }
    }

    @Override
    public void searchVehicle(String vehicleNo) {
        serviceDispatcher.searchVehicle(vehicleNo, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response);
                try{
                    switch (response.getInt("result")){
                        case 200:
                            Gson gson = new Gson();
                            VehicleInformation vehicleInformation = gson.fromJson(response.getJSONObject("data").toString(),VehicleInformation.class);
                            success(vehicleInformation);
                            break;

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error.networkResponse!=null && error.networkResponse.data !=null){
                    switch (error.networkResponse.statusCode){
                        case 204:
                            failed("NO RECORDS");
                            break;
                        case 400:
                            error(error);
                            break;
                        default:
                            error(error);
                            break;
                    }
                }
                else if(error instanceof ParseError){
                    if(error.getMessage().trim().equalsIgnoreCase("org.json.JSONException: End of input at character 0 of")){
                        failed("NO RECORDS");
                    }
                }
                else {
                    error(error);
                }
            }
        });
    }

    @Override
    public void updateVehicle(VehicleInformation vehicleInformation) {
        serviceDispatcher.updateVehicle(vehicleInformation, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response);
                try{
                    switch (response.getInt("result")){
                        case 200:
                            success("UPDATED");
                            break;
                        case 201:
                            success("SUCCESS");
                            break;
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

    private void success(VehicleInformation vehicleInformation){
        broadcastIntent.setAction(AppConstants.ACTION_VEHICLE_INFO_LOADED);
        broadcastIntent.putExtra(AppConstants.EXTRA_VEHICLE_INFO,vehicleInformation);
        System.out.println("vehicle no : "+vehicleInformation.getVehicleRegNo());
        context.sendBroadcast(broadcastIntent);
    }
}
