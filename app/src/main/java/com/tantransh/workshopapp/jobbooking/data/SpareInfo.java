package com.tantransh.workshopapp.jobbooking.data;

import java.io.Serializable;

public class SpareInfo implements Serializable{
    private String spareId, spareName, make, amount, unit,qty;
    private SpareInfo (){}

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public static SpareInfo getInstance(){
        return new SpareInfo();
    }

    public String getSpareId() {
        return spareId;
    }

    public void setSpareId(String spareId) {
        this.spareId = spareId;
    }

    public String getSpareName() {
        return spareName;
    }

    public void setSpareName(String spareName) {
        this.spareName = spareName;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
