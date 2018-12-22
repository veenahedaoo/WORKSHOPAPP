package com.tantransh.workshopapp.requestlistener;

import com.android.volley.VolleyError;
import com.tantransh.workshopapp.jobbooking.data.VehicleInformation;

public interface VehicleRequestListener {

    void registerVehicle(VehicleInformation vehicleInformation);
    void searchVehicle(String vehicleNo);
    void updateVehicle(VehicleInformation vehicleInformation);
    void success(String message);
    void failed(String msg);
    void error(VolleyError error);
}
