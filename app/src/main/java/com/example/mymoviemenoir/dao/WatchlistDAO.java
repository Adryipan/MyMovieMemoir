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

    @Query("SELECT * FROM MOVIE")
    LiveData<List<MOVIE>> getAll();

    @Query("UPDATE MOVIE SET movie_name=:movieName, release_date=:releaseDate, time_added=:timeAdded WHERE mid=:mid")
    void updateWatchlistByID(int mid, String movieName, String releaseDate, String timeAdded);

    @Query("SELECT * FROM MOVIE WHERE mid = :mid LIMIT 1")
    LiveData<MOVIE> findByID(int mid);

    @Query("SELECT * FROM MOVIE WHERE movie_name=:movieName AND release_date=:releaseDate AND time_added=:timeAdded LIMIT 1")
    LiveData<MOVIE> findByDetails(String movieName, String releaseDate, String timeAdded);

    @Insert
    long insert(MOVIE movie);

    @Delete
    void delete(MOVIE movie);


}
