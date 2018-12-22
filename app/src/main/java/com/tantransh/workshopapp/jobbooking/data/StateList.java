package com.tantransh.workshopapp.jobbooking.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StateList implements Serializable {

    private static StateList instance;
    private List<StateInfo> statesList;

    private StateList(){
        statesList = new ArrayList<>();
    }

    public static StateList getInstance(){
        if(instance == null){
            instance = new StateList();
        }

        return instance;
    }

    public void addState(StateInfo stateInfo){
        this.statesList.add(stateInfo);
    }

    public StateInfo getState(int pos){
        return this.statesList.get(pos);
    }

    public StateInfo getState(String stateId){
        for(int i = 0; i<statesList.size(); i++){
            if(statesList.get(i).getState_id().equals(stateId)){
                return statesList.get(i);
            }
        }
        return null;
    }

    public String getStateId(String stateName){
        for(int  i =0; i<getSize(); i++){
            if(statesList.get(i).getState_name().equals(stateName)){
                return statesList.get(i).getState_id();
            }
        }
        return null;
    }

    public int getSize(){
        return this.statesList.size();
    }

    public String getStateName(String stateId){
        for(int i = 0; i<getSize(); i++){
            if(getState(i).getState_id().equals(stateId)){
                return getState(i).getState_name();
            }
        }
        return null;
    }

}
