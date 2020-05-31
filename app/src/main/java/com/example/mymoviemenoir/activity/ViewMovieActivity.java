package com.example.mymoviemenoir.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.mymoviemenoir.R;
import com.example.mymoviemenoir.ReminderBroadcast;
import com.example.mymoviemenoir.RoomEntity.MOVIE;
import com.example.mymoviemenoir.entity.Movie;
import com.example.mymoviemenoir.neworkconnection.SearchOMDbAPI;
import com.example.mymoviemenoir.viewmodel.WatchlistViewModel;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ViewMovieActivity extends AppCompatActivity {

    private Button watchListBtn;
    private Button menoirBtn;
    private Movie thisMovie;
    private WatchlistViewModel watchlistViewModel;
    private ImageView poster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_movie);
        poster = findViewById(R.id.viewMoviePoster);
        watchlistViewModel = new ViewModelProvider(this).get(WatchlistViewModel.class);
        watchlistViewModel.initialize(getApplication());
        watchlistViewModel.getAllMovies().observe(this, new Observer<List<MOVIE>>() {
            @Override
            public void onChanged(List<MOVIE> moviesResult) {

            }
        });

        Intent intent = this.getIntent();
        final String imdbID = intent.getStringExtra("IMDB ID");

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... strings) {
                return SearchOMDbAPI.searchByIMDbID(imdbID);
            }

            @Override
            protected void onPostExecute(String result) {
                thisMovie = SearchOMDbAPI.getResultMovie(result);
                TextView movieNameTV = findViewById(R.id.movieNameTV);
                TextView movieInfoTV = findViewById(R.id.movieInfoTV);
                RatingBar ratingBar = findViewById(R.id.onlineRatingBar);

                ratingBar.setRating(Float.valueOf(thisMovie.getRating()));
                movieNameTV.setText(thisMovie.getMovieName());
                movieInfoTV.setText("Genre: " + thisMovie.getGenre() + "\n" +
                        "Country: " + thisMovie.getCountry() + "\n" +
                        "Released On: " + thisMovie.getReleaseDate() + "\n" +
                        "Directed by: " + thisMovie.getDirectors() + "\n" +
                        "Cast: " + thisMovie.getCast() + "\n\n" +
                        "Summary: " + "\n\n" +
                        thisMovie.getPlot());
                Picasso.get()
                        .load(thisMovie.getImageLink())
                        .placeholder(R.mipmap.ic_launcher)
                        .resize(450,500)
                        .centerInside()
                        .into(poster);


            }
        }.execute();

        //Check if the caller is from Watchlist or Search movie
        //If it is from Watchlist then disable the buttom
        //Add to watchlist
        watchListBtn = findViewById(R.id.watchlistBtn);
        if (intent.getBooleanExtra("DISABLE", false)) {
            watchListBtn.setEnabled(false);
            watchListBtn.setText("Already in list");
        } else {
            watchListBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Get timestamp
                    Date currentTime = Calendar.getInstance().getTime();
                    DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
                    String timeAdded = dateFormat.format(currentTime);
                    //Call Room
                    MOVIE roomMOVIE = new MOVIE(thisMovie.getMovieName(), thisMovie.getReleaseDate(), timeAdded, thisMovie.getImdbID());
                    AddToWatchListTask add = new AddToWatchListTask();
                    add.execute(roomMOVIE);
                }
            });
        }

            //Add to menoir
            menoirBtn = findViewById(R.id.memoirBtn);
            menoirBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ViewMovieActivity.this, AddToMemoirActivity.class);
                    intent.putExtra("MOVIE", thisMovie.getMovieName());
                    intent.putExtra("RELEASE", thisMovie.getReleaseDate());
                    intent.putExtra("IMDBID", thisMovie.getImdbID());
                    startActivityForResult(intent, 1);
                }
            });
        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            Toast.makeText(this, "Memoir Added", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Cannot add to memoir", Toast.LENGTH_SHORT).show();
        }
    }

    private class AddToWatchListTask extends AsyncTask<MOVIE, Void, String>{

        @Override
        protected String doInBackground(MOVIE... movies) {
            List<MOVIE> result = watchlistViewModel.getAllNoLive();
            boolean found = false;
            //Check if there is existing movie
            for(MOVIE thisMovie : result){
                if(thisMovie.getImdbID().equals(movies[0].getImdbID())){
                    found = true;
                    break;
                }
            }
            //If not found
            if(!found){
                watchlistViewModel.insert(movies[0]);
                return movies[0].getMovieName();
            }else{
                return null;
            }
        }


        @Override
        protected void onPostExecute(String result) {
           //Result is null means not added
            if(result == null){
                Toast.makeText(ViewMovieActivity.this, "Movie already in watchlist", Toast.LENGTH_SHORT).show();
                watchListBtn.setEnabled(false);
            }else{
                Toast.makeText(ViewMovieActivity.this, result + " added to watchlist", Toast.LENGTH_SHORT).show();
                Calendar calendar = Calendar.getInstance();
                int requestCode = Integer.valueOf(new SimpleDateFormat("dd").format(calendar.getTime()));

                Intent broadcastIntent = new Intent(getApplicationContext(), ReminderBroadcast.class);
                PendingIntent actionIntent = PendingIntent.getBroadcast(getApplicationContext(), requestCode, broadcastIntent, PendingIntent.FLAG_CANCEL_CURRENT);

                //For demo
                calendar.add(Calendar.SECOND, 1);
//                calendar.add(Calendar.DAY_OF_MONTH, 7);
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), actionIntent);


            }
        }
    }
}
