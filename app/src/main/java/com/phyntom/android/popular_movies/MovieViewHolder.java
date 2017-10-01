package com.phyntom.android.popular_movies;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by aimable on 27/09/2017.
 */

public class MovieViewHolder extends RecyclerView.ViewHolder {

    ImageView posterImageView;
    TextView titleTextView;
    private MovieViewHolderClickListener listener;

    public MovieViewHolder(View itemView) {
        super(itemView);
        posterImageView = (ImageView) itemView.findViewById(R.id.iv_video_poster);
    }

    public void bind(final Movie movie, final MovieViewHolderClickListener listener){
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(movie);
            }
        });
    }
}
