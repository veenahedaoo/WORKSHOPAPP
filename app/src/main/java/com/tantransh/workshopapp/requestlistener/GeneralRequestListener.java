package com.tantransh.workshopapp.requestlistener;

public interface GeneralRequestListener {
    void getStateList();
    void getCityList(String stateCode);
    void getMakeList();
    void getSpares();
    void getServices();

}
