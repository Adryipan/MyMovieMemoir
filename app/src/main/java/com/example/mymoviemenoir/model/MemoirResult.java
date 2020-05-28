package com.example.mymoviemenoir.model;

public class MemoirResult {

    private String movieName;
    private String releaseDate;
    private String watchDate;
    private String userRating;
    private String onlineRating;
    private String comment;
    private String imageLink;
    private String suburb;

    public MemoirResult(String movieName, String releaseDate, String watchDate,
                        String userRating, String onlineRating, String comment,
                        String imageLink, String suburb) {
        this.movieName = movieName;
        this.releaseDate = releaseDate;
        this.watchDate = watchDate;
        this.userRating = userRating;
        this.onlineRating = onlineRating;
        this.comment = comment;
        this.imageLink = imageLink;
        this.suburb = suburb;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getWatchDate() {
        return watchDate;
    }

    public void setWatchDate(String watchDate) {
        this.watchDate = watchDate;
    }

    public String getUserRating() {
        return userRating;
    }

    public void setUserRating(String userRating) {
        this.userRating = userRating;
    }

    public String getOnlineRating() {
        return onlineRating;
    }

    public void setOnlineRating(String onlineRating) {
        this.onlineRating = onlineRating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }
}
