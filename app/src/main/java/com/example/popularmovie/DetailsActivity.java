package com.example.popularmovie;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity {

    private static final String TAG = MovieDetails.class.getSimpleName();

    public static final String ADD_Opeartion = "add";
    public static final String Delete_Opeartion = "delete";

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

    public Button favouriteButton;
    private DatabaseClient database;
    static Boolean isFav = false;


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
        if (movieItem == null)
            Log.e("null", "Null");
        Picasso.with(DetailsActivity.this)
                .load("http://image.tmdb.org/t/p/w185/" + getIntent().getExtras().getString("poster_path"))
                .into(imageView);

        gettrailers();
        getreviews();

        database = new DatabaseClient(this);
        DetailsActivity.GetFav gt = new DetailsActivity.GetFav(getApplicationContext(), movieItem.getId());
        gt.execute();

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
                    SaveTask st = new SaveTask(favouritMovie
                            , getApplicationContext(), Delete_Opeartion);
                    st.execute();
                    isFav = false;
                    favouriteButton.setText("add to fav");
                } else {
                    // insert item
                    SaveTask st = new SaveTask(favouritMovie, getApplicationContext(), ADD_Opeartion);
                    st.execute();
                    Toast.makeText(DetailsActivity.this, "added", Toast.LENGTH_SHORT).show();
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

    class GetFav extends AsyncTask<Void, Void, FavouritMovie> {

        Context context;
        int id;

        GetFav(Context context, int id) {
            this.context = context;
            this.id = id;
        }

        @Override
        protected FavouritMovie doInBackground(Void... voids) {
            FavouritMovie taskList = DatabaseClient
                    .getInstance(this.context)
                    .getAppDatabase()
                    .FavDAO().loadMovieById(this.id);
            return taskList;
        }

        @Override
        protected void onPostExecute(FavouritMovie FavouritMovie) {
            super.onPostExecute(FavouritMovie);
            if (FavouritMovie == null) {
                DetailsActivity.isFav = false;
                favouriteButton.setText("add to fav");
            } else {
                DetailsActivity.isFav = true;
                favouriteButton.setText("Remove from fav");

            }
        }
    }

    class SaveTask extends AsyncTask<Void, Void, Void> {
        FavouritMovie favouritMovie;
        Context context;
        String operationType;

        SaveTask(FavouritMovie favouritMovie, Context context, String operationType) {
            this.favouritMovie = favouritMovie;
            this.context = context;
            this.operationType = operationType;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            if (operationType == DetailsActivity.ADD_Opeartion) {
                //adding to database
                DatabaseClient.getInstance(this.context).getAppDatabase()
                        .FavDAO()
                        .insert(this.favouritMovie);

            } else {
                DatabaseClient.getInstance(this.context).getAppDatabase()
                        .FavDAO()
                        .delete(this.favouritMovie);

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(context, operationType, Toast.LENGTH_LONG).show();

        }


    }
}



