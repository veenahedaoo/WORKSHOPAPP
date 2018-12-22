package com.tantransh.workshopapp.services;

import com.android.volley.Response;
import com.tantransh.workshopapp.appdata.BookingDetails;
import com.tantransh.workshopapp.jobbooking.data.CustomerInformation;
import com.tantransh.workshopapp.jobbooking.data.VehicleInformation;

import org.json.JSONException;

public interface RequestListener {

    void login(String userId, String password, Response.Listener listener, Response.ErrorListener errorListener);
    void registerVehicle(VehicleInformation vehicleInformation, Response.Listener listener, Response.ErrorListener errorListener) throws JSONException;
    void searchVehicle(String vehicleId, Response.Listener listener, Response.ErrorListener errorListener);
    void addCustomer(CustomerInformation customerInformation, Response.Listener listener, Response.ErrorListener errorListener);
    void searchCustomerByContact(String contactNo, Response.Listener listener, Response.ErrorListener errorListener);
    void searchCustomerByVehicle(String contactNo, Response.Listener listener, Response.ErrorListener errorListener);
    void bookJob(BookingDetails bookingDetails, Response.Listener listener, Response.ErrorListener errorListener);
    void getJobList(String userId, String status, Response.Listener listener, Response.ErrorListener errorListener);
    void changeCustomer(String customerId, String vehicleId, Response.Listener listener, Response.ErrorListener errorListener);
    void updateCustomer(CustomerInformation customerInformation, Response.Listener listener, Response.ErrorListener errorListener);
    void updateVehicle(VehicleInformation vehicleInformation, Response.Listener listener, Response.ErrorListener errorListener);
    void getAllJobs(String userId, Response.Listener listener, Response.ErrorListener errorListener);
    void getState(Response.Listener listener, Response.ErrorListener errorListener);
    void getCities(String stateCode, Response.Listener listener, Response.ErrorListener errorListener);
    void getSpares(Response.Listener listener, Response.ErrorListener errorListener);
    void getServices(Response.Listener listener, Response.ErrorListener errorListener);
    void getMake(Response.Listener listener, Response.ErrorListener errorListener);
    void getCurrentJobs(Response.Listener listener, Response.ErrorListener errorListener);
    void getOpenJobs(Response.Listener listener, Response.ErrorListener errorListener);
    void getJobCard();
    void getJobInformation(String jobId);
    void getItemList(Response.Listener listener, Response.ErrorListener errorListener);

}
