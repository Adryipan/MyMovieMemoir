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
    private String suburb;
    private String genre;

    public MemoirResult(String movieName, Date releaseDate, Date watchDate, float userRating, float onlineRating, String comment, String imageLink, String suburb, String genre) {
        this.movieName = movieName;
        this.releaseDate = releaseDate;
        this.watchDate = watchDate;
        this.userRating = userRating;
        this.onlineRating = onlineRating;
        this.comment = comment;
        this.imageLink = imageLink;
        this.suburb = suburb;
        this.genre = genre;
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

    public String getSuburb() {
        return suburb;
    }

    public String getGenre() {
        return genre;
    }
}
