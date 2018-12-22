package com.tantransh.workshopapp.jobbooking.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MakeList implements Serializable{

    private List<MakeInfo> makesList;

    private static MakeList instance;

    private MakeList(){
        makesList = new ArrayList<>();
    }

    public static MakeList getInstance(){
        if(instance == null){
            instance = new MakeList();
        }

        return instance;
    }

    public void clearList(){
        this.makesList.clear();
    }
    public void addItem(MakeInfo item){
        this.makesList.add(item);
    }

    public MakeInfo getItem(int pos){
        return this.makesList.get(pos);
    }

    public int getSize(){
        return this.makesList.size();
    }

    public String getMakeName(String makeId){
        for(int i = 0; i<getSize(); i++){
            if(getItem(i).getMake_id().equals(makeId)){
                return getItem(i).getMake_name();
            }
        }
        return null;
    }
}
