package com.tantransh.workshopapp.jobbooking.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ServiceList implements Serializable{
    private static ServiceList instance;
    private List<ServiceInfo> serviceInfoList;

    private ServiceList(){
        serviceInfoList = new ArrayList<>();
    }

    public static ServiceList getInstance(){
        if(instance == null)
            instance = new ServiceList();

        return instance;
    }

    public void addService(ServiceInfo serviceInfo){
        serviceInfoList.add(serviceInfo);
    }

    public ServiceInfo getService(int pos){
        return serviceInfoList.get(pos);
    }

    public int getSize(){
        return serviceInfoList.size();
    }

    public void removeService(int pos){
        serviceInfoList.remove(pos);
    }

    public void clear(){
        serviceInfoList.clear();
    }

}
