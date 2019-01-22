package com.tantransh.workshopapp.currentjobs;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CurrentJobInfo implements Serializable{

    @Expose
    @SerializedName("booking_id")
    private String bookingId;
    @Expose
    @SerializedName("kilometers")
    private String kilometers;
    @Expose
    @SerializedName("vehicle_registration_number")
    private String vehicleNo;
    @Expose
    @SerializedName("booking_status")
    private String bookingStatus;
    @Expose
    @SerializedName("model")
    private String model;
    @Expose
    @SerializedName("make")
    private String makeName;
    @Expose
    @SerializedName("customer_id")
    private String customerId;
    @Expose
    @SerializedName("rep_fname")
    private String firstName;
    @Expose
    @SerializedName("rep_lname")
    private String lastName;
    @Expose
    @SerializedName("rep_number")
    private String contact;

    @Expose
    @SerializedName("customer_name")
    private String customerName;

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getKilometers() {
        return kilometers;
    }

    public void setKilometers(String kilometers) {
        this.kilometers = kilometers;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMakeName() {
        return makeName;
    }

    public void setMakeName(String makeName) {
        this.makeName = makeName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
