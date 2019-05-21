package com.example.popularmovie;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import com.example.popularmovie.adapters.AdapterActivity;
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


    APIinterface moviesAPI;
    List<Movie> PopularList = new ArrayList<>();
    List<Movie> TopRateList = new ArrayList<>();
    List<FavouritMovie> Favourites = new ArrayList<>();
    RecyclerView recyclerView;


    private AdapterActivity adapter;
    private appdatabase mDb;
    private static final String TAG = MainActivity.class.getSimpleName();

    //for save instance state
    private final static String MENU_SELECTED = "selected";
    int Select;
   private MenuItem menuItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);


        if (savedInstanceState != null) {
            Select = savedInstanceState.getInt(MENU_SELECTED);
        }

        mDb = appdatabase.getInstance(getApplicationContext());
        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
        openCall();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt(MENU_SELECTED, Select);
        super.onSaveInstanceState(savedInstanceState);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);


        switch (Select) {
            case R.id.Pouplar:
                menuItem = (MenuItem) menu.findItem(R.id.Pouplar);
                menuItem.setChecked(true);
                break;

            case R.id.TopRate:
                menuItem = (MenuItem) menu.findItem(R.id.TopRate);
                menuItem.setChecked(true);
                break;

            case R.id.Favourits:
                menuItem = (MenuItem) menu.findItem(R.id.Favourits);
                menuItem.setChecked(true);
                break;
        }


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.Pouplar) {
            Select = 1;
            item.setChecked(true);
            recyclerView.setAdapter(new AdapterActivity(PopularList, MainActivity.this));
        }
        if (id == R.id.TopRate) {
            Select = 2;
            item.setChecked(true);
            recyclerView.setAdapter(new AdapterActivity(TopRateList, MainActivity.this));
        }
        if (id == R.id.Favourits) {
            Select = 3;
            item.setChecked(true);
            setupViewModel();
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupViewModel() {
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getFavS().observe(this, new Observer<List<FavouritMovie>>() {
            @Override
            public void onChanged(List<FavouritMovie> Favourites) {
                Log.d(TAG, "retrieve data from LiveDtata in viewModel");

                for (int i = 0; i < Favourites.size(); i++) {
                    FavouritMovie mov = new FavouritMovie(Favourites.get(i).getId(),
                            Favourites.get(i).getTitle(),
                            Favourites.get(i).getReleaseDate(),
                            Favourites.get(i).getVoteAverage(),
                            Favourites.get(i).getPopularity(),
                            Favourites.get(i).getOverview(),
                            Favourites.get(i).getPosterPath(),
                            Favourites.get(i).getBackdropPath()
                    );

                    Favourites.add(mov);
                }
                adapter.setTasks(Favourites);

            }
        });


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



