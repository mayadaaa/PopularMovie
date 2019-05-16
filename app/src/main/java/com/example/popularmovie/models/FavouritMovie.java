package com.example.popularmovie.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

    @Entity(tableName = "FavMovies")
    public class FavouritMovie implements Parcelable {

        @PrimaryKey(autoGenerate = true)
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

        protected FavouritMovie(Parcel in) {
            id = in.readInt();
            title = in.readString();
            releaseDate = in.readString();
            if (in.readByte() == 0) {
                voteAverage = null;
            } else {
                voteAverage = in.readDouble();
            }
            if (in.readByte() == 0) {
                popularity = null;
            } else {
                popularity = in.readDouble();
            }
            overview = in.readString();
            posterPath = in.readString();
            backdropPath = in.readString();
        }

        public static final Creator<FavouritMovie> CREATOR = new Creator<FavouritMovie>() {
            @Override
            public FavouritMovie createFromParcel(Parcel in) {
                return new FavouritMovie(in);
            }

            @Override
            public FavouritMovie[] newArray(int size) {
                return new FavouritMovie[size];
            }
        };

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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(id);
            dest.writeString(title);
            dest.writeString(releaseDate);
            if (voteAverage == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeDouble(voteAverage);
            }
            if (popularity == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeDouble(popularity);
            }
            dest.writeString(overview);
            dest.writeString(posterPath);
            dest.writeString(backdropPath);
        }
    }




