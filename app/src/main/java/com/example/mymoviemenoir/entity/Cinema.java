package com.example.mymoviemenoir.entity;

public class Cinema {
    private String cinemaId;
    private String cinemaName;
    private String postcode;

    public Cinema(String cinemaId, String cinemaName, String postcode) {
        this.cinemaId = cinemaId;
        this.cinemaName = cinemaName;
        this.postcode = postcode;
    }

    public String getCinemaId() {
        return cinemaId;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public String getPostcode() {
        return postcode;
    }
}
