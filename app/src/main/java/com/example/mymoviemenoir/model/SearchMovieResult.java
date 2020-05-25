package com.example.mymoviemenoir.model;

public class SearchMovieResult {

    private String movieName;
    private String releaseYear;
    private String imageLink;

    public SearchMovieResult(String movieName, String releaseYear, String imageLink) {
        this.movieName = movieName;
        this.releaseYear = releaseYear;
        this.imageLink = imageLink;
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

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }
}
