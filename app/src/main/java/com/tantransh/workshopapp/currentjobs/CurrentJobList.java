package com.tantransh.workshopapp.currentjobs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CurrentJobList implements Serializable{
    private List<CurrentJobInfo> currentJobInfoList;

    private static CurrentJobList instance;

    private CurrentJobList(){
        currentJobInfoList = new ArrayList<>();
    };

    public static CurrentJobList getInstance(){
        if(instance==null)
            instance = new CurrentJobList();
        return instance;
    }

    public void addJob(CurrentJobInfo jobInfo){
        currentJobInfoList.add(jobInfo);
    }

    public CurrentJobInfo getJob(int pos){
        return currentJobInfoList.get(pos);
    }

    public int getSize(){
        return currentJobInfoList.size();
    }


    public void remove(){
        currentJobInfoList.clear();
    }

}
