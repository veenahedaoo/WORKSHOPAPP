package com.tantransh.workshopapp.appdata

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.tantransh.workshopapp.jobbooking.data.ServiceList
import com.tantransh.workshopapp.jobbooking.data.SparesList

class JobInformation {
    @Expose
    @SerializedName("booking_id")
    lateinit var bookingId : String
    @Expose
    @SerializedName("booking_date")
    lateinit var bookingDate : String
    @Expose
    @SerializedName("rep_name")
    lateinit var repName : String
    @Expose
    @SerializedName("rep_contact")
    lateinit var repContact : String
    @Expose
    @SerializedName("vehicle_reg_no")
    lateinit var vehicleRegNo : String
    @Expose
    @SerializedName("service_done_date")
    lateinit var serviceDoneDate : String
    @Expose
    @SerializedName("vehicle_release_date")
    lateinit var vehicleReleaseDate : String
    @Expose
    @SerializedName("booking_status")
    lateinit var bookingStatus : String
    @Expose
    @SerializedName("customer_id")
    lateinit var customerId : String
    @Expose
    @SerializedName("booked_username")
    lateinit var bookedUserName : String
    @Expose
    @SerializedName("customer_name")
    lateinit var customerName : String
    @Expose
    @SerializedName("chassis_number")
    lateinit var chassisNo : String
    @Expose
    @SerializedName("engine_number")
    lateinit var engineNo : String
    @Expose
    @SerializedName("make_id")
    lateinit var makeId : String
    @Expose
    @SerializedName("make_name")
    lateinit var makeName : String
    @Expose
    @SerializedName("model")
    lateinit var model : String
    @Expose
    @SerializedName("model_year")
    lateinit var modelYear : String
    @Expose
    @SerializedName("insurance")
    lateinit var insurance : String
    @Expose
    @SerializedName("insurance_date")
    lateinit var insuranceDate : String
    @Expose
    @SerializedName("km_reading")
    lateinit var kmReading : String
    @Expose
    @SerializedName("last_service_date")
    lateinit var lastServiceDate : String
    @Expose
    @SerializedName("job_concern")
    lateinit var jobConcernList : JobConcernList
    @Expose
    @SerializedName("spares")
    lateinit var spareList : SparesList
    @Expose
    @SerializedName("services")
    lateinit var serviceList : ServiceList
    @Expose
    @SerializedName("remark")
    lateinit var remark : String
}