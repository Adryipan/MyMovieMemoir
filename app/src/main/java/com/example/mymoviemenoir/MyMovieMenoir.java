package com.example.mymoviemenoir;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;

public class MyMovieMenoir extends Application {
    public static final String CHANNEL1_ID = "MyMovieMemoir";

    @Override
    public void onCreate(){
        super.onCreate();
        createNotificationChannel();

    }

    private void createNotificationChannel(){
        NotificationChannel channel1 = new NotificationChannel(CHANNEL1_ID, "My Movie Memoir", NotificationManager.IMPORTANCE_DEFAULT);
        channel1.setDescription("My Movie Memoir Watchlist reminder");

        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(channel1);
    }
}
