package com.tantransh.workshopapp.openjobs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.tantransh.workshopapp.R;
import com.tantransh.workshopapp.appdata.AppConstants;
import com.tantransh.workshopapp.listadapters.OpenJobListAdapter;
import com.tantransh.workshopapp.requestadapter.JobListRequestAdapter;

public class OpenJobsActivity extends AppCompatActivity {

    private RecyclerView openJobsRV;
    private JobListRequestAdapter jobListRequestAdapter;
    private BroadcastReceiver receiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_jobs);
        initViews();
        initData();
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                switch (action){
                    case AppConstants.ACTION_OPEN_JOB_LIST_LOADED:
                        System.out.println("Open jobs are loaded");
                        OpenJoblist openJoblist = (OpenJoblist) intent.getSerializableExtra(AppConstants.EXTRA_OPEN_JOB_LIST);
                        OpenJobListAdapter adapter = OpenJobListAdapter.getInstance(context,openJoblist);
                        openJobsRV.setAdapter(adapter);
                        break;

                    case AppConstants.ACTION_NO_RECORDS:
                        break;

                    case AppConstants.ACTION_SERVER_ERROR:
                        break;
                }
            }
        };

        registerReceiver(receiver,new IntentFilter(AppConstants.ACTION_OPEN_JOB_LIST_LOADED));
        registerReceiver(receiver,new IntentFilter(AppConstants.ACTION_NO_RECORDS));
        registerReceiver(receiver,new IntentFilter(AppConstants.ACTION_SERVER_ERROR));

        jobListRequestAdapter.getOpenJobs();
    }

    private void initViews() {
        openJobsRV = findViewById(R.id.openJobsRV);
        openJobsRV.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initData(){
        jobListRequestAdapter = JobListRequestAdapter.getInstance(this);
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
        registerReceiver(receiver,new IntentFilter(AppConstants.ACTION_OPEN_JOB_LIST_LOADED));
        registerReceiver(receiver,new IntentFilter(AppConstants.ACTION_NO_RECORDS));
        registerReceiver(receiver,new IntentFilter(AppConstants.ACTION_SERVER_ERROR));
    }
}
