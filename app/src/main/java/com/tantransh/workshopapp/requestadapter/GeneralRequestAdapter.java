package com.tantransh.workshopapp.requestadapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.tantransh.workshopapp.appdata.AppConstants;
import com.tantransh.workshopapp.jobbooking.data.MakeInfo;
import com.tantransh.workshopapp.jobbooking.data.MakeList;
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
    @SuppressLint("StaticFieldLeak")
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
        serviceDispatcher.getState(new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try{
                    switch (response.getInt("result")){
                        case 200:
                            JSONArray stateArr = response.getJSONArray("data");
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
                            break;

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                context.sendBroadcast(broadcastIntent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
    }



    @Override
    public void getMakeList() {
        serviceDispatcher.getMake(new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response);
                try {
                    JSONObject responseJSON = new JSONObject(response.toString());
                    JSONArray makeArray = responseJSON.getJSONArray("data");
                    MakeList makeList = MakeList.getInstance();
                    makeList.clearList();
                    Gson gson = new Gson();

                    for(int i = 0; i<makeArray.length();i++){

                            JSONObject json = makeArray.getJSONObject(i);
                            MakeInfo makeInfo = gson.fromJson(json.toString(),MakeInfo.class);
                            makeList.addItem(makeInfo);

                    }

                    broadcastIntent.setAction(AppConstants.ACTION_LIST_LOADED);
                    broadcastIntent.putExtra(AppConstants.EXTRA_MAKE_LIST,makeList);
                    context.sendBroadcast(broadcastIntent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("ERROR : "+error);
            }
        });
    }

}
