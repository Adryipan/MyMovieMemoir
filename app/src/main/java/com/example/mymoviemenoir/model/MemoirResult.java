package com.example.mymoviemenoir.model;

import java.util.Date;

public class MemoirResult {

    private String movieName;
    private Date releaseDate;
    private Date watchDate;
    private float userRating;
    private float onlineRating;
    private String comment;
    private String imageLink;
    private String cinema;
    private String suburb;
    private String genre;
    private String country;
    private String director;
    private String cast;
    private String plot;

    public MemoirResult(String movieName, Date releaseDate, Date watchDate
            , float userRating, float onlineRating, String comment
            , String imageLink, String cinema, String suburb, String genre
            , String country, String director, String cast, String plot) {
        this.movieName = movieName;
        this.releaseDate = releaseDate;
        this.watchDate = watchDate;
        this.userRating = userRating;
        this.onlineRating = onlineRating;
        this.comment = comment;
        this.imageLink = imageLink;
        this.cinema = cinema;
        this.suburb = suburb;
        this.genre = genre;
        this.country = country;
        this.director = director;
        this.cast = cast;
        this.plot = plot;
    }

    public String getCast() {
        return cast;
    }

    public String getPlot() {
        return plot;
    }

    public String getDirector() {
        return director;
    }

    public String getMovieName() {
        return movieName;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public Date getWatchDate() {
        return watchDate;
    }

    public float getUserRating() {
        return userRating;
    }

    public float getOnlineRating() {
        return onlineRating;
    }

    public String getComment() {
        return comment;
    }

    public String getImageLink() {
        return imageLink;
    }

    public String getCinema() {
        return cinema;
    }

    public String getSuburb() {
        return suburb;
    }

    public String getGenre() {
        return genre;
    }

    public String getCountry() {
        return country;
    }
}
