package com.example.mymoviemenoir.entity;

import com.google.android.gms.maps.model.LatLng;

public class MapCinema extends Cinema {

    private LatLng geoCode;

    public MapCinema(String cinemaId, String cinemaName, String suburb, LatLng geoCode) {
        super(cinemaId, cinemaName, suburb);
        this.geoCode = geoCode;
    }

    public LatLng getGeoCode() {
        return geoCode;
    }
}
