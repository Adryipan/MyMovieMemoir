package com.example.mymoviemenoir.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.mymoviemenoir.RoomEntity.MOVIE;

import java.util.List;

@Dao
public interface WatchlistDAO {

    @Query("SELECT * FROM movie_table")
    LiveData<List<MOVIE>> getAll();

    @Query("SELECT * FROM movie_table")
    List<MOVIE> getAllNoLive();

    @Query("UPDATE movie_table SET movie_name=:movieName, release_date=:releaseDate, time_added=:timeAdded WHERE mid=:mid")
    void updateWatchlistByID(int mid, String movieName, String releaseDate, String timeAdded);

    @Query("SELECT * FROM movie_table WHERE mid = :mid LIMIT 1")
    MOVIE findByID(int mid);

    @Query("SELECT * FROM movie_table WHERE imdb_id = :imdbID LIMIT 1")
    MOVIE findByIMDbID(String imdbID);

    @Query("SELECT * FROM movie_table WHERE movie_name=:movieName AND release_date=:releaseDate LIMIT 1")
    MOVIE findByDetails(String movieName, String releaseDate);

    @Query("SELECT * FROM movie_table WHERE movie_name = :movieName")
    MOVIE findByName(String movieName);

    @Insert
    long insert(MOVIE movie);

    @Delete
    void delete(MOVIE movie);


}
