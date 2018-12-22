package com.tantransh.workshopapp.requestadapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Base64;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tantransh.workshopapp.appdata.AppConstants;
import com.tantransh.workshopapp.appdata.BookingDetails;
import com.tantransh.workshopapp.jobbooking.data.ImageList;
import com.tantransh.workshopapp.requestlistener.BookingRequestListener;
import com.tantransh.workshopapp.services.ServiceData;
import com.tantransh.workshopapp.services.ServiceDispatcher;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

public class BookingRequestAdapter implements BookingRequestListener {
    private ServiceDispatcher serviceDispatcher;
    private Context context;
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
        serviceDispatcher.bookJob(bookingDetails, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                System.out.println("Response : "+response);
                JSONObject resJSON = (JSONObject) response;
                if(resJSON.has("bookingId")){
                    try {
                        bookingDetails.setBookingId(resJSON.getString("bookingId"));

                        ImageList imageList = ImageList.getInstance();
                        if(imageList.getSize()>0)
                            uploadPictures(imageList);
                        else{
                            broadcastIntent.setAction(AppConstants.ACTION_PICTURE_UPLOADED);
                            context.sendBroadcast(broadcastIntent);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error : "+error);
            }
        });
    }

    @Override
    public void uploadPictures(ImageList imageList) {

        final int[] countUploads = {0};
        for(int i = 0; i<imageList.getSize(); i++){
            String image = getStringImage(imageList.getImage(i));
            final int finalI = i;
            serviceDispatcher.uploadPicture(image, new Response.Listener() {
                @Override
                public void onResponse(Object response) {
                    System.out.println(response);
                    JSONObject resJSON = (JSONObject) response;
                    try {
                        if(resJSON.getString("msg").equals("SUCCESS")){
                            countUploads[0] = finalI;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println(error);
                }
            });
        }

        if(countUploads.length == imageList.getSize()){
            broadcastIntent.setAction(AppConstants.ACTION_PICTURE_UPLOADED);
            context.sendBroadcast(broadcastIntent);
        }
        else {
            broadcastIntent.setAction(AppConstants.ACTION_SERVER_ERROR);
            context.sendBroadcast(broadcastIntent);
        }

    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
}
