package com.example.mymoviemenoir.RoomEntity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MOVIE {
    @PrimaryKey(autoGenerate = true)
    public int mid;

    @ColumnInfo(name = "movie_name")
    public String movieName;

    @ColumnInfo(name = "release_date")
    public String releaseDate;

    @ColumnInfo(name = "time_added")
    public String timeAdded;

    public MOVIE(String movieName, String releaseDate, String timeAdded) {
        this.movieName = movieName;
        this.releaseDate = releaseDate;
        this.timeAdded = timeAdded;
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
}
