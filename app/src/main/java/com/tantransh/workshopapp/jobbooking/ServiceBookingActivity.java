package com.tantransh.workshopapp.jobbooking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.tantransh.workshopapp.R;
import com.tantransh.workshopapp.currentjobs.CurrentJobListActivity;
import com.tantransh.workshopapp.home.DashboardActivity;

public class ServiceBookingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_booking);
    }

    public void goToHome(View view){
        startActivity(new Intent(this, DashboardActivity.class));
    }

    public void goToCurrentJobs(View view){
        startActivity(new Intent(this, CurrentJobListActivity.class));
    }
}
