package com.tantransh.workshopapp.appdata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookingDetails {
    @Expose
    @SerializedName("booking_id")
    private String bookingId;

    @Expose
    @SerializedName("vehicle_reg_no")
    private String vehicleRegNo;

    @Expose
    @SerializedName("customer_id")
    private String customerId;

    @Expose
    @SerializedName("kilometers")
    private String kilometers;

    @Expose
    @SerializedName("rep_fname")
    private String repFName;

    @Expose
    @SerializedName("rep_lname")
    private String repLName;

    @Expose
    @SerializedName("rep_contact")
    private String repContact;

    @Expose
    @SerializedName("booking_date")
    private String bookingDate;

    @Expose
    @SerializedName("job_concern_list")
    private JobConcernList jobConcernList;

    private static BookingDetails instance;

    private BookingDetails(){}

    public String getRepFName() {
        return repFName;
    }

    public void setRepFName(String repFName) {
        this.repFName = repFName;
    }

    public String getRepLName() {
        return repLName;
    }

    public void setRepLName(String repLName) {
        this.repLName = repLName;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getRepContact() {
        return repContact;

    }

    public void setRepContact(String repContact) {
        this.repContact = repContact;
    }

    public String getKilometers() {

        return kilometers;
    }

    public void setKilometers(String kilometers) {
        this.kilometers = kilometers;
    }

    public static BookingDetails getInstance(){

        if(instance==null)
            instance = new BookingDetails();

        return instance;
    }

    public String getVehicleRegNo() {
        return vehicleRegNo;
    }

    public void setVehicleRegNo(String vehicleRegNo) {
        this.vehicleRegNo = vehicleRegNo;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public JobConcernList getJobConcernList() {
        return jobConcernList;
    }

    public void setJobConcernList(JobConcernList jobConcernList) {
        this.jobConcernList = jobConcernList;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }
}
