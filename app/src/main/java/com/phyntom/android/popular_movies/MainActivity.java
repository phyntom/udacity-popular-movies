package com.phyntom.android.popular_movies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

    String sortBy;

    String page = "1";

    SharedPreferences preferences;

    private TextView titleTextView;

    private LoadMoreRecyclerOnScrollListener loadMoreRecyclerOnScrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.pb_loading_content);

        preferences = getPreferences(MODE_PRIVATE);
        sortBy = preferences.getString("SORT_KEY", "popular");

        recyclerView = (RecyclerView) findViewById(R.id.rv_display_movies);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        new FetchTask().execute(sortBy, page);

        mAdapter = new MovieAdapter(getApplicationContext(), this);
        recyclerView.setAdapter(mAdapter);
        loadMoreRecyclerOnScrollListener = new LoadMoreRecyclerOnScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMorePage(int page) {
                loadMoreMovies(sortBy, "" + page);
            }
        };
        recyclerView.addOnScrollListener(loadMoreRecyclerOnScrollListener);
    }

    /**
     * load more movies by calling background task
     *
     * @param sortBy
     * @param page
     */
    public void loadMoreMovies(String sortBy, String page) {
        new FetchTask().execute(sortBy, "" + page);
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
        if (id == R.id.menu_popular) {
            sortBy = "popular";
            new FetchTask().execute(sortBy, "1");
            mAdapter = new MovieAdapter(getApplicationContext(), this);
        }
        if (id == R.id.menu_top) {
            sortBy = "top_rated";
            new FetchTask().execute(sortBy, "1");
            mAdapter = new MovieAdapter(getApplicationContext(), this);
        }
        recyclerView.setAdapter(mAdapter);
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("SORT_KEY", sortBy);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstance) {
        this.sortBy = savedInstance.getString("SORT_KEY");
    }
    // Based on : https://developer.android.com/training/basics/data-storage/shared-preferences.html
    // 2017-10-09
    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("SORT_KEY", this.sortBy);
        editor.commit();
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
            String sortBy = null;
            String page = null;
            try {
                if (strings.length > 0) {
                    sortBy = strings[0];
                }
                if (strings.length > 1) {
                    page = strings[1];
                }
                if (isInternetConnectionAvailable()) {
                    MovieService service = new MovieService();
                    return service.fetchMovies(sortBy, page);
                } else {
                    Intent intent = new Intent(getApplicationContext(), NoInternet.class);
                    startActivity(intent);
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
            return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();

        }
    }
}
