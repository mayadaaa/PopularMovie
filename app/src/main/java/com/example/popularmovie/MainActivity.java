package com.example.popularmovie;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import com.example.popularmovie.adapters.AdapterActivity;
import com.example.popularmovie.database.DAO;
import com.example.popularmovie.database.DatabaseClient;
import com.example.popularmovie.database.appdatabase;
import com.example.popularmovie.models.FavouritMovie;
import com.example.popularmovie.models.Movie;
import com.example.popularmovie.models.MovieDetails;
import com.example.popularmovie.network.APIinterface;

import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    int Select;
    APIinterface moviesAPI;
    public List<Movie> PopularList = new ArrayList<>();
    public List<Movie> TopRateList = new ArrayList<>();
    public List<FavouritMovie> favMovie = new ArrayList<>();

    private DatabaseClient database;
    private AdapterActivity adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private int selectedItem;
    private DAO dao;
    Parcelable listState;

    private final String STORED_MOVIES = "stored_movies";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        database = new DatabaseClient(this);

        if(savedInstanceState != null) {
            if(savedInstanceState.<FavouritMovie>getParcelableArrayList(STORED_MOVIES) != null) {
                favMovie.clear();
                favMovie.addAll(savedInstanceState.<FavouritMovie>getParcelableArrayList(STORED_MOVIES));
            }
        }
        openCall();


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STORED_MOVIES, (ArrayList<? extends Parcelable>) favMovie);
    }

 /*   @Override
    protected void onRestoreInstanceState(Bundle outState) {

        outState.pu
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (listState != null) {
            mLayoutManager.onRestoreInstanceState(listState);
        }
    }*/




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.Pouplar) {
            Select = 1;

            recyclerView.setAdapter(new AdapterActivity(PopularList, MainActivity.this));
        }
        if (id == R.id.TopRate) {
            Select = 2;
            recyclerView.setAdapter(new AdapterActivity(TopRateList, MainActivity.this));
        }
        if (id == R.id.Favourits) {
            Select = 3;
            loadfav();
        }
        return super.onOptionsItemSelected(item);
    }

    private void openCall() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIinterface.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        moviesAPI = retrofit.create(APIinterface.class);

        Call<MovieDetails> PopularReCall = moviesAPI.getApiPopular();

        PopularReCall.enqueue(new Callback<MovieDetails>() {

            @Override
            public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {

                PopularList = response.body().getResults();

            }

            @Override
            public void onFailure(Call<MovieDetails> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Please Wait Until Connection available", Toast.LENGTH_SHORT).show();
            }

        });

        Call<MovieDetails> TopRateReCall = moviesAPI.getApiTopRated();

        TopRateReCall.enqueue(new Callback<MovieDetails>() {

            @Override
            public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {


                TopRateList = response.body().getResults();
                Select = 1;
                adapter = new AdapterActivity(response.body().getResults(), MainActivity.this);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<MovieDetails> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Please Wait Until Connection available", Toast.LENGTH_SHORT).show();

            }

        });


    }

    @SuppressLint("StaticFieldLeak")

    public void loadfav() {
        recyclerView = findViewById(R.id.recyclerView);
        mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                favMovie = dao.loadAllMovies();
                return null;
            }


            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                adapter.notifyDataSetChanged();
            }

        }.execute();


    }
}













