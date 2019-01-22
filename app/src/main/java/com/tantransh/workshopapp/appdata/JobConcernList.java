package com.tantransh.workshopapp.appdata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class JobConcernList {

    @Expose
    @SerializedName("job_concern_item")
    private List<JobConcernItem> jobConcernItems;

    private JobConcernList(){
        jobConcernItems = new ArrayList<>();
    }

    public static JobConcernList getInstance(){
        return new JobConcernList();
    }

    public void addItem(JobConcernItem item){
        jobConcernItems.add(item);
    }

    public JobConcernItem getItem(int pos){
        return jobConcernItems.get(pos);
    }

    public int getSize(){
        return jobConcernItems.size();
    }

    public void clearAll(){
        jobConcernItems.clear();
    }

    public void removeItem(int pos){
        jobConcernItems.remove(pos);
    }

    public String getItemId(String item_name){
        for(int i =0; i<getSize(); i++){
            if(getItem(i).itemName.equalsIgnoreCase(item_name)){
                return getItem(i).itemName;
            }
        }
        return null;
    }

    public boolean isItemExists(String itemId){
        for(int i = 0 ; i<getSize(); i++){
            if(getItem(i).itemId.equalsIgnoreCase(itemId)){
                return true;
            }
        }
        return false;
    }


}
