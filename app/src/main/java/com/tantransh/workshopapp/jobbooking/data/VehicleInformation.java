package com.tantransh.workshopapp.jobbooking.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class VehicleInformation implements Serializable {

    @Expose
    @SerializedName("vehicle_no")
    private String vehicleRegNo;

    @Expose
    @SerializedName("chassis_no")
    private String chassisNo;

    @Expose
    @SerializedName("engine_no")
    private String engineNo;

    @Expose
    @SerializedName("make_name")
    private String make;

    @Expose
    @SerializedName("make_id")
    private String makeId;

    @Expose
    @SerializedName("model")
    private String model;

    @Expose
    @SerializedName("model_year")
    private String modelYear;

    @Expose
    @SerializedName("insurance")
    private String insurance;

    @Expose
    @SerializedName("insurance_date")
    private String insuranceDate;

    @Expose
    @SerializedName("insurance_type")
    private String insuranceType;


    private static VehicleInformation instance;
//    private VehicleInformation(){}

    public String getMakeId() {
        return makeId;
    }

    public void setMakeId(String makeId) {
        this.makeId = makeId;
    }

    public static VehicleInformation getInstance (String vehicleRegNo, String chassisNo, String engineNo, String make, String makeId, String model, String modelYear, String insurance, String insuranceDate, String insuranceType) {
        if(instance == null) {
            instance = new VehicleInformation();
        }
            instance.vehicleRegNo = vehicleRegNo;
            instance.chassisNo = chassisNo;
            instance.engineNo = engineNo;
            instance.make = make;
            instance.model = model;

            instance.modelYear = modelYear;
            instance.insurance = insurance;
            instance.insuranceDate = insuranceDate;
            instance.insuranceType = insuranceType;
            instance.makeId = makeId;

        return instance;
    }

    public String getVehicleRegNo() {
        return vehicleRegNo;
    }

    public String getChassisNo() {
        return chassisNo;
    }

    public String getEngineNo() {
        return engineNo;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getInsuranceType() {
        return insuranceType;
    }

    public String getModelYear() {
        return modelYear;
    }

    public String getInsurance() {
        return insurance;
    }

    public String getInsuranceDate() {
        return insuranceDate;
    }
}
