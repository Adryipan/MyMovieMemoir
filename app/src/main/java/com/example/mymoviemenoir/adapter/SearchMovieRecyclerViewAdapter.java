package com.example.mymoviemenoir.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymoviemenoir.R;
import com.example.mymoviemenoir.model.SearchMovieResult;

import java.util.List;

public class SearchMovieRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView movieNameView;
        public TextView releaseYearView;
        public ImageView posterView;

        public ViewHolder(View itemView){
            super(itemView);

            movieNameView = itemView.findViewById(R.id.movie_name);
            releaseYearView = itemView.findViewById(R.id.release_date);
            posterView = itemView.findViewById(R.id.release_date);
        }
    }

    private List<SearchMovieResult> searchMovieResults;

    public SearchMovieRecyclerViewAdapter(List<SearchMovieResult> movies){

    }

    @Override
    public int getItemCount() {
        return searchMovieResults.size();
    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {

    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }




}
