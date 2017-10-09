package com.phyntom.android.popular_movies;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by aimable on 26/09/2017.
 */

class MovieService {

    private static final String API_KEY = "";
    private static String MOVIE_BASE_UR = "https://api.themoviedb.org/3/movie/";

    public List<Movie> fetchMovies(String sortBy,String lan,String pageSize) {
        List<Movie> movies = new ArrayList<>();
        Uri uri = Uri.parse(MOVIE_BASE_UR)
                .buildUpon()
                .appendPath(sortBy)
                .appendQueryParameter("api_key", API_KEY)
                .appendQueryParameter("language", lan)
                .appendQueryParameter("page",pageSize)
                .build();
        try {
            Log.d("URL",uri.toString());
            URL dataURL = new URL(uri.toString());
            HttpURLConnection connection = (HttpURLConnection) dataURL.openConnection();
            InputStream in = connection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            boolean hasInput = scanner.hasNext();
            String jsonResponse;
            if (hasInput) {
                jsonResponse = scanner.next();
            } else {
                jsonResponse = "";
            }
            movies = getMoviesFromJsonResponse(jsonResponse);
            return movies;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return movies;
    }

    private List<Movie> getMoviesFromJsonResponse(String jsonResponse) {
        List<Movie> movieList = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            JSONObject jsonResult = new JSONObject(jsonResponse);
            JSONArray moviesData = jsonResult.getJSONArray("results");
            if (!jsonResponse.isEmpty()) {
                for (int i = 0; i < moviesData.length(); i++) {
                    JSONObject jsonMovieObject = moviesData.getJSONObject(i);
                    Movie movie = new Movie();
                    movie.setId((Integer) jsonMovieObject.get("id"));
                    movie.setTitle((String) jsonMovieObject.get("title"));
                    movie.setOverview((String) jsonMovieObject.get("overview"));
                    movie.setPosterPath((String) jsonMovieObject.get("poster_path"));
                    movie.setVoteAverage(Double.valueOf(jsonMovieObject.get("vote_average").toString()));
                    movie.setReleaseDate(formatter.parse((String) jsonMovieObject.get("release_date")));
                    movieList.add(movie);
                }
            }
        }
        catch (JSONException | ParseException ex) {
            Log.e("Error", ex.getCause().toString());
        }
        return movieList;
    }

}
