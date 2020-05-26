package com.example.mymoviemenoir;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.mymoviemenoir.RoomEntity.MOVIE;
import com.example.mymoviemenoir.entity.Movie;
import com.example.mymoviemenoir.neworkconnection.SearchOMDbAPI;
import com.example.mymoviemenoir.viewmodel.WatchlistViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ViewMovieActivity extends AppCompatActivity {

    private Button watchListBtn;
    private Button menoirBtn;
    private Movie thisMovie;
    private WatchlistViewModel watchlistViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_view);
        watchlistViewModel = new ViewModelProvider(this).get(WatchlistViewModel.class);
        watchlistViewModel.initialize(getApplication());

        Intent intent = this.getIntent();
        String movieName = intent.getStringExtra("MOVIE NAME");
        final String imdbID = intent.getStringExtra("IMDB ID");

        new AsyncTask<String, Void, String>(){

            @Override
            protected String doInBackground(String... strings) {
                return SearchOMDbAPI.searchByIMDbID(imdbID);
            }
            @Override
            protected void onPostExecute(String result) {
                thisMovie = SearchOMDbAPI.getResultMovie(result);
                TextView movieNameTV = findViewById(R.id.movieNameTV);
                TextView movieInfoTV = findViewById(R.id.movieInfoTV);

                movieNameTV.setText(thisMovie.getMovieName());
                movieInfoTV.setText("Genre: " + thisMovie.getGenre() + "\n" +
                        "Country: " + thisMovie.getCountry() + "\n" +
                        "Released On: " + thisMovie.getReleaseDate() + "\n" +
                        "Directed by: " + thisMovie.getDirectors() + "\n" +
                        "Cast: " + thisMovie.getCast() + "\n" +
                        "Summary: " + "\n" +
                        thisMovie.getPlot());
            }
        }.execute();

        //Add to watchlist
        watchListBtn = findViewById(R.id.watchlistBtn);
        watchListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get timestamp
                Date currentTime = Calendar.getInstance().getTime();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String timeAdded = dateFormat.format(currentTime);
                //Call Room
                MOVIE roomMOVIE = new MOVIE(thisMovie.getMovieName(), thisMovie.getReleaseDate(), timeAdded);
                watchlistViewModel.insert(roomMOVIE);
                Toast.makeText(ViewMovieActivity.this, thisMovie.getMovieName() + " added to Watchlist.", Toast.LENGTH_SHORT).show();

            }
        });

        //Add to menoir
        menoirBtn = findViewById(R.id.memoirBtn);
        menoirBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewMovieActivity.this, AddToMemoirActivity.class);
                intent.putExtra("MOVIE", thisMovie.getMovieName());
                intent.putExtra("RELEASE", thisMovie.getReleaseDate());
                startActivity(intent);
            }
        });

    }
}
