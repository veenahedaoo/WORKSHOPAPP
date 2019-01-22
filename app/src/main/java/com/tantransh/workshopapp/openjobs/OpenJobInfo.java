package com.tantransh.workshopapp.openjobs;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OpenJobInfo implements Serializable {

    @Expose
    @SerializedName("vehicle_registration_number")
    private String vehicleNo;
    @Expose
    @SerializedName("make")
    private String make;
    @Expose
    @SerializedName("model")
    private String model;
    @Expose
    @SerializedName("booking_status")
    private String bookingStatus;
    @Expose
    @SerializedName("booking_date")
    private String bookingDate;
    @Expose
    @SerializedName("booked_by")
    private String bookedBy;
    @Expose
    @SerializedName("booking_id")
    private String bookingId;
    @Expose
    @SerializedName("customer_name")
    private String customerName;
    @Expose
    @SerializedName("customer_contact")
    private String customerContact;
    @Expose
    @SerializedName("rep_fname")
    private String repFirstName;

    @Expose
    @SerializedName("rep_lname")
    private String repLastName;

    @Expose
    @SerializedName("rep_number")
    private String repContact;

    @Expose
    @SerializedName("user_name")
    private String username;

    public String getVehicleNo() {
        return vehicleNo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public String getBookedBy() {
        return bookedBy;
    }

    public void setBookedBy(String bookedBy) {
        this.bookedBy = bookedBy;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerContact() {
        return customerContact;
    }

    public void setCustomerContact(String customerContact) {
        this.customerContact = customerContact;
    }

    public String getRepContact() {
        return repContact;
    }

    public void setRepContact(String repContact) {
        this.repContact = repContact;
    }

    public String getRepLastName() {
        return repLastName;
    }

    public void setRepLastName(String repLastName) {
        this.repLastName = repLastName;
    }

    public String getRepFirstName() {
        return repFirstName;
    }

    public void setRepFirstName(String repFirstName) {
        this.repFirstName = repFirstName;
    }
}
