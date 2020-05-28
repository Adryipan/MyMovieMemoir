package com.example.mymoviemenoir.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymoviemenoir.R;
import com.example.mymoviemenoir.model.TopFiveMovieResult;

import java.util.List;

public class TopFiveRecyclerViewAdapter extends RecyclerView.Adapter
        <TopFiveRecyclerViewAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView movieNameTV;
        public TextView releaseDateTV;
        public RatingBar ratingBar;

        public ViewHolder(View itemView){
            super(itemView);

            movieNameTV = itemView.findViewById(R.id.rv_3col_rb_col1);
            releaseDateTV = itemView.findViewById(R.id.rv_3col_rb_col2);
            ratingBar = itemView.findViewById(R.id.ratingBar2);
        }
    }

    private List<TopFiveMovieResult> movieResults;

    public TopFiveRecyclerViewAdapter(List<TopFiveMovieResult> movies){
        movieResults = movies;
    }

    @NonNull
    @Override
    public TopFiveRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View movieView = inflater.inflate(R.layout.rv_layout_3co_withratingbarl, parent, false);
        ViewHolder viewHolder = new ViewHolder(movieView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        final TopFiveMovieResult movie = movieResults.get(position);
        TextView tvName = viewHolder.movieNameTV;
        tvName.setText(movie.getName());
        TextView tvReleaseDate = viewHolder.releaseDateTV;
        tvReleaseDate.setText(movie.getReleaseDate());
        RatingBar ratingBar = viewHolder.ratingBar;
        ratingBar.setRating(Float.parseFloat(movie.getRating()));
    }


    @Override
    public int getItemCount() {
        return movieResults.size();
    }
}
