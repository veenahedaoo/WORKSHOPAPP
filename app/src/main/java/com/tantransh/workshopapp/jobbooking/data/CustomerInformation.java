package com.tantransh.workshopapp.jobbooking.data;

import java.io.Serializable;

public class CustomerInformation implements Serializable{
    private String customerId, firstName, lastName, contact, alternateContact, email, plotNo, area, landmark, stateId, stateName, city, postalCode, gstin;
    private static CustomerInformation instance;
    private CustomerInformation() {

    }

    public static CustomerInformation getInstance(){
        return instance;

    }

    public String getAlternateContact() {
        return alternateContact;
    }

    public void setAlternateContact(String alternateContact) {
        this.alternateContact = alternateContact;
    }

    public static CustomerInformation getInstance(String customerId, String firstName, String lastName, String contact, String alternateContact, String email, String plotNo, String area, String landmark, String stateId, String stateName, String city, String postalCode, String gstin) {
        if(instance == null)
            instance = new CustomerInformation();
        instance.customerId = customerId;
        instance.firstName = firstName;
        instance.lastName = lastName;
        instance.contact = contact;
        instance.email = email;
        instance.plotNo = plotNo;
        instance.area = area;
        instance.landmark = landmark;
        instance.stateId = stateId;
        instance.stateName = stateName;
        instance.city = city;
        instance.postalCode = postalCode;
        instance.gstin = gstin;
        instance.alternateContact = alternateContact;

        return instance;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPlotNo() {
        return plotNo;
    }

    public void setPlotNo(String plotNo) {
        this.plotNo = plotNo;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getGstin() {
        return gstin;
    }

    public void setGstin(String gstin) {
        this.gstin = gstin;
    }
}
