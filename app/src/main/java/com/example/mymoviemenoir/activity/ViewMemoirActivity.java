package com.example.mymoviemenoir.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mymoviemenoir.R;
import com.squareup.picasso.Picasso;

public class ViewMemoirActivity extends AppCompatActivity {

    private TextView movieNameTV;
    private TextView watchDateTV;
    private TextView cinemaTV;
    private TextView genreTV;
    private TextView countryTV;
    private TextView releaseTV;
    private TextView directorTV;
    private TextView castTV;
    private TextView summaryTV;
    private TextView commentTV;
    private ImageView posterView;
    private RatingBar userRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_memoir);
        Intent intent = this.getIntent();

        movieNameTV = findViewById(R.id.nameTV);
        movieNameTV.setText(intent.getStringExtra("NAME"));

        userRating = findViewById(R.id.myRatingBar);
        userRating.setRating(intent.getFloatExtra("RATING", 0));

        posterView = findViewById(R.id.posterView);
        String imageLink = intent.getStringExtra("IMGLINK");
        if(imageLink.isEmpty()){
            posterView.setImageResource(R.mipmap.ic_launcher);
        }else {
            Picasso.get()
                    .load(imageLink)
                    .placeholder(R.mipmap.ic_launcher)
                    .resize(300, 450)
                    .centerInside()
                    .into(posterView);
        }

        watchDateTV = findViewById(R.id.watchDateTV);
        String watch = intent.getStringExtra("WATCH");
        watchDateTV.setText("Watched on:\n" + intent.getStringExtra("WATCH"));

        cinemaTV = findViewById(R.id.cinemaTV);
        cinemaTV.setText("Cinema: " + intent.getStringExtra("CINEMA"));

        countryTV = findViewById(R.id.countryTV);
        countryTV.setText("Country: " + intent.getStringExtra("COUNTRY"));

        genreTV = findViewById(R.id.genreTV);
        genreTV.setText("Genre: " + intent.getStringExtra("GENRE"));

        releaseTV = findViewById(R.id.releaseDateTV);
        String releaseDate = intent.getStringExtra("RELEASE");
        releaseTV.setText("Released on:\n" + releaseDate);

        directorTV = findViewById(R.id.directorTV);
        directorTV.setText("Directed by: " + intent.getStringExtra("DIRECTOR"));

        castTV = findViewById(R.id.castTV);
        castTV.setText("Cast:\n" + intent.getStringExtra("CAST") + "\n");

        summaryTV = findViewById(R.id.summaryTV);
        summaryTV.setText("Plot:\n" + intent.getStringExtra("SUMMARY") + "\n");

        commentTV = findViewById(R.id.commentTV);
        commentTV.setText("Comment:\n" + intent.getStringExtra("COMMENT"));
    }
}
