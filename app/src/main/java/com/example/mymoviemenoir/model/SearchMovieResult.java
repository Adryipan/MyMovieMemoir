package com.example.mymoviemenoir.model;

public class SearchMovieResult {

    private String movieName;
    private String releaseYear;

    public SearchMovieResult(String movieName, String releaseYear) {
        this.movieName = movieName;
        this.releaseYear = releaseYear;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }
}
