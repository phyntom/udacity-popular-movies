package com.phyntom.android.popular_movies;

import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by aimable on 26/09/2017.
 */

class DataService {

    private static final String API_KEY = "699f241dc2ed8782cbc3d073d7138d94";
    private static final String topRated = "top_rated";
    private static final String popular = "popular";
    private String apiURL = "https://api.themoviedb.org/3/";

    public List<Movie> fetchMovies(String searchBy) {
        switch (searchBy) {
            case "top_rated":
                apiURL += topRated;
                break;
            default:
                apiURL += popular;
                break;
        }
        Uri uri = Uri.parse(apiURL)
                .buildUpon()
                .appendQueryParameter("apiKey", API_KEY)
                .build();
        try {
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
            return getMoviesFromJsonResponse(jsonResponse);
        }
        catch (IOException ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        }
    }

    private List<Movie> getMoviesFromJsonResponse(String jsonResponse) {
        List<Movie> movieList = new ArrayList<>();
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
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
                    movie.setVoteAverage(((Integer) jsonMovieObject.get("vote_average")).doubleValue());
                    movie.setReleaseDate(formatter.parse((String)jsonMovieObject.get("vote_average")));
                    movieList.add(movie);
                }
            }
        }
        catch (JSONException | ParseException ex) {
            ex.printStackTrace();
        }
        return movieList;
    }

}
