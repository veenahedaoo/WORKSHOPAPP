package com.tantransh.workshopapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tantransh.workshopapp.appdata.AppPreferences;
import com.tantransh.workshopapp.home.DashboardActivity;
import com.tantransh.workshopapp.login.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    private AppPreferences ap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ap = AppPreferences.getInstance(this);
        //ap.removeUser();
        if(ap.isUserExists()){
            //dashboard of workshop app
            startActivity(new Intent(this, DashboardActivity.class));
        }
        else{
            // login to app
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}
