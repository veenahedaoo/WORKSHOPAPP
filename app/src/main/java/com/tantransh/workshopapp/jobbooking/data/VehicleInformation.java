package com.tantransh.workshopapp.jobbooking.data;

import java.io.Serializable;

public class VehicleInformation implements Serializable {

    private String vehicleRegNo, chassisNo, engineNo, make, makeId, model, modelYear, insurance, insuranceDate, insuranceType;
    private static VehicleInformation instance;
    private VehicleInformation(){}

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
