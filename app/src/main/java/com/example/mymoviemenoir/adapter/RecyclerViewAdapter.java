package com.example.mymoviemenoir.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymoviemenoir.R;
import com.example.mymoviemenoir.model.TopFiveMovieResult;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView movieNameTV;
        public TextView releaseDateTV;
        public TextView rating;

        //Accept the entire view
        //Provide reference and access to all the views in each row
        public ViewHolder(View itemView){
            super(itemView);

            movieNameTV = itemView.findViewById(R.id.movie_name);
            releaseDateTV = itemView.findViewById(R.id.release_date);
            rating = itemView.findViewById(R.id.myScore);
        }
    }

    private List<TopFiveMovieResult> topFiveMovieResults;

    //Pass in the result to the constructor
    public RecyclerViewAdapter(List<TopFiveMovieResult> movieResults){
        topFiveMovieResults = movieResults;
    }

    @Override
    public int getItemCount(){
        return topFiveMovieResults.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }


}
