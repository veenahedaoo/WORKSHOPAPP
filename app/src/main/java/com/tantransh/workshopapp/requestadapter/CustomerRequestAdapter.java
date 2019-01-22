package com.tantransh.workshopapp.requestadapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.tantransh.workshopapp.appdata.AppConstants;
import com.tantransh.workshopapp.appdata.BookingDetails;
import com.tantransh.workshopapp.appdata.Validator;
import com.tantransh.workshopapp.jobbooking.data.CustomerInformation;
import com.tantransh.workshopapp.requestlistener.CustomerRequestListener;
import com.tantransh.workshopapp.services.ServiceDispatcher;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class CustomerRequestAdapter implements CustomerRequestListener {
    private ServiceDispatcher serviceDispatcher;
    private Context context;
    @SuppressLint("StaticFieldLeak")
    private static  CustomerRequestAdapter instance;
    private Intent broadcastIntent;

    private CustomerRequestAdapter(){

    }

    public static CustomerRequestAdapter getInstance(Context context){
        if(instance == null){
            instance = new CustomerRequestAdapter();
            instance.serviceDispatcher = ServiceDispatcher.getInstance(context);
            instance.context = context;
            instance.broadcastIntent = new Intent();
        }

        return instance;
    }


    @Override
    public void addCustomer(CustomerInformation customerInformation) {
        serviceDispatcher.addCustomer(customerInformation, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("Response : "+response);
                try{
                    switch (response.getInt("result")){
                        case 200:
                            BookingDetails bookingDetails = BookingDetails.getInstance();
                            bookingDetails.setCustomerId(response.getJSONObject("data").getString("customer_id"));
                            success("CUSTOMER ADDED");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error : "+error);
                if(error.networkResponse!=null && error.networkResponse.data != null){
                    switch (error.networkResponse.statusCode){
                        case 400:
                            try {
                                String res = new String(error.networkResponse.data, "UTF-8");
                                System.out.println(res);
                                JSONObject json = new JSONObject(res);
                                if(json.getString("data").equalsIgnoreCase("DUPLICATE CONTACT")){
                                    failed(json.getString("data"));
                                }
                            } catch (UnsupportedEncodingException | JSONException e) {
                                e.printStackTrace();
                            }
                    }
                }
            }
        });
    }

    @Override
    public void searchCustomerByContact(String phoneNo) {
        serviceDispatcher.searchCustomerByContact(phoneNo, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                System.out.println(response);
                try{
                    switch (response.getInt("result")){
                        case 200:
                            Gson gson = new Gson();
                            CustomerInformation customerInformation = gson.fromJson(response.getJSONObject("data").toString(),CustomerInformation.class);
                            broadcastIntent.setAction(AppConstants.ACTION_CUSTOMER_FOUND);
                            broadcastIntent.putExtra(AppConstants.EXTRA_CUTOMER_INFORMATION,customerInformation);
                            context.sendBroadcast(broadcastIntent);
                            break;

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError verror) {
                if(verror instanceof ParseError){
                    if(verror.getMessage().trim().equalsIgnoreCase("org.json.JSONException: End of input at character 0 of")){
                        failed("NO RECORD");
                    }
                }
                else{
                    error(verror);
                }

            }
        });

    }

    @Override
    public void searchCustomerByVehicle(String vehicleNo) {
        serviceDispatcher.searchCustomerByVehicle(vehicleNo, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response);
                try{
                    switch (response.getInt("result")){
                        case 200:
                            Gson gson = new Gson();
                            CustomerInformation customerInformation = gson.fromJson(response.getJSONObject("data").toString(),CustomerInformation.class);
                            broadcastIntent.setAction(AppConstants.ACTION_CUSTOMER_FOUND);
                            broadcastIntent.putExtra(AppConstants.EXTRA_CUTOMER_INFORMATION,customerInformation);
                            context.sendBroadcast(broadcastIntent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error : "+error);
                if(error.networkResponse!=null && error.networkResponse.data != null){
                    switch (error.networkResponse.statusCode){
                        case 204:
                            failed("NO RECORD");
                            break;

                    }
                }
                else if(error instanceof ParseError){
                    if(error.getMessage().trim().equalsIgnoreCase("org.json.JSONException: End of input at character 0 of")){
                        failed("NO RECORD");
                    }
                }
                else {
                    error(error);

                }
            }
        });

    }

    @Override
    public void updateCustomer(final CustomerInformation customerInformation) {
        serviceDispatcher.updateCustomer(customerInformation, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("Response : "+response);
                try{
                    switch (response.getInt("result")){
                        case 200:
                            broadcastIntent.setAction(AppConstants.ACTION_CUSTOMER_UPDATED);
                            BookingDetails bookingDetails = BookingDetails.getInstance();
                            bookingDetails.setCustomerId(customerInformation.getCustomerId());
                            context.sendBroadcast(broadcastIntent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error : "+error);
                error(error);

            }
        });
    }

    @Override
    public void changeCustomer(CustomerInformation customerInformation) {

    }

    @Override
    public void changeCustomer(String customerId) {

    }

    @Override
    public void success(String message) {
        if(message.equals("CUSTOMER ADDED")){
            broadcastIntent.setAction(AppConstants.ACTION_CUSTOMER_ADDED);
            context.sendBroadcast(broadcastIntent);
        }
    }

    @Override
    public void failed(String message) {
        switch (message) {
            case "ERROR":
                broadcastIntent.setAction(AppConstants.ACTION_SERVER_ERROR);
                broadcastIntent.putExtra("msg", "Something went wrong");
                break;
            case "DUPLICATE CONTACT":
                broadcastIntent.setAction(AppConstants.ACTION_DUPLICATE_CONTACT);

                break;
            case "NO RECORD":
            case "NO RECORDS":
                broadcastIntent.setAction(AppConstants.ACTION_NO_RECORDS);
                break;
        }

        context.sendBroadcast(broadcastIntent);
    }

    @Override
    public void error(VolleyError error) {
        broadcastIntent.setAction(AppConstants.ACTION_SERVER_ERROR);
        broadcastIntent.putExtra("msg", Validator.getErrorMessage(error));
        context.sendBroadcast(broadcastIntent);
    }
}
