package com.tantransh.workshopapp.appdata;

import com.tantransh.workshopapp.jobbooking.data.JobServiceList;
import com.tantransh.workshopapp.jobbooking.data.JobSpareList;

public class BookingDetails {
    private String bookingId;
    private String vehicleRegNo;

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    private String customerId;
    private String kilometers;
    private String repFName, repLName, repContact;
    private JobSpareList jobSpareList;
    private JobServiceList jobServiceList;
    private String bookingDate;
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

    public JobSpareList getJobSpareList() {
        return jobSpareList;
    }

    public void setJobSpareList(JobSpareList jobSpareList) {
        this.jobSpareList = jobSpareList;
    }

    public JobServiceList getJobServiceList() {
        return jobServiceList;
    }

    public void setJobServiceList(JobServiceList jobServiceList) {
        this.jobServiceList = jobServiceList;
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
}
