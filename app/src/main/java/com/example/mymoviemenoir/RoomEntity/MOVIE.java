package com.example.mymoviemenoir.RoomEntity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "movie_table")
public class MOVIE {
    @PrimaryKey(autoGenerate = true)
    public int mid;

    @ColumnInfo(name = "movie_name")
    public String movieName;

    @ColumnInfo(name = "release_date")
    public String releaseDate;

    @ColumnInfo(name = "time_added")
    public String timeAdded;

    @ColumnInfo(name = "imdb_id")
    public String imdbID;

    public MOVIE(String movieName, String releaseDate, String timeAdded, String imdbID) {
        this.movieName = movieName;
        this.releaseDate = releaseDate;
        this.timeAdded = timeAdded;
        this.imdbID = imdbID;
    }

    public int getMid() {
        return mid;
    }

    public String getMovieName() {
        return movieName;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getTimeAdded() {
        return timeAdded;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setTimeAdded(String timeAdded) {
        this.timeAdded = timeAdded;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }
}
