package com.example.mymoviemenoir;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.mymoviemenoir.activity.NavHomeActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ReminderBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Calendar calendar = Calendar.getInstance();
        int requestCode = Integer.valueOf(new SimpleDateFormat("dd").format(calendar.getTime()));

        Intent intent1 = new Intent(context,  NavHomeActivity.class);
        intent1.putExtra("start", "watchlist");
        PendingIntent pendingIntent = PendingIntent.getActivities(context,
                requestCode, new Intent[]{intent1}, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        Notification notification = new NotificationCompat.Builder(context, MyMovieMenoir.CHANNEL1_ID)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("My Movie Memoir Watchlist Reminder")
                .setContentText("You want to view one or more movies stored in the watchlist for more than one week?")
                .setCategory(NotificationCompat.CATEGORY_REMINDER)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setOnlyAlertOnce(true)
                .addAction(R.drawable.images, "OK", pendingIntent)
                .build();

        notificationManager.notify(requestCode, notification);
    }
}

