package com.tantransh.workshopapp.appdata;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by android on 18/1/17.
 */

public class Validator {

    public static boolean isValidString(String... str){
        boolean result = false;
        for(int i=0; i<str.length;i++){
            if(str[i] == null || str[i].isEmpty() || str[i].equals("null")){
                System.out.println("false");
                return   false;
            }
        }
        return true;
    }

    public static double getFormattedPrice(double price){
        DecimalFormat dtime = new DecimalFormat("#.##");
        double formatedPrice= Double.valueOf(dtime.format(price));
        return formatedPrice;
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return (cm.getActiveNetworkInfo() != null) && cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    public static  String getFormattedtDate(String str){

        if(!Validator.isValidString(str)){
            return null;
        }
        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            Date date = null;
            date = format.parse(str);
            DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
            String formatedDate = df.format(date);
            System.out.println(formatedDate); // Sat Jan 02 00:00:00 GMT 2010
            return formatedDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getSqlDate(String str){
        try {
            DateFormat format = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
            Date date = null;
            date = format.parse(str);
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String formatedDate = df.format(date);
            System.out.println(formatedDate); // Sat Jan 02 00:00:00 GMT 2010
            return formatedDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static  String getFormattedtDateTime(String str){

        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            Date date = null;
            date = format.parse(str);
            DateFormat df = new SimpleDateFormat("dd-MMM-yyyy hh:mm a");
            String formatedDate = df.format(date);
            System.out.println(formatedDate); // Sat Jan 02 00:00:00 GMT 2010
            return formatedDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static String getCurrentDateTime(){
        Calendar cal = GregorianCalendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(cal.getTime());
        return date;
    }

    public static String getCurrentDate(){
        Calendar cal = GregorianCalendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(cal.getTime());
        return date;
    }

    public static String getStatus(String status){
        if(status.equals("0")){
            return  "Waiting for recommendation";
        }
        else if(status.equals("1")){
            return  "not recommended";
        }
        else if(status.equals("2")){
            return "recommended";
        }
        else if(status.equals("3")){
            return "Waiting for processing request";
        }
        else if(status.equals("4")){
            return "processed successfully";
        }
        else if(status.equals("5")){
            return "closed ";
        }
        return  "Waiting for recommendation";
    }

    public static boolean isValidPhone(String phone){

        return phone.matches("^(?=(?:[7-9]){1})(?=[0-9]{10}).*");
    }

    public static boolean isValidVehicleNo(String vehicleNo){
        return vehicleNo.matches("^[A-Z]{2}[0-9]{1,2}[A-Z]{1,2}[0-9]{4}$");
    }

    public static boolean isValidName(String name){
        return name.matches("/^[A-Za-z]+$/");
    }

    public static String getErrorMessage(VolleyError volleyError) {
        String message = null;
        if (volleyError instanceof NetworkError) {
            message = "Cannot connect to Internet...Please check your connection!";
        } else if (volleyError instanceof ServerError) {
            message = "Something went wrong";
        } else if (volleyError instanceof AuthFailureError) {
            message = "Cannot connect to Internet...Please check your connection!";
        } else if (volleyError instanceof ParseError) {
            message = "Something went wrong";
        } else if (volleyError instanceof NoConnectionError) {
            message = "Cannot connect to Internet...Please check your connection!";
        } else if (volleyError instanceof TimeoutError) {
            message = "Connection TimeOut! Please check your internet connection.";
        }

        return message;
    }




}
