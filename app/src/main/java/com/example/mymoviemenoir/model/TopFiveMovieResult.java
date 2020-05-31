package com.example.mymoviemenoir.model;

public class TopFiveMovieResult {
    private String name;
    private String releaseDate;
    private String rating;

    public TopFiveMovieResult(String name, String releaseDate, String rating){
        this.name = name;
        this.releaseDate = releaseDate;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getRating() {
        return rating;
    }
}
