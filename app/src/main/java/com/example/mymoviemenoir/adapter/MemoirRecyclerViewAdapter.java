package com.example.mymoviemenoir.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymoviemenoir.R;
import com.example.mymoviemenoir.model.MemoirResult;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

public class MemoirRecyclerViewAdapter extends RecyclerView.Adapter
        <MemoirRecyclerViewAdapter.ViewHolder>{

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView movieNameTV;
        public TextView releaseDateTV;
        public TextView watchDate;
        public TextView suburbTV;
        public RatingBar userRatingBar;
        public RatingBar onlineRatingBar;
        public TextView comment;
        public ImageView posterView;

        public ViewHolder(View view){
            super(view);

            movieNameTV = view.findViewById(R.id.rv_memoir_name);
            releaseDateTV = view.findViewById(R.id.rv_memoir_release);
            watchDate = view.findViewById(R.id.rv_memoir_watch);
            suburbTV = view.findViewById(R.id.rv_memoir_suburb);
            userRatingBar = view.findViewById(R.id.rv_memoir_userRating);
            onlineRatingBar = view.findViewById(R.id.rv_memoir_publicRating);
            comment = view.findViewById(R.id.rv_memoir_comment);
            posterView = view.findViewById(R.id.rv_memoir_posterView);
        }

    }

    private List<MemoirResult> memoirResults;

    public MemoirRecyclerViewAdapter(List<MemoirResult> memoirs){
        memoirResults = memoirs;
    }

    @NonNull
    @Override
    public MemoirRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View memoirView = inflater.inflate(R.layout.rv_layout_memoir, parent, false);
        ViewHolder viewHolder = new ViewHolder(memoirView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        final MemoirResult memoir = memoirResults.get(position);
        TextView movieName = viewHolder.movieNameTV;
        movieName.setText(memoir.getMovieName());

        TextView releaseDate = viewHolder.releaseDateTV;
        releaseDate.setText("Released:\n" + memoir.getReleaseDate());

        TextView watchDate = viewHolder.watchDate;
        watchDate.setText("Watched:\n" + memoir.getWatchDate());

        TextView suburb = viewHolder.suburbTV;
        suburb.setText("Cinema location: " + memoir.getSuburb());

        RatingBar userRating = viewHolder.userRatingBar;
        userRating.setRating(Float.parseFloat(memoir.getUserRating()));

        RatingBar onlineRating = viewHolder.onlineRatingBar;
        if (!memoir.getOnlineRating().equals("N/A")) {
            onlineRating.setRating(Float.parseFloat(memoir.getOnlineRating()));
        }else{
            onlineRating.setRating(0);
        }

        TextView comment = viewHolder.comment;
        comment.setText("Comment\n" + memoir.getComment() + "\n");

        ImageView poster = viewHolder.posterView;
        if(memoir.getImageLink().isEmpty()){
            poster.setImageResource(R.mipmap.ic_launcher);
        }else {
            Picasso.get()
                    .load(memoir.getImageLink())
                    .placeholder(R.mipmap.ic_launcher)
                    .resize(300, 450)
                    .centerInside()
                    .into(poster);
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return memoirResults.size();
    }
}
