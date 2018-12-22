package com.tantransh.workshopapp.requestadapter;

import android.content.Context;
import android.content.Intent;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.tantransh.workshopapp.appdata.AppConstants;
import com.tantransh.workshopapp.jobbooking.data.MakeInfo;
import com.tantransh.workshopapp.jobbooking.data.MakeList;
import com.tantransh.workshopapp.jobbooking.data.ServiceInfo;
import com.tantransh.workshopapp.jobbooking.data.ServiceList;
import com.tantransh.workshopapp.jobbooking.data.SpareInfo;
import com.tantransh.workshopapp.jobbooking.data.SparesList;
import com.tantransh.workshopapp.jobbooking.data.StateInfo;
import com.tantransh.workshopapp.jobbooking.data.StateList;
import com.tantransh.workshopapp.requestlistener.GeneralRequestListener;
import com.tantransh.workshopapp.services.ServiceDispatcher;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GeneralRequestAdapter implements GeneralRequestListener {
    private ServiceDispatcher serviceDispatcher;
    private Intent broadcastIntent;
    private Context context;
    private static GeneralRequestAdapter instance;


    private GeneralRequestAdapter(Context context){
        this.context = context;
        serviceDispatcher = ServiceDispatcher.getInstance(context);
        broadcastIntent = new Intent();
    }

    public static GeneralRequestAdapter getInstance(Context context){
        if(instance == null){
            instance = new GeneralRequestAdapter(context);
        }
        return instance;
    }
    @Override
    public void getStateList() {
        serviceDispatcher.getState(new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                System.out.println(response);
                JSONArray stateArr = (JSONArray) response;
                StateList stateList = StateList.getInstance();
                Gson gson = new Gson();
                for(int i = 0; i<stateArr.length(); i++){
                    try {
                        JSONObject json = stateArr.getJSONObject(i);
                        StateInfo stateInfo  = gson.fromJson(json.toString(),StateInfo.class);
                        stateList.addState(stateInfo);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                broadcastIntent.setAction(AppConstants.ACTION_STATE_LIST_LOADED);
                broadcastIntent.putExtra(AppConstants.EXTRA_STATE_LIST,stateList);
                context.sendBroadcast(broadcastIntent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
            }
        });
    }

    @Override
    public void getCityList(String stateCode) {

    }

    @Override
    public void getMakeList() {
        serviceDispatcher.getMake(new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                System.out.println(response);
                JSONArray makeArray = (JSONArray) response;
                MakeList makeList = MakeList.getInstance();
                makeList.clearList();
                Gson gson = new Gson();

                for(int i = 0; i<makeArray.length();i++){
                    try {
                        JSONObject json = makeArray.getJSONObject(i);
                        MakeInfo makeInfo = gson.fromJson(json.toString(),MakeInfo.class);
                        makeList.addItem(makeInfo);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                broadcastIntent.setAction(AppConstants.ACTION_LIST_LOADED);
                broadcastIntent.putExtra(AppConstants.EXTRA_MAKE_LIST,makeList);
                context.sendBroadcast(broadcastIntent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("ERROR : "+error);
            }
        });
    }

    @Override
    public void getSpares() {
        serviceDispatcher.getSpares(new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                System.out.println("Spares : "+response);
                JSONArray responseArr = (JSONArray) response;
                SparesList sparesList = SparesList.getInstance();
                sparesList.clear();
                Gson gson = new Gson();

                for(int i = 0; i< responseArr.length(); i++){
                    try {
                        SpareInfo spareInfo = gson.fromJson(responseArr.getJSONObject(i).toString(),SpareInfo.class);
                        sparesList.setSpare(spareInfo);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                broadcastIntent.putExtra(AppConstants.EXTRA_SPARE_LIST,sparesList);
                broadcastIntent.setAction(AppConstants.ACTION_SPARE_LIST_LOADED);
                context.sendBroadcast(broadcastIntent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error : "+error);
            }
        });
    }

    @Override
    public void getServices() {
        serviceDispatcher.getServices(new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                System.out.println("Services : "+response);
                if(response.toString().contains("NO RECORDS")){
                    broadcastIntent.setAction(AppConstants.ACTION_NO_RECORDS);
                    context.sendBroadcast(broadcastIntent);
                    return;
                }

                broadcastIntent.setAction(AppConstants.ACTION_SERVICE_LIST_LOADED);
                JSONArray serviceJsonArr = (JSONArray) response;

                Gson gson = new Gson();
                ServiceList serviceList = ServiceList.getInstance();
                for(int i = 0; i<serviceJsonArr.length(); i++){

                    try {
                        ServiceInfo serviceInfo = gson.fromJson(serviceJsonArr.getJSONObject(i).toString(),ServiceInfo.class);
                        serviceList.addService(serviceInfo);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                broadcastIntent.putExtra(AppConstants.EXTRA_SERVICE_LIST,serviceList);
                context.sendBroadcast(broadcastIntent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error : "+error);
            }
        });
    }
}
