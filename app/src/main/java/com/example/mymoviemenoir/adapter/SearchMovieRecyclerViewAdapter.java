package com.example.mymoviemenoir.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymoviemenoir.R;
import com.example.mymoviemenoir.model.SearchMovieResult;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchMovieRecyclerViewAdapter extends RecyclerView.Adapter
        <SearchMovieRecyclerViewAdapter.ViewHolder>{

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView movieNameView;
        public TextView releaseYearView;
        public ImageView posterView;
        public TextView viewMovie;

        public ViewHolder(View itemView){
            super(itemView);

            movieNameView = itemView.findViewById(R.id.movie_name);
            releaseYearView = itemView.findViewById(R.id.release_date);
            posterView = itemView.findViewById(R.id.movie_poster);
            viewMovie = itemView.findViewById(R.id.viewMovie);

        }
    }

    private List<SearchMovieResult> searchMovieResults;

    public SearchMovieRecyclerViewAdapter(List<SearchMovieResult> movies){
        searchMovieResults = movies;
    }

    @NonNull
    @Override
    public SearchMovieRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        //Inflate the layout
        View resultView = inflater.inflate(R.layout.search_rv_layout, parent, false);
        //construct the viewholder
        ViewHolder viewHolder = new ViewHolder(resultView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchMovieRecyclerViewAdapter.ViewHolder ViewHolder, int position) {
        final SearchMovieResult movie = searchMovieResults.get(position);

        //For the movie name
        TextView tvMovieName = ViewHolder.movieNameView;
        tvMovieName.setText(movie.getMovieName());
        //For the release year
        TextView tvReleaseYear = ViewHolder.releaseYearView;
        tvReleaseYear.setText(movie.getReleaseYear());
        //One more for the imageview
        ImageView imPoster = ViewHolder.posterView;
        Picasso.get()
                .load(movie.getImageLink())
                .placeholder(R.mipmap.ic_launcher)
                .resize(300,450)
                .centerInside()
                .into(imPoster);
        //Set view movie button
        TextView tvViewMovie = ViewHolder.viewMovie;
        tvMovieName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }



    @Override
    public int getItemCount() {
        return searchMovieResults.size();
    }
}
