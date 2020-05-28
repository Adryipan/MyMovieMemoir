package com.example.mymoviemenoir.model;

public class SearchMovieResult {

    private String movieName;
    private String releaseYear;
    private String imageLink;
    private String imdbID;

    public SearchMovieResult(String movieName, String releaseYear, String imageLink, String imdbID) {
        this.movieName = movieName;
        this.releaseYear = releaseYear;
        this.imageLink = imageLink;
        this.imdbID = imdbID;
    }

    public String getImdbID() {
        return imdbID;
    }


    public String getMovieName() {
        return movieName;
    }


    public String getReleaseYear() {
        return releaseYear;
    }


    public String getImageLink() {
        return imageLink;
    }


}
