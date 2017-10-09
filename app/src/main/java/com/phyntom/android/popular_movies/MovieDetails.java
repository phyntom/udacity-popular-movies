package com.phyntom.android.popular_movies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;

public class MovieDetails extends AppCompatActivity {

    private final String LOG_TAG = MovieDetails.class.getSimpleName();
    TextView titleTextView;
    ImageView ivPoster;
    TextView averageRateTextView;
    TextView overviewTextView;
    TextView releaseDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        titleTextView = (TextView) findViewById(R.id.tv_title);
        ivPoster = (ImageView) findViewById(R.id.iv_poster);
        averageRateTextView =(TextView) findViewById(R.id.tv_vote_average);
        overviewTextView = (TextView)findViewById(R.id.tv_overview);
        releaseDate = (TextView)findViewById(R.id.tv_release_date);

        Intent sentIntent = getIntent();
        if (sentIntent.hasExtra("INTENT_MOVIE")) {
            Movie selectedMovie = (Movie) sentIntent.getSerializableExtra("INTENT_MOVIE");
            titleTextView.setText(getString(R.string.title) + " : " + selectedMovie.getTitle());
            averageRateTextView.setText(getString(R.string.rating) + " : " + selectedMovie.getVoteAverage());
            overviewTextView.setText(getString(R.string.overview) + " : " + selectedMovie.getOverview());
            Picasso.with(this).load("https://image.tmdb.org/t/p/w342/" + selectedMovie.getPosterPath()).into(ivPoster);
            releaseDate.setText(getString(R.string.release_date) + " : " + format.format(selectedMovie.getReleaseDate()));

        }
    }
}
