package com.tantransh.workshopapp.appdata;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by android on 26/10/17.
 */

public class AppPreferences {

    private SharedPreferences sp;
    @SuppressLint("StaticFieldLeak")
    private static AppPreferences instance;

    private AppPreferences(Context context){
        this.sp = context.getSharedPreferences(AppConstants.SP_NAME,Context.MODE_PRIVATE);
    }

    public static AppPreferences getInstance(Context context){
        if(instance == null){
            instance = new AppPreferences(context);
        }
        return instance;
    }

    public void addUser(String loginId, String authToken, String userType, String userId, String password){
        getEditor().putString(AppConstants.SP_USER_ID,userId).apply();
        getEditor().putString(AppConstants.SP_LOGIN_ID,loginId).apply();
        getEditor().putString(AppConstants.SP_AUTH_TOKEN,authToken).apply();
        getEditor().putString(AppConstants.SP_USER_TYPE,userType).apply();
        getEditor().putString(AppConstants.SP_PASSWRD,password).apply();
    }

    public void addToken(String token){
        getEditor().putString(AppConstants.SP_AUTH_TOKEN,token).apply();
    }

    public void removeUser(){
        getEditor().clear().apply();
    }

    private SharedPreferences.Editor getEditor(){
        return sp.edit();
    }

    public boolean isUserExists(){
        return sp.contains(AppConstants.SP_USER_ID) && sp.contains(AppConstants.SP_PASSWRD);

    }

    public String getUserId(){
        return sp.getString(AppConstants.SP_USER_ID,null);
    }

}
