package com.example.mymoviemenoir.entity;

public class Movie {

    private String movieName;
    private String rating;
    private String genre;
    private String cast;
    private String releaseDate;
    private String country;
    private String directors;
    private String plot;
    private String imdbID;
    private String imageLink;

    public Movie(String movieName, String rating, String genre, String cast, String releaseDate, String country, String directors, String plot, String imdbID, String imageLink) {
        this.movieName = movieName;
        this.rating = rating;
        this.genre = genre;
        this.cast = cast;
        this.releaseDate = releaseDate;
        this.country = country;
        this.directors = directors;
        this.plot = plot;
        this.imdbID = imdbID;
        this.imageLink = imageLink;
    }

    public String getMovieName() {
        return movieName;
    }

    public String getRating() {
        return rating;
    }

    public String getGenre() {
        return genre;
    }

    public String getCast() {
        return cast;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getCountry() {
        return country;
    }

    public String getDirectors() {
        return directors;
    }

    public String getPlot() {
        return plot;
    }

    public String getImdbID() {
        return imdbID;
    }

    public String getImageLink() {
        return imageLink;
    }
}
