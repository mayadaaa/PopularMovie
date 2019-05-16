package com.example.popularmovie;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovie.adapters.reviewAdapter;
import com.example.popularmovie.adapters.trailerAdapter;
import com.example.popularmovie.database.appdatabase;
import com.example.popularmovie.models.FavouritMovie;
import com.example.popularmovie.models.Movie;
import com.example.popularmovie.models.MovieDetails;
import com.example.popularmovie.models.Review;
import com.example.popularmovie.models.Trailer;
import com.example.popularmovie.models.review_response;
import com.example.popularmovie.models.trails_resonse;
import com.example.popularmovie.network.APIinterface;
import com.example.popularmovie.network.RetrofitClient;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity {



    private Movie movieItem;

    private List<Review> reviewList = new ArrayList<>();
    private List<Trailer> movieVideoList = new ArrayList<>();
    private List<Movie> Model;

    private trailerAdapter adapter;
    private reviewAdapter rAdapter;
    private RecyclerView.LayoutManager mTrailerLayoutManager;
    private RecyclerView.LayoutManager reviewLayoutManager;
    private RecyclerView reviewRecycleView;
    private RecyclerView mTrailerRecyclerView;
    
    private int id;
    public Button favouriteButton;
    private appdatabase database;
    static Boolean isFav = false;


    private FavoritsViewModel favoritsViewModel;


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

        movieItem = (Movie) getIntent().getSerializableExtra("Movie");

        Picasso.with(DetailsActivity.this)
                .load("https://image.tmdb.org/t/p/w185/" + getIntent().getExtras().getString("poster_path"))
                .into(imageView);

        gettrailers();
        getreviews();



        favoritsViewModel = ViewModelProviders.of(this).get(FavoritsViewModel.class);


       database = appdatabase.getInstance(this);

        favouriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FavouritMovie favouritMovie = new FavouritMovie(
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
                    favoritsViewModel.delete(favouritMovie);
                    isFav = false;
                    favouriteButton.setText("add to fav");
                } else {
                    // insert item
                    favoritsViewModel.insert(favouritMovie);
                    isFav = true;
                    favouriteButton.setText("Remove from  fav");


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





