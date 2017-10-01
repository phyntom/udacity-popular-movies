package com.phyntom.android.popular_movies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieViewHolderClickListener {

    RecyclerView recyclerView;
    MovieAdapter mAdapter;
    private TextView titleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.rv_display_movies);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);

        SharedPreferences sharedPreference =
                PreferenceManager.getDefaultSharedPreferences(this);
        String searchKey  = sharedPreference.getString("pref_key", "popular");

        new FetchTask().execute(searchKey);
        mAdapter = new MovieAdapter(getApplicationContext(), this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(Movie movie) {
        Intent intent = new Intent(this, MovieDetails.class);
        intent.putExtra("INTENT_MOVIE", movie);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_settings, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_settings) {
            Intent intent = new Intent(this, Settings.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class FetchTask extends AsyncTask<String, Void, List<Movie>> {

        public FetchTask() {
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<Movie> doInBackground(String... strings) {
            String searchCriteria = null;
            try {
                if (strings.length > 0) {
                    searchCriteria = strings[0];
                }
                MovieService service = new MovieService();
                return service.fetchMovies(searchCriteria);
            }
            catch (Exception ex) {
                ex.printStackTrace();
                Log.e("Loading ERROR", ex.getMessage());
                return new ArrayList<>();
            }
        }

        @Override
        protected void onPostExecute(List<Movie> movieList) {
            super.onPostExecute(movieList);
            mAdapter.setMoviesData(movieList);
        }
    }
}
