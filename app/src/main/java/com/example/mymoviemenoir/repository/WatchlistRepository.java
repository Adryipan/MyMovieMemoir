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
    private MOVIE queryMovie;

    public WatchlistRepository(Application application){
        WatchlistDB db = WatchlistDB.getInstance(application);
        dao = db.watchlistDAO();
    }

    public LiveData<List<MOVIE>> getAllMovie(){
        allMovie = dao.getAll();
        return allMovie;
    }

    public List<MOVIE> getAllNoLive(){
        return dao.getAllNoLive();
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

    public MOVIE findByID(final int mid){
        WatchlistDB.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                MOVIE runMOVIE = dao.findByID(mid);
                setResultMovie(runMOVIE);
            }
        });
        return queryMovie;
    }

    public MOVIE findByDetails(final String movieName, final String releaseDate){
        WatchlistDB.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                MOVIE runMOVIE = dao.findByDetails(movieName, releaseDate);
                setResultMovie(runMOVIE);
            }
        });
        return queryMovie;
    }

    public MOVIE findByName(final String movieName){
        WatchlistDB.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                MOVIE runMOVIE = dao.findByName(movieName);
                setResultMovie(runMOVIE);
            }
        });
        return queryMovie;
    }

    public MOVIE findByIMDbID(final String imdbID){
        WatchlistDB.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                MOVIE runMOVIE = dao.findByIMDbID(imdbID);
                setResultMovie(runMOVIE);
            }
        });
        return queryMovie;
    }


    public void setResultMovie(MOVIE thisMovie){
        this.queryMovie = thisMovie;
    }
}
