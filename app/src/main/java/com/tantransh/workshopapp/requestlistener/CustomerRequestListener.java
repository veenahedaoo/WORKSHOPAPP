package com.tantransh.workshopapp.requestlistener;

import com.android.volley.VolleyError;
import com.tantransh.workshopapp.jobbooking.data.CustomerInformation;

public interface CustomerRequestListener {

    void addCustomer(CustomerInformation customerInformation);
    void searchCustomerByContact(String phoneNo);
    void searchCustomerByVehicle(String vehicleNo);
    void updateCustomer(CustomerInformation customerInformation);
    void changeCustomer(CustomerInformation customerInformation);
    void changeCustomer(String customerId);
    void success(String message);
    void failed(String message);
    void error(VolleyError error);

}
