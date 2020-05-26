package com.example.mymoviemenoir.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.mymoviemenoir.RoomEntity.MOVIE;
import com.example.mymoviemenoir.dao.WatchlistDAO;
import com.example.mymoviemenoir.database.WatchlistDB;

import java.util.List;

public class WatchlistRepository {

    private WatchlistDAO dao;
    private LiveData<List<MOVIE>> allMovie;
    private LiveData<MOVIE> movie;

    public WatchlistRepository(Application application){
        WatchlistDB db = WatchlistDB.getInstance(application);
        dao = db.watchlistDAO();
    }

    public LiveData<List<MOVIE>> getAllMovie(){
        allMovie = dao.getAll();
        return allMovie;
    }

    public void insert (final MOVIE movie){
        WatchlistDB.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.insert(movie);
            }
        });
    }

    //Delete all is not implemented. Maybe later

    public void delete(final MOVIE movie){
        WatchlistDB.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.delete(movie);
            }
        });
    }

    //Insert all is not implemented. Don't see the use case yet

    public void updateWatchlistByID(final int mid, final String movieName, final String releaseDate, final String timeAdded){
        WatchlistDB.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.updateWatchlistByID(mid, movieName, releaseDate, timeAdded);
            }
        });
    }

    public LiveData<MOVIE> findByID(final int mid){
        WatchlistDB.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                LiveData<MOVIE> runMOVIE = dao.findByID(mid);
                setMovie(runMOVIE);
            }
        });
        return movie;
    }

    public LiveData<MOVIE> findByDetails(final String movieName, final String releaseDate, final String timeAdded){
        WatchlistDB.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                LiveData<MOVIE> runMOVIE = dao.findByDetails(movieName, releaseDate, timeAdded);
                setMovie(runMOVIE);
            }
        });
        return movie;
    }

    public void setMovie(LiveData<MOVIE> movie) {
        this.movie = movie;
    }
}
