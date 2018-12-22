package com.tantransh.workshopapp.requestlistener;

import com.android.volley.VolleyError;

public interface JobListRequestLister {

    void getCurrentJobs();
    void getOpenJobs();
    void getJobCard();
    void getJobInformation(String jobId);
    void failed();
    void error(VolleyError error);
}
