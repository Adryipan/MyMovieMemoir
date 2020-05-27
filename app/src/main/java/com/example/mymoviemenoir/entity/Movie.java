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

    public Movie(String movieName, String rating, String genre, String cast, String releaseDate, String country, String directors, String plot, String imdbID) {
        this.movieName = movieName;
        this.rating = rating;
        this.genre = genre;
        this.cast = cast;
        this.releaseDate = releaseDate;
        this.country = country;
        this.directors = directors;
        this.plot = plot;
        this.imdbID = imdbID;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getCast() {
        return cast;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDirectors() {
        return directors;
    }

    public void setDirectors(String directors) {
        this.directors = directors;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }
}
