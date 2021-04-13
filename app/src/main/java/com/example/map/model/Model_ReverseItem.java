package com.example.map.model;


import com.google.gson.annotations.SerializedName;


import java.util.List;

public class Model_ReverseItem {
    @SerializedName("display_name")
    String display_name;
    public Address address;

    public String getDisplay_name() {
        return display_name;
    }


}
