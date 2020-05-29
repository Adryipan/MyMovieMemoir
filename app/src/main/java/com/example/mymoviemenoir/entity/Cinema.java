package com.example.mymoviemenoir.entity;

public class Cinema {
    private String cinemaId;
    private String cinemaName;
    private String suburb;

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

}
