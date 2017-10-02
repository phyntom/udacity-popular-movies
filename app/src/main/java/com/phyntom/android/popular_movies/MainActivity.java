package com.phyntom.android.popular_movies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
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
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieViewHolderClickListener {

    RecyclerView recyclerView;
    MovieAdapter mAdapter;
    ProgressBar progressBar;
    private TextView titleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar=(ProgressBar)findViewById(R.id.pb_loading_content);
        recyclerView = (RecyclerView) findViewById(R.id.rv_display_movies);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);

        SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(this);
        String searchKey = sharedPreference.getString("pref_sort_key", "popular");
        String language = sharedPreference.getString("pref_lan_key", "en-US");

        new FetchTask().execute(searchKey, language);
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
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Movie> doInBackground(String... strings) {
            String searchCriteria = null;
            String pageSize = null;
            try {
                if (strings.length > 0) {
                    searchCriteria = strings[0];
                }
                if (strings.length > 1) {
                    pageSize = strings[1];
                }
                if(isInternetConnectionAvailable()) {
                    MovieService service = new MovieService();
                    return service.fetchMovies(searchCriteria, pageSize);
                }
                else{
                    String errorMessage=getString(R.string.error_internet);
                    Toast.makeText(getApplicationContext(),errorMessage,Toast.LENGTH_SHORT).show();
                    return new ArrayList<>();
                }
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
            progressBar.setVisibility(View.INVISIBLE);
        }
        // Based on : https://stackoverflow.com/questions/1560788/how-to-check-internet-access-on-android-inetaddress-never-times-out
        // 2017-10-01
        public boolean isInternetConnectionAvailable() {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected()) {
                Log.d("CONNECTION","connected");
                return true;
            } else {
                return false;
            }

        }
    }
}
