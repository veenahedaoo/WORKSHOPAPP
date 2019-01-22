package com.tantransh.workshopapp.services;

import com.android.volley.Response;
import com.tantransh.workshopapp.appdata.BookingDetails;
import com.tantransh.workshopapp.jobbooking.data.CustomerInformation;
import com.tantransh.workshopapp.jobbooking.data.VehicleInformation;

import org.json.JSONException;
import org.json.JSONObject;

public interface RequestListener {

    void login(String userId, String password, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);
    void registerVehicle(VehicleInformation vehicleInformation, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) throws JSONException;
    void searchVehicle(String vehicleId, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);
    void addCustomer(CustomerInformation customerInformation, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);
    void searchCustomerByContact(String contactNo, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);
    void searchCustomerByVehicle(String contactNo, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);
    void bookJob(BookingDetails bookingDetails, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);
    void updateCustomer(CustomerInformation customerInformation, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);
    void updateVehicle(VehicleInformation vehicleInformation, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);
    void getAllJobs(String userId, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);
    void getState(Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);
    void getMake(Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);
    void getCurrentJobs(Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);
    void getOpenJobs(Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);
    void getItemList(Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);
    void getJobInformation(String jobId, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);
    void getJobList(String userId, String status, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener);

}
