package com.tantransh.workshopapp.currentjobs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.tantransh.workshopapp.R;
import com.tantransh.workshopapp.appdata.AppConstants;
import com.tantransh.workshopapp.listadapters.CurrentJobListAdapter;
import com.tantransh.workshopapp.requestadapter.JobListRequestAdapter;

public class CurrentJobListActivity extends AppCompatActivity {

    private JobListRequestAdapter jobListRequestAdapter;
    private BroadcastReceiver receiver;
    private CurrentJobListAdapter currentJobListAdapter;
    private RecyclerView currentJobsRV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_job_list);
        initViews();

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                switch (action){
                    case AppConstants.ACTION_CURRENT_JOB_LIST_LOADED:
                        CurrentJobList currentJobList = (CurrentJobList) intent.getSerializableExtra(AppConstants.EXTRA_CURRENT_JOB_LIST);
                        System.out.println("current jobs after receiving broadcasts : "+currentJobList.getSize());
                        currentJobListAdapter  = CurrentJobListAdapter.getInstance(context,currentJobList);
                        currentJobsRV.setAdapter(currentJobListAdapter);
                        break;

                    case AppConstants.ACTION_SERVER_ERROR:
                        String message = intent.getStringExtra("msg");
                        break;

                    case AppConstants.ACTION_NO_RECORDS:

                }
            }
        };


        registerReceiver(receiver, new IntentFilter(AppConstants.ACTION_CURRENT_JOB_LIST_LOADED));
        registerReceiver(receiver, new IntentFilter(AppConstants.ACTION_NO_RECORDS));
        registerReceiver(receiver, new IntentFilter(AppConstants.ACTION_SERVER_ERROR));
        initData();

    }

    private void initViews() {
        currentJobsRV = findViewById(R.id.currentJobsRV);
        currentJobsRV.setLayoutManager(new LinearLayoutManager(this));

    }

    private void initData() {
        System.out.println("Fetch current jobs list");
        jobListRequestAdapter = JobListRequestAdapter.getInstance(this);
        jobListRequestAdapter.getCurrentJobs();
    }

    public void back(View view){
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter(AppConstants.ACTION_CURRENT_JOB_LIST_LOADED));
        registerReceiver(receiver, new IntentFilter(AppConstants.ACTION_NO_RECORDS));
        registerReceiver(receiver, new IntentFilter(AppConstants.ACTION_SERVER_ERROR));
    }
}
