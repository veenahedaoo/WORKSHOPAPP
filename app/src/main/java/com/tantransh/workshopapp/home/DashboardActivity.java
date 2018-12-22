package com.tantransh.workshopapp.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.tantransh.workshopapp.R;
import com.tantransh.workshopapp.currentjobs.CurrentJobListActivity;
import com.tantransh.workshopapp.jobbooking.VehicleInformationActivity;
import com.tantransh.workshopapp.openjobs.OpenJobsActivity;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
    }

    public void goToJobBooking(View view){
        startActivity(new Intent(this, VehicleInformationActivity.class));
    }

    public void getCurrentJobs(View view){
        startActivity(new Intent(this, CurrentJobListActivity.class));
    }

    public void getOpenJobs(View view){
        startActivity(new Intent(this, OpenJobsActivity.class
        ));
    }


}
