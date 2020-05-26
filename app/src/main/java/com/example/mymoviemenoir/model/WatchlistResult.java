package com.example.mymoviemenoir.model;

import com.example.mymoviemenoir.RoomEntity.MOVIE;

public class WatchlistResult {
    private MOVIE movie;

    public WatchlistResult(MOVIE movie) {
        this.movie = movie;
    }

    public MOVIE getMovie() {
        return movie;
    }
}
