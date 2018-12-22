package com.tantransh.workshopapp.jobbooking.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SparesList implements Serializable{
    private List<SpareInfo> spareInfoList;
    private static SparesList instance;

    public static SparesList getInstance() {
        if(instance == null)
            instance = new SparesList();
        return instance;
    }

    private SparesList(){
        spareInfoList = new ArrayList<>();
    }

    public void setSpare(SpareInfo spareInfo){
        this.spareInfoList.add(spareInfo);
    }

    public SpareInfo getSpare(int pos){
        return  this.spareInfoList.get(pos);
    }

    public SpareInfo getSpare(String spareId){
        for(int i = 0; i<getSize(); i++){
            if(getSpare(i).getSpareId().equals(spareId)){
                return getSpare(i);
            }
        }
        return null;
    }

    public int getSize(){
        return this.spareInfoList.size();
    }

    public void clear(){
        spareInfoList.clear();
    }
}