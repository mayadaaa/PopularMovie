package com.example.popularmovie;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class MainActivity extends AppCompatActivity {

    int Select;
    APIinterface moviesAPI;
    List<Movie> PopularList = new ArrayList<>();
    List<Movie> TopRateList = new ArrayList<>();
  @BindView (R.id.recyclerView) RecyclerView recyclerView;

    private AdapterActivity adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        openCall();
    }

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


}



