package com.example.mymoviemenoir.entity;

public class Memoir {

    private String movieName;
    private String releaseDate;
    private String rating;
    private String watchDate;
    private String watchTime;
    private String comment;
    private Cinema cinemaId;
    private PersonWithId userId;

    public Memoir(String movieName, String releaseDate, String rating, String watchDate, String watchTime, String comment, Cinema cinema, PersonWithId person) {
        this.movieName = movieName;
        this.releaseDate = releaseDate;
        this.rating = rating;
        this.watchDate = watchDate;
        this.watchTime = watchTime;
        this.comment = comment;
        this.cinemaId = cinema;
        this.userId = person;
    }

    public String getMovieName() {
        return movieName;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getRating() {
        return rating;
    }

    public String getWatchDate() {
        return watchDate;
    }

    public String getWatchTime() {
        return watchTime;
    }

    public String getComment() {
        return comment;
    }

    public Cinema getCinema() {
        return cinemaId;
    }

    public PersonWithId getPerson() {
        return userId;
    }
}
