package com.tantransh.workshopapp.jobbooking.data;

import java.io.Serializable;
import java.util.ArrayList;

public class JobServiceList implements Serializable{

    private ArrayList<ServiceInfo> serviceList;

    private static JobServiceList instance;

    private JobServiceList (){
        serviceList = new ArrayList<>();
    }

    public static JobServiceList getInstance(){
        if(instance == null){
            instance = new JobServiceList();
        }

        return instance;
    }

    public ServiceInfo getService(int pos){
        return serviceList.get(pos);
    }

    public void addService(ServiceInfo serviceInfo){
        serviceList.add(serviceInfo);
    }

    public void remove(int pos){
        serviceList.remove(pos);
    }

    public void clear(){
        serviceList.clear();
    }

    public int getSize(){
        return serviceList.size();
    }

    public ServiceInfo getService(String serviceId){
        for(int i = 0; i<getSize(); i++){
            if(getService(i).getServiceId().equals(serviceId)){
                return getService(i);
            }
        }
        return null;
    }

    public boolean searchService(String serviceId){
        return getService(serviceId) != null;

    }

    public boolean comparePrice(String serviceId, String price){
        return getService(serviceId).getAmount().equals(price);
    }

    public void updateAmount(String serviceId, String amount){
        getService(serviceId).setAmount(amount);
    }


}
