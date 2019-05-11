package com.example.popularmovie;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

    @Entity(tableName = "FavMovies")
    public class FavouritMovie {

        @PrimaryKey
        private int id;
        private String title;
        private String releaseDate;
        private Double voteAverage;
        private Double popularity;
        private String overview;
        private String posterPath;
        private String backdropPath;

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




