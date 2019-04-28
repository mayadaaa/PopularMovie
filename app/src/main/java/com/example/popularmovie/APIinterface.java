package com.example.popularmovie;


import retrofit2.Call;
import retrofit2.http.GET;


public interface APIinterface {

    String API_URL = "http://api.themoviedb.org";

    @GET("/3/movie/popular?api_key=" + "YOUR API")
    Call<MovieDetails> getApiPopular();

    @GET("/3/movie/top_rated?api_key=" + "YOUR API")
    Call<MovieDetails> getApiTopRated();

}

