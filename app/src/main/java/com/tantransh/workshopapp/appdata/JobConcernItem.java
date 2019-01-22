package com.tantransh.workshopapp.appdata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JobConcernItem {

    @SerializedName("item_id")
    @Expose
    public String itemId ;

    @SerializedName("item_name")
    @Expose
    public String itemName;

    @SerializedName("category_id")
    @Expose
    public String categoryName;
}
