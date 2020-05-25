package com.example.mymoviemenoir;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MovieViewActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_view);
        Intent intent = this.getIntent();
        String movieName = intent.getStringExtra("MOVIE NAME");
        String imdbID = intent.getStringExtra("IMDB ID");


    }
}
