package com.tantransh.workshopapp.jobbooking.data;

import java.io.Serializable;

public class MakeInfo implements Serializable {
    private String make_id, make_name;

    public String getMake_id() {
        return make_id;
    }

    public void setMake_id(String make_id) {
        this.make_id = make_id;
    }

    public String getMake_name() {
        return make_name;
    }

    public void setMake_name(String make_name) {
        this.make_name = make_name;
    }
}
