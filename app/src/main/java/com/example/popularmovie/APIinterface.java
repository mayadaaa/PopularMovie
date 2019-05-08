package com.example.popularmovie;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface APIinterface {

    String API_URL = "http://api.themoviedb.org";

    @GET("/3/movie/popular?api_key=" + "e452fe926d2f858deb33e033f9ee6873")
    Call<MovieDetails> getApiPopular();

    @GET("/3/movie/top_rated?api_key=" + "e452fe926d2f858deb33e033f9ee6873")
    Call<MovieDetails> getApiTopRated();

    @GET("/3/movie/{id}/videos?api_key=" + "e452fe926d2f858deb33e033f9ee6873")
    Call<trails_resonse> getVideos(@Path("id") int id  );

    @GET("/3/review/{review_id}?api_key=" + "e452fe926d2f858deb33e033f9ee6873")
    Call<review_response> getReviews(@Path("review_id") int id  );




}

