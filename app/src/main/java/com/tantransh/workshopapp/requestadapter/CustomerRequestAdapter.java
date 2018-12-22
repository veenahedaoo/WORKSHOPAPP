package com.tantransh.workshopapp.requestadapter;

import android.content.Context;
import android.content.Intent;

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

public class CustomerRequestAdapter implements CustomerRequestListener {
    private ServiceDispatcher serviceDispatcher;
    private Context context;
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
        serviceDispatcher.addCustomer(customerInformation, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                System.out.println("Response : "+response);
                JSONObject responseJSON = (JSONObject) response;
                try {
                    if(responseJSON.has("msg") && responseJSON.getString("msg").equals("SUCCESS")){
                        BookingDetails bookingDetails = BookingDetails.getInstance();
                        bookingDetails.setCustomerId(responseJSON.getString("customerId"));
                        success("CUSTOMER ADDED");
                    }
                    else{
                        failed(responseJSON.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    failed("ERROR");
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
    public void searchCustomerByContact(String phoneNo) {
        serviceDispatcher.searchCustomerByContact(phoneNo, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                System.out.println(response);
                JSONObject rJson = (JSONObject) response;
                if(rJson.has("msg")){
                    try {
                        failed(rJson.getString("msg"));
                        return;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Gson gson = new Gson();
                CustomerInformation customerInformation = gson.fromJson(rJson.toString(),CustomerInformation.class);
                broadcastIntent.setAction(AppConstants.ACTION_CUSTOMER_FOUND);
                broadcastIntent.putExtra(AppConstants.EXTRA_CUTOMER_INFORMATION,customerInformation);
                context.sendBroadcast(broadcastIntent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.err.println(error);
                error(error);
            }
        });

    }

    @Override
    public void searchCustomerByVehicle(String vehicleNo) {
        serviceDispatcher.searchCustomerByVehicle(vehicleNo, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                System.out.println(response);
                JSONObject rJson = (JSONObject) response;
                if(rJson.has("msg")){
                    try {
                        failed(rJson.getString("msg"));
                        return;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Gson gson = new Gson();
                CustomerInformation customerInformation = gson.fromJson(rJson.toString(),CustomerInformation.class);
                broadcastIntent.setAction(AppConstants.ACTION_CUSTOMER_FOUND);
                broadcastIntent.putExtra(AppConstants.EXTRA_CUTOMER_INFORMATION,customerInformation);
                context.sendBroadcast(broadcastIntent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.err.println(error);
                error(error);
            }
        });

    }

    @Override
    public void updateCustomer(final CustomerInformation customerInformation) {
        serviceDispatcher.updateCustomer(customerInformation, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                System.out.println("Response : "+response);
                JSONObject responseJSON = (JSONObject) response;
                try {
                    if(responseJSON.getString("msg").equals("CUSTOMER UPDATED")){
                        broadcastIntent.setAction(AppConstants.ACTION_CUSTOMER_UPDATED);
                        BookingDetails bookingDetails = BookingDetails.getInstance();
                        bookingDetails.setCustomerId(customerInformation.getCustomerId());
                        context.sendBroadcast(broadcastIntent);
                    }
                    else{
                        failed(responseJSON.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    failed("ERROR");
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
        if(message.equals("ERROR")){
            broadcastIntent.setAction(AppConstants.ACTION_SERVER_ERROR);
            broadcastIntent.putExtra("msg","Something went wrong");
        }
        else if(message.equals("DUPLICATE CONTACT NO.")){
            broadcastIntent.setAction(AppConstants.ACTION_DUPLICATE_CONTACT);

        }
        else if(message.equals("NO RECORD") || message.equals("NO RECORDS")){
            broadcastIntent.setAction(AppConstants.ACTION_NO_RECORDS);

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
