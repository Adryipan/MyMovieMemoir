package com.example.mymoviemenoir.model;

public class WatchlistResult {
    private String movieName;
    private String releaseDate;
    private String timeAdded;

    public WatchlistResult(String movieName, String releaseDate, String timeAdded) {
        this.movieName = movieName;
        this.releaseDate = releaseDate;
        this.timeAdded = timeAdded;
    }

    public String getMovieName() {
        return movieName;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getTimeAdded() {
        return timeAdded;
    }

}
