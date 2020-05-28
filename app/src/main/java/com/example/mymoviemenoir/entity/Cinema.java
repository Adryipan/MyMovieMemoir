package com.example.mymoviemenoir.entity;

import com.google.android.gms.maps.model.LatLng;

public class Cinema {
    private String cinemaId;
    private String cinemaName;
    private String suburb;
    private LatLng geocode;

    public Cinema(String cinemaId, String cinemaName, String suburb) {
        this.cinemaId = cinemaId;
        this.cinemaName = cinemaName;
        this.suburb = suburb;
    }

    public String getCinemaId() {
        return cinemaId;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public String getSuburb() {
        return suburb;
    }

    public LatLng getGeocode() {
        return geocode;
    }

    public void setGeocode(LatLng geocode) {
        this.geocode = geocode;
    }
}
