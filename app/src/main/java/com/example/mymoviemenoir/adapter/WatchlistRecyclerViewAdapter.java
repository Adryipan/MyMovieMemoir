package com.example.mymoviemenoir.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymoviemenoir.R;
import com.example.mymoviemenoir.model.WatchlistResult;

import java.util.List;

public class WatchlistRecyclerViewAdapter  extends RecyclerView.Adapter
        <WatchlistRecyclerViewAdapter.ViewHolder>{

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView movieNameTV;
            public TextView releaseDateTV;
            public TextView timeAddedTV;

            public ViewHolder(View itemView){
                super(itemView);
                movieNameTV = itemView.findViewById(R.id.rv_3col_rb_col1);
                releaseDateTV = itemView.findViewById(R.id.rv_3vol_rb_col2);
                timeAddedTV = itemView.findViewById(R.id.rv_3vol_col3);
            }

        }

    private List<WatchlistResult> watchlistResults;
    private int selectedPosition = -1;

    public WatchlistRecyclerViewAdapter(List<WatchlistResult> movies){
        watchlistResults = movies;
    }

    @NonNull
    @Override
    public WatchlistRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View watchlistView = inflater.inflate(R.layout.rv_layout_3col, parent, false);
        ViewHolder viewHolder = new ViewHolder(watchlistView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WatchlistRecyclerViewAdapter.ViewHolder viewHolder, final int position) {
        if(selectedPosition == position){
            viewHolder.itemView.setBackgroundColor(Color.parseColor("#ffcc00"));
        }else{
            viewHolder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition = position;
                notifyDataSetChanged();
            }
        });
        final WatchlistResult movie = watchlistResults.get(position);
        TextView tvMovieName = viewHolder.movieNameTV;
        tvMovieName.setText(movie.getMovie().getMovieName());
        TextView tvReleaseDate = viewHolder.releaseDateTV;
        tvReleaseDate.setText(movie.getMovie().getReleaseDate());
        TextView tvTimeAdded = viewHolder.timeAddedTV;
        tvTimeAdded.setText(movie.getMovie().getReleaseDate());

    }

    @Override
    public int getItemCount() {
        return watchlistResults.size();
    }

    public WatchlistResult getSelectedItem(){
        return watchlistResults.get(selectedPosition);
    }

    public void deleteDate(){
        watchlistResults.remove(selectedPosition);
        notifyDataSetChanged();
    }


}
