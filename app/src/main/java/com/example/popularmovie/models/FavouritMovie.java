package com.example.popularmovie.models;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "FavMovies")
public class FavouritMovie implements Serializable {

    @SerializedName("id")
    @Expose
    @PrimaryKey
    private int id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("release_date")
    @Expose
    private String releaseDate;
    @SerializedName("vote_average")
    @Expose
    private Double voteAverage;
    @SerializedName("popularity")
    @Expose
    private Double popularity;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("poster_path")
    @Expose
    private String posterPath;
    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;

    @Ignore

    public FavouritMovie(){

    }

    public FavouritMovie(int id, String title, String releaseDate, Double voteAverage, Double popularity, String overview, String posterPath, String backdropPath) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
        this.popularity = popularity;
        this.overview = overview;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
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

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }
}




