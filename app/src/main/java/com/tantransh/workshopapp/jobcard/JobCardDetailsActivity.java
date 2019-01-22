package com.tantransh.workshopapp.jobcard;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tantransh.workshopapp.R;
import com.tantransh.workshopapp.appdata.JobInformation;
import com.tantransh.workshopapp.requestadapter.JobListRequestAdapter;
import com.tantransh.workshopapp.services.ServiceDispatcher;

public class JobCardDetailsActivity extends AppCompatActivity {

    private ServiceDispatcher serviceDispatcher;
    private Intent intent;
    private Gson gson;
    private LinearLayout pdLLO;
    private BroadcastReceiver receiver;
    private JobListRequestAdapter adapter;
    private TextView customerNameTV, customerAddressTV, customerContactTV, cityOinTV, jobCardNoTV, vehicleNoTV, chassisNoTV, engineNOTV, makeTV, modelTV, modelYearTV, kmReadingTV, bookingDateTV, lastServiceDateTV, repNameTV, repContactTV, attendedByTV, jobStatusTV, remarkTV;
    private LinearLayout sparesLLO, servicesLLO, jonConcerLLO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_card_details);
        getInformation();
        initComponents();

    }

    private void initJobInformation(JobInformation jobInformation){
        System.out.println(jobInformation.toString());
    }

    private void getInformation(){
        serviceDispatcher = ServiceDispatcher.getInstance(getApplicationContext());
        intent = getIntent();
        gson = new Gson();
        pdLLO = findViewById(R.id.pdLLO);
        pdLLO.setVisibility(View.VISIBLE);
        adapter = JobListRequestAdapter.getInstance(this);
        adapter.getJobInformation(intent.getStringExtra("booking_id"));
    }

    private void initComponents(){
        customerNameTV = findViewById(R.id.customerNameTV);
        customerContactTV = findViewById(R.id.customerContactTV);
        customerAddressTV = findViewById(R.id.addressTV);
        cityOinTV = findViewById(R.id.cityandPinTV);
        jobCardNoTV = findViewById(R.id.jobcardNumberTV);
        vehicleNoTV = findViewById(R.id.vehicleRegsTV);
        chassisNoTV = findViewById(R.id.chassisNumberTV);
        engineNOTV = findViewById(R.id.engineNumberTV);
        makeTV = findViewById(R.id.makeTV);
        modelTV = findViewById(R.id.modelTV);
        modelYearTV = findViewById(R.id.modelYearTV);
        kmReadingTV = findViewById(R.id.mileageTV);
        bookingDateTV = findViewById(R.id.bookingDateTV);
        lastServiceDateTV = findViewById(R.id.previousBookingDateTV);
        repNameTV = findViewById(R.id.customerRepresentativeNameTV);
        repContactTV = findViewById(R.id.representativeContactTV);
        attendedByTV = findViewById(R.id.attendedByTV);
        jobStatusTV = findViewById(R.id.jobStatusTV);

    }


}
