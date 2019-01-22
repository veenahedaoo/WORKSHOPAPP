package com.tantransh.workshopapp.requestadapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Base64;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.tantransh.workshopapp.appdata.AppConstants;
import com.tantransh.workshopapp.appdata.BookingDetails;
import com.tantransh.workshopapp.jobbooking.data.ImageList;
import com.tantransh.workshopapp.requestlistener.BookingRequestListener;
import com.tantransh.workshopapp.services.ServiceDispatcher;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

public class BookingRequestAdapter implements BookingRequestListener {
    private ServiceDispatcher serviceDispatcher;
    private Context context;
    @SuppressLint("StaticFieldLeak")
    private static BookingRequestAdapter instance;
    private Intent broadcastIntent;

    public static BookingRequestAdapter getInstance(Context context){
        if(instance == null)
            instance = new BookingRequestAdapter(context);
        return instance;
    }

    private BookingRequestAdapter(Context context){
        this.context = context;
        broadcastIntent = new Intent();
        serviceDispatcher = ServiceDispatcher.getInstance(context);
    }
    @Override
    public void bookJob(final BookingDetails bookingDetails) {
        serviceDispatcher.bookJob(bookingDetails, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("Response : "+response);
                try{
                    switch (response.getInt("result")){
                        case 200:
                        case 201:
                            bookingDetails.setBookingId(response.getJSONObject("data").getString("booking_id"));
                            ImageList imageList = ImageList.getInstance();
                            System.out.println("Images : "+imageList.getSize());
                            if(imageList.getSize()>0)
                                uploadPictures(imageList);
                            else{
                                broadcastIntent.setAction(AppConstants.ACTION_PICTURE_UPLOADED);
                                context.sendBroadcast(broadcastIntent);
                            }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error : "+error);
                if(error.networkResponse != null && error.networkResponse.data != null){
                    try {
                        String body = new String(error.networkResponse.data, "UTF-8");
                        JSONObject json = new JSONObject(body);
                        switch (error.networkResponse.statusCode){
                            case 400:
                                if(json.getString("data").trim().equalsIgnoreCase("DUPLICATE SERVICE BOOKING")){
                                    broadcastIntent.setAction(AppConstants.ACTION_DUPLICATE_SERVICE_BOOKING);
                                    context.sendBroadcast(broadcastIntent);
                                }


                        }

                    } catch (UnsupportedEncodingException | JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    @Override
    public void uploadPictures(final ImageList imageList) {

        final int[] countUploads = {0};
        for(int i = 0; i<imageList.getSize(); i++){
            String image = getStringImage(imageList.getImage(i));
            final int finalI = i;
            serviceDispatcher.uploadPicture(image, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    System.out.println(response);
                    try {
                        JSONObject resJSON = new JSONObject(response);
                        if(resJSON.getString("msg").equals("SUCCESS")){
                            imageList.imageUploaded();
                            countUploads[0] = finalI;

                            if(imageList.uploadedImageCount() == imageList.getSize()){
                                broadcastIntent.setAction(AppConstants.ACTION_PICTURE_UPLOADED);
                                context.sendBroadcast(broadcastIntent);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    broadcastIntent.setAction(AppConstants.ACTION_SERVER_ERROR);
                    context.sendBroadcast(broadcastIntent);
                }
            });


        }


       
    }

    private String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        return  Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }
}
