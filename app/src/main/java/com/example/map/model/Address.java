package com.example.map.model;

import com.google.gson.annotations.SerializedName;

public class Address {
    @SerializedName("house_number")
    String house_number;
    @SerializedName("road")
    String road;
    @SerializedName("hamlet")
    String hamlet;
    @SerializedName("town")
    String town;
    @SerializedName("village")
    String village;
    @SerializedName("city")
    String city;
    @SerializedName("county")
    String county;
    @SerializedName("state_district")
    String state_district;
    @SerializedName("state")
    String state;
    @SerializedName("postcode")
    String postcode;
    @SerializedName("country")
    String country;
    @SerializedName("country_code")
    String country_code;

    public String getHouse_number() {
        return house_number;
    }

    public String getRoad() {
        return road;
    }

    public String getHamlet() {
        return hamlet;
    }

    public String getTown() {
        return town;
    }

    public String getVillage() {
        return village;
    }

    public String getCity() {
        return city;
    }

    public String getCounty() {
        return county;
    }

    public String getState_district() {
        return state_district;
    }

    public String getState() {
        return state;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getCountry() {
        return country;
    }

    public String getCountry_code() {
        return country_code;
    }
}
