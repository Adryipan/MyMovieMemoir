package com.example.mymoviemenoir.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.mymoviemenoir.RoomEntity.MOVIE;
import com.example.mymoviemenoir.dao.WatchlistDAO;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {MOVIE.class}, version = 3, exportSchema = false)
public abstract class WatchlistDB extends RoomDatabase {

    public abstract WatchlistDAO watchlistDAO();
    private static WatchlistDB INSTANCE;

    //Setup fixed thread pool
    private static final int NUMBER_OF_THREADS = 4;

    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static synchronized WatchlistDB getInstance(final Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    WatchlistDB.class, "WatchlistDB")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}
