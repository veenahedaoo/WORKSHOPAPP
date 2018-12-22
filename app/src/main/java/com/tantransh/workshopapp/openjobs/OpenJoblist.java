package com.tantransh.workshopapp.openjobs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OpenJoblist implements Serializable{
    private List<OpenJobInfo> openJobInfoList;

    private static OpenJoblist instance;

    private OpenJoblist(){
        openJobInfoList = new ArrayList<>();
    }

    public static OpenJoblist getInstance(){
        if(instance==null){
            instance = new OpenJoblist();
        }
        return instance;
    }

    public void setOpenJob(OpenJobInfo openJob){
        openJobInfoList.add(openJob);
    }

    public OpenJobInfo getOpenJob(int pos){
        return openJobInfoList.get(pos);
    }

    public int getSize(){
        return openJobInfoList.size();
    }

    public void removeAll(){
        openJobInfoList.clear();
    }

    public void remove(int pos){
        openJobInfoList.remove(pos);
    }


}
