package com.tantransh.workshopapp.services;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by android on 2/9/17.
 */

public class WebService extends AsyncTask<JSONObject,Object,String> {
    private String responseString = null;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    @Override
    protected String doInBackground(JSONObject... params) {
        OkHttpClient client = new OkHttpClient();
        JSONObject requestJson = params[0];
        try {
            String url = requestJson.getString("url");
            RequestBody body = RequestBody.create(JSON,requestJson.toString());
            System.out.println(body.contentLength()+" contents : "+requestJson);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            responseString =  response.body().string();
        } catch (JSONException | IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        return responseString;
    }
}
