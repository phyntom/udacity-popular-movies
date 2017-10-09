package com.phyntom.android.popular_movies;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aimable on 27/09/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    List<Movie> movies =new ArrayList<>();
    Context context;
    private MovieViewHolderClickListener mListener;

    public MovieAdapter(Context context,MovieViewHolderClickListener listener) {
        this.context = context;
        this.mListener=listener;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        boolean shouldAttachToParentImmediately = false;
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movies_list, parent,shouldAttachToParentImmediately);
        return new MovieViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, final int position) {
        Picasso.with(context).load("https://image.tmdb.org/t/p/w342/"+movies.get(position).getPosterPath()).into(holder.posterImageView);
        holder.bind(movies.get(position),mListener);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void setMoviesData(List<Movie> fetchedMovies) {

        if(this.getItemCount() > 0){
            this.movies.addAll(fetchedMovies);
            notifyItemRangeInserted(this.getItemCount(),this.movies.size()-1);
        }
        else{
            this.movies = fetchedMovies;
            notifyDataSetChanged();
        }

    }



}
