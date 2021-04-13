package com.example.map.model;

import com.google.gson.annotations.SerializedName;

public class Search {
    @SerializedName("display_name")
    String display_name;

    @SerializedName("lat")
    String lat;

    @SerializedName("lon")
    String lon;

    public String getDisplay_name() {
        return display_name;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }
}
