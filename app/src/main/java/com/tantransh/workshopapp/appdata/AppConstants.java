package com.tantransh.workshopapp.appdata;

/**
 * Created by android on 26/10/17.
 */

public class AppConstants {

    private AppConstants(){}

    /**
     * static constants for storing shared preferences data
     */

    public static final String SP_NAME = "WORKSHOP";
    public static final String SP_USER_ID = "USER_ID";
    public static final String SP_PASSWRD = "PASSWORD";
    public static final String SP_AUTH_TOKEN = "AUTH_TOKEN";

    /**
     * extra intents name
     */

    public static final String EXTRA_USER_ID = "USER_ID";
    public static final String EXTRA_PASSWORD = "PASSWORD";
    public static final String EXTRA_VEHICLE_INFO = "VEHICLE_INFO";
    public static final String EXTRA_MAKE_LIST = "MAKE_LIST";
    public static final String EXTRA_STATE_LIST = "STATE_LIST";
    public static final String EXTRA_CUTOMER_INFORMATION = "[CUSTOMER_INFORMATION";
    public static final String EXTRA_SPARE_LIST = "SPARE_LIST";
    public static final String EXTRA_SERVICE_LIST = "SERVICE_LIST";
    public static final String EXTRA_CURRENT_JOB_LIST = "CURRENT_JOB_LIST";
    public static final String EXTRA_OPEN_JOB_LIST = "OPEN_JOB_LIST";
    public static final String EXTRA_JOB_INFO = "JOB_INFO";
    public static final String EXTRA_JOB_CARD = "JOB_CARD";

    /**
     * actions
     */

    public static final String ACTION_LOGIN_SUCCESS = "LOGIN_SUCCESS";
    public static final String ACTION_LOGIN_FAILED = "LOGIN_FAILED";
    public static final String ACTION_LOGIN_ERROR = "LOGIN_ERROR";
    public static final String ACTION_CONNECTION_ERROR = "CONNECTION_ERROR";
    public static final String ACTION_SERVER_ERROR = "SERVER_ERROR";
    public static final String ACTION_ADD_VEHICLE_SUCCESS = "ADD_VEHICLE_SUCCESS";
    public static final String ACTION_ADD_VEHICLE_FAILED = "ADD_VEHICLE_FAILED";
    public static final String ACTION_NO_RECORDS = "NO_RECORDS";
    public static final String ACTION_LIST_LOADED = "LIST_LOADED";
    public final static String ACTION_VEHICLE_INFO_LOADED = "VEHICLE_INFO_LOADED";
    public final static String ACTION_INVALID_VEHICLE_NO = "INVALID_VEHICLE_NO";
    public final static String ACTION_VEHICLE_INFO_UPDATED = "VEHICLE_INFO_UPDATED";
    public final static String ACTION_STATE_LIST_LOADED = "STATE_LIST_LOADED";
    public final static String ACTION_CUSTOMER_ADDED = "CUSTOMER_ADDED";
    public final static String ACTION_CUSTOMER_FOUND = "CUSTOMER_FOUND";
    public final static String ACTION_CUSTOMER_UPDATED = "CUSTOMER_UPDATED";
    public final static String ACTION_CUSTOMER_CHANGED = "CUSTOMER_CHANGED";
    public final static String ACTION_DUPLICATE_CONTACT = "DUPLICATE_CONTACT";
    public final static String ACTION_SPARE_LIST_LOADED = "SPARE_LIST_LOADED";
    public final static String ACTION_SERVICE_LIST_LOADED = "SERVICE_LIST_ADDED";
    public final static String ACTION_PICTURE_UPLOADED = "PICTURE_UPLOADED";
    public final static String ACTION_CURRENT_JOB_LIST_LOADED = "CURRENT_JOB_LIST_LOADED";
    public final static String ACTION_OPEN_JOB_LIST_LOADED = "OPEN_JOB_LIST_LOADED";
    public final static String ACTION_JOB_CARD_LOADED = "JOB_CARD_LOADED";
    public final static String ACTION_JOB_INFO_LOADED = "JOB_INFO_LOADED";


    public static int getProgress(String bookingStatus){
        switch (bookingStatus){
            case "1":
                return 20;
            case "2":
                return 40;
            case "3":
                return 80;
            case "4":
                return 100;
            case "5":
                return 60;
        }
        return 0;
    }

    public static String getProgressMessage(String bookingStatus){
        switch (bookingStatus){
            case "1":
                return "BOOKED";
            case "2":
                return "OPEN";
            case "3":
                return "COMPLETED";
            case "4":
                return "RELEASED";
            case "5":
                return "PENDING";
        }
        return null;
    }
}
