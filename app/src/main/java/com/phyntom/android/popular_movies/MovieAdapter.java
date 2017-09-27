package com.phyntom.android.popular_movies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by aimable on 27/09/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    List<Movie> movies;
    Context context;

    public MovieAdapter(List<Movie> movies, Context context) {
        this.movies = movies;
        this.context = context;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView= LayoutInflater.from(parent.getContext()).inflate(R.layout.movies_list,parent,false);
        return new MovieViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.titleTextView.setText(movies.get(position).getTitle());
        Picasso.with(context).load("https://image.tmdb.org/t/p/w185/"+movies.get(position).getPosterPath()).into(holder.posterImageView);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}
