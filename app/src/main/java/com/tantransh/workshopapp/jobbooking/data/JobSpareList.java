package com.tantransh.workshopapp.jobbooking.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class JobSpareList implements Serializable{

    private JobSpareList(){
        spareInfoList = new ArrayList<>();
    }
    private static JobSpareList instance;

    public static JobSpareList getInstance(){
        if(instance == null)
            instance = new JobSpareList();
        return instance;
    }

    private List<SpareInfo> spareInfoList;

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

    public boolean searchSpare(String spareId){
        return getSpare(spareId) != null;
    }

    public boolean matchPrice(String spareId, String amount){
        return getSpare(spareId).getAmount().equals(amount);

    }

    public void updateQty(String spareId, String qty){
        for(int i = 0; i<getSize(); i++){
            if(getSpare(i).getSpareId().equals(spareId)){
                getSpare(i).setQty(qty);
            }
        }
    }

    public void updatePrice(String spareId, String amount){
        for(int i = 0; i<getSize(); i++){
            if(getSpare(i).getSpareId().equals(spareId)){
                getSpare(i).setAmount(amount);
            }
        }
    }

    public void updatePriceAndQty(String spareId, String amount, String qty){
        for(int i = 0; i<getSize(); i++){
            if(getSpare(i).getSpareId().equals(spareId)){
                getSpare(i).setAmount(amount);
                getSpare(i).setQty(qty);

            }
        }
    }

    public void removeSpare(String spareId){
        for(int i = 0; i<getSize(); i++){
            if(getSpare(i).getSpareId().equals(spareId)){
                removeSpare(i);

            }
        }
    }

    public void removeSpare(int pos){
        spareInfoList.remove(pos);
    }
}
