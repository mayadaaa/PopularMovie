package com.example.popularmovie;


import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailsActivity extends AppCompatActivity {

    private static final String TAG = MovieDetails.class.getSimpleName();
    private Movie movieItem;

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
    int id = 299534;

    private Button favouriteButton;
    private FavDatabase database;
    private Boolean isFav = false;


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
        favouriteButton = findViewById(R.id.favButton);


        title.setText(getIntent().getExtras().getString("title"));
        description.setText(getIntent().getExtras().getString("overview"));
        date.setText(getIntent().getExtras().getString("release_date"));
        rate.setText(String.valueOf(getIntent().getExtras().getDouble("vote_average")) + "/10");
        id = getIntent().getExtras().getInt("id");
        Picasso.with(DetailsActivity.this)
                .load("http://image.tmdb.org/t/p/w185/" + getIntent().getExtras().getString("poster_path"))
                .into(imageView);

        gettrailers();
        getreviews();





        favouriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FavouritMovie favouritMovie = new FavouritMovie(
                        movieItem.getId(),
                        movieItem.getTitle(),
                        movieItem.getReleaseDate(),
                        movieItem.getVoteAverage(),
                        movieItem.getPopularity(),
                        movieItem.getOverview(),
                        movieItem.getPosterPath(),
                        movieItem.getBackdropPath()

                );

                if (isFav) {
                        // delete item
                        database.Dao().delete(favouritMovie);
                    } else {
                        // insert item
                        database.Dao().insert(favouritMovie);
                    }
                }



        });
    }




    public void gettrailers() {
        APIinterface service = RetrofitClient.getRetrofitInstance().create(APIinterface.class);
        Call<trails_resonse> call = service.getVideos(id);
        call.enqueue(new Callback<trails_resonse>() {
            @Override
            public void onResponse(Call<trails_resonse> call,
                                   Response<trails_resonse> response) {
                //   Toast.makeText(DetailsActivity.this, "size"+response.body().getResults().size(), Toast.LENGTH_SHORT).show();
                List<Trailer> trailers = response.body().getResults();

                mTrailerLayoutManager = new LinearLayoutManager(getApplicationContext());
                mTrailerRecyclerView.setLayoutManager(mTrailerLayoutManager);
                adapter = new trailerAdapter(trailers, getApplicationContext());
                mTrailerRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<trails_resonse> call, Throwable t) {
                Toast.makeText(DetailsActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void getreviews() {
        APIinterface service = RetrofitClient.getRetrofitInstance().create(APIinterface.class);
        //   Log.e("idididi", "id===  "+id);
        Call<review_response> call = service.getReviews(id);
        call.enqueue(new Callback<review_response>() {
            @Override
            public void onResponse(Call<review_response> call,
                                   Response<review_response> response) {
                //   Toast.makeText(DetailsActivity.this, "size"+response.body(), Toast.LENGTH_SHORT).show();
                List<Review> review = response.body().getReviews();
                reviewLayoutManager = new LinearLayoutManager(getApplicationContext());
                reviewRecycleView.setLayoutManager(reviewLayoutManager);
                rAdapter = new reviewAdapter(review, getApplicationContext());
                reviewRecycleView.setAdapter(rAdapter);

            }

            @Override
            public void onFailure(Call<review_response> call, Throwable t) {
                Toast.makeText(DetailsActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }


}



