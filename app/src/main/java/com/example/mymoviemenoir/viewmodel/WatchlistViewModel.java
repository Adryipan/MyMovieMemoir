package com.example.mymoviemenoir.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mymoviemenoir.RoomEntity.MOVIE;
import com.example.mymoviemenoir.repository.WatchlistRepository;

import java.util.List;

public class WatchlistViewModel extends ViewModel {

    private WatchlistRepository watchlistRepository;

    private MutableLiveData<List<MOVIE>> allMovie;

    public WatchlistViewModel(){
        allMovie = new MutableLiveData<>();
    }

    public void initialize(Application application){
        watchlistRepository = new WatchlistRepository(application);
    }

    public LiveData<List<MOVIE>> getAllMovies(){
        return watchlistRepository.getAllMovie();
    }

    public void insert(MOVIE movie){
        watchlistRepository.insert(movie);
    }

    public void delete(MOVIE movie){
        watchlistRepository.delete(movie);
    }

    public void updateWatchlistByID(int mid, String movieName, String releaseDate, String timeAdded){
        watchlistRepository.updateWatchlistByID(mid, movieName, releaseDate, timeAdded);
    }

    public MOVIE findByID(int mid){
        return watchlistRepository.findByID(mid);
    }

}
