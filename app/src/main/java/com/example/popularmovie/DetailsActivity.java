package com.example.popularmovie;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class DetailsActivity extends AppCompatActivity {

    private List<Review> reviewList = new ArrayList<>();
    private List<Trailer> movieVideoList = new ArrayList<>();
    private List<Movie> Model;
    private Movie movie;
    private trailerAdapter adapter;
    private reviewAdapter rAdapter;
    private RecyclerView.LayoutManager mTrailerLayoutManager;
    private RecyclerView.LayoutManager reviewLayoutManager;
    RecyclerView reviewRecycleView;
    RecyclerView mTrailerRecyclerView;
    APIinterface moviesAPI;
    int id;



    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_main);

        ImageView imageView = findViewById(R.id.Image_poster);
        TextView rate = findViewById(R.id.Rate);
        TextView title = findViewById(R.id.Header_title);
        TextView date = findViewById(R.id.Release_date);
        TextView description = findViewById(R.id.Description);
        reviewRecycleView = findViewById(R.id.review_recycler);
        mTrailerRecyclerView = findViewById(R.id.trailer_recycler);


        title.setText(getIntent().getExtras().getString("title"));
        description.setText(getIntent().getExtras().getString("overview"));
        date.setText(getIntent().getExtras().getString("release_date"));
        rate.setText(String.valueOf(getIntent().getExtras().getDouble("vote_average")) + "/10");
        id = getIntent().getExtras().getInt("id");
        Picasso.with(DetailsActivity.this)
                .load("http://image.tmdb.org/t/p/w185/" + getIntent().getExtras().getString("poster_path"))
                .into(imageView);

        reviewCall();
        trailerCall();


    }

    private void reviewCall() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIinterface.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        moviesAPI = retrofit.create(APIinterface.class);

        Call<review_response> reviewCall = moviesAPI.getReviews(id);

        reviewCall.enqueue(new Callback<review_response>() {

            @Override
            public void onResponse(Call<review_response> call, Response<review_response> response) {

                List<Review> review = response.body().getResults();
                //   Toast.makeText(DetailsActivity.this, "size" + trailers.size(), Toast.LENGTH_SHORT).show();
                reviewLayoutManager = new LinearLayoutManager(getApplicationContext());
                reviewRecycleView.setLayoutManager(reviewLayoutManager);
                rAdapter = new reviewAdapter(review, getApplicationContext());
                reviewRecycleView.setAdapter(rAdapter);
            }

            @Override
            public void onFailure(Call<review_response> call, Throwable t) {
                Toast.makeText(DetailsActivity.this, "Please Wait Until Connection available", Toast.LENGTH_SHORT).show();
            }

        });
    }




   private void trailerCall() {
       Retrofit retrofit = new Retrofit.Builder()
               .baseUrl(APIinterface.API_URL)
               .addConverterFactory(GsonConverterFactory.create())
               .build();
       moviesAPI = retrofit.create(APIinterface.class);


       Call<trails_resonse> TrailerCall = moviesAPI.getVideos(id);

       TrailerCall.enqueue(new Callback<trails_resonse>() {

           @Override
           public void onResponse(Call<trails_resonse> call, Response<trails_resonse> response) {

               List<Trailer> trailers = response.body().getResults();
               //  Toast.makeText(DetailsActivity.this, "size" + trailers.size(), Toast.LENGTH_SHORT).show();

               mTrailerLayoutManager = new LinearLayoutManager(getApplicationContext());
               mTrailerRecyclerView.setLayoutManager(mTrailerLayoutManager);
               adapter = new trailerAdapter(trailers, getApplicationContext());
               mTrailerRecyclerView.setAdapter(adapter);
           }

           @Override
           public void onFailure(Call<trails_resonse> call, Throwable t) {
               Toast.makeText(DetailsActivity.this, "Please Wait Until Connection available", Toast.LENGTH_SHORT).show();
           }

       });


   }

}



