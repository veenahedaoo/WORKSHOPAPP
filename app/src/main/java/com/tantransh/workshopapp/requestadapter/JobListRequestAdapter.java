package com.tantransh.workshopapp.requestadapter;

import android.content.Context;
import android.content.Intent;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.tantransh.workshopapp.appdata.AppConstants;
import com.tantransh.workshopapp.appdata.Validator;
import com.tantransh.workshopapp.currentjobs.CurrentJobInfo;
import com.tantransh.workshopapp.currentjobs.CurrentJobList;
import com.tantransh.workshopapp.openjobs.OpenJobInfo;
import com.tantransh.workshopapp.openjobs.OpenJoblist;
import com.tantransh.workshopapp.requestlistener.JobListRequestLister;
import com.tantransh.workshopapp.services.ServiceDispatcher;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JobListRequestAdapter implements JobListRequestLister {
    private ServiceDispatcher serviceDispatcher;
    private Intent broadcastIntent;
    private JobListRequestAdapter(Context context){
        this.context = context;
        this.serviceDispatcher = ServiceDispatcher.getInstance(context);
        this.broadcastIntent = new Intent();
    }


    private Context context;

    public static JobListRequestAdapter getInstance(Context context){
        return new JobListRequestAdapter(context);
    }

    @Override
    public void getCurrentJobs() {
        serviceDispatcher.getCurrentJobs(new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                JSONArray listArr = (JSONArray) response;

                if(listArr.toString().contains("NO RECORDS")){
                    broadcastIntent.setAction(AppConstants.ACTION_NO_RECORDS);
                    context.sendBroadcast(broadcastIntent);
                    return;
                }

                if(listArr.toString().contains("ERROR")){
                    broadcastIntent.setAction(AppConstants.ACTION_SERVER_ERROR);
                    context.sendBroadcast(broadcastIntent);
                    return;
                }

                Gson gson = new Gson();
                CurrentJobList currentJobList = CurrentJobList.getInstance();
                currentJobList.remove();

                for(int i = 0; i<listArr.length();i++){
                    try {
                        JSONObject jsonObject = listArr.getJSONObject(i);
                        CurrentJobInfo jobInfo = gson.fromJson(jsonObject.toString(),CurrentJobInfo.class);
                        currentJobList.addJob(jobInfo);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                System.out.println("current jobs : "+currentJobList.getSize());

                broadcastIntent.setAction(AppConstants.ACTION_CURRENT_JOB_LIST_LOADED);
                broadcastIntent.putExtra(AppConstants.EXTRA_CURRENT_JOB_LIST,currentJobList);
                context.sendBroadcast(broadcastIntent);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error : "+error);
                String message = Validator.getErrorMessage(error);
                broadcastIntent.setAction(AppConstants.ACTION_SERVER_ERROR);
                broadcastIntent.putExtra("msg",message);
                context.sendBroadcast(broadcastIntent);
            }
        });
    }

    @Override
    public void getOpenJobs() {
        serviceDispatcher.getOpenJobs(new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                JSONArray responseArr = (JSONArray) response;
                OpenJoblist openJoblist = OpenJoblist.getInstance();
                openJoblist.removeAll();
                Gson gson = new Gson();
                for(int i =0; i<responseArr.length(); i++){
                    try {
                        JSONObject json = responseArr.getJSONObject(i);
                        if(json.has("msg")){
                            if(json.getString("msg").equals("NO RECORDS")){
                                broadcastIntent.setAction(AppConstants.ACTION_NO_RECORDS);
                            }
                            else {
                                broadcastIntent.setAction(AppConstants.ACTION_SERVER_ERROR);
                            }
                            context.sendBroadcast(broadcastIntent);
                            break;
                        }
                        else{
                            OpenJobInfo openJobInfo = gson.fromJson(json.toString(),OpenJobInfo.class);
                            openJoblist.setOpenJob(openJobInfo);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                broadcastIntent.setAction(AppConstants.ACTION_OPEN_JOB_LIST_LOADED);
                broadcastIntent.putExtra(AppConstants.EXTRA_OPEN_JOB_LIST,openJoblist);
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
    public void getJobCard() {

    }

    @Override
    public void getJobInformation(String jobId) {

    }

    @Override
    public void failed() {

    }

    @Override
    public void error(VolleyError error) {

    }
}
