package com.example.mymoviemenoir.entity;

import androidx.annotation.NonNull;

public class Cinema {
    private int cinemaId;
    private String cinemaName;
    private String suburb;

    public Cinema(int cinemaId, String cinemaName, String suburb) {
        this.cinemaId = cinemaId;
        this.cinemaName = cinemaName;
        this.suburb = suburb;
    }

    public int getCinemaId() {
        return cinemaId;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public String getSuburb() {
        return suburb;
    }

    @NonNull
    @Override
    public String toString() {
        return cinemaName + ", " + suburb;
    }
}
