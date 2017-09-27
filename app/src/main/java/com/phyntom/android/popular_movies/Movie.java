package com.phyntom.android.popular_movies;

import java.util.Date;

/**
 * Created by aimable on 26/09/2017.
 */

public class Movie {

    private Integer id;
    private String title;
    private String overview;
    private String posterPath;
    private double voteAverage;
    private Date releaseDate;

    public Movie() {
    }

    public Movie(Integer id,String title, String overview, String posterPath, double voteAverage, Date releaseDate) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.posterPath = posterPath;
        this.voteAverage = voteAverage;
        this.releaseDate = releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        if (Double.compare(movie.voteAverage, voteAverage) != 0) return false;
        if (title != null ? !title.equals(movie.title) : movie.title != null) return false;
        if (id != null ? !id.equals(movie.id) : movie.id != null) return false;
        if (overview != null ? !overview.equals(movie.overview) : movie.overview != null)
            return false;
        return posterPath != null ? posterPath.equals(movie.posterPath) : movie.posterPath == null && (releaseDate != null ? releaseDate.equals(movie.releaseDate) : movie.releaseDate == null);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = title != null ? title.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (overview != null ? overview.hashCode() : 0);
        result = 31 * result + (posterPath != null ? posterPath.hashCode() : 0);
        temp = Double.doubleToLongBits(voteAverage);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (releaseDate != null ? releaseDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", id=" + id +
                '}';
    }
}
