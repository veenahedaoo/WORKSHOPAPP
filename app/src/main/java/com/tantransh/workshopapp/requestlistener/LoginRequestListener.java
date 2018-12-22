package com.tantransh.workshopapp.requestlistener;

public interface LoginRequestListener {

    void login(String userId, String password);
    void success();
    void failed(String message);
    void error(String message);
}
