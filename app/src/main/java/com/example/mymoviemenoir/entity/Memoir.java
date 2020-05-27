package com.example.mymoviemenoir.entity;

public class Memoir {

    private String movieName;
    private String releaseDate;
    private String rating;
    private String watchDate;
    private String watchTime;
    private String comment;
    private Cinema cinema;
    private PersonWithId person;

    public Memoir(String movieName, String releaseDate, String rating, String watchDate, String watchTime, String comment, Cinema cinema, PersonWithId person) {
        this.movieName = movieName;
        this.releaseDate = releaseDate;
        this.rating = rating;
        this.watchDate = watchDate;
        this.watchTime = watchTime;
        this.comment = comment;
        this.cinema = cinema;
        this.person = person;
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
        return cinema;
    }

    public PersonWithId getPerson() {
        return person;
    }
}
