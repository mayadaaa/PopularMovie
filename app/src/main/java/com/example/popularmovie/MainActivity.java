package com.example.popularmovie;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import com.example.popularmovie.adapters.AdapterActivity;
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
    public List<Movie> PopularList = new ArrayList<>();
    public List<Movie> TopRateList = new ArrayList<>();
    public List<FavouritMovie> favouritMovies = new ArrayList<>();

    //private DatabaseClient database;
    private AdapterActivity adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;


    private final static String MENU_SELECTED = "selected";
    private int Select = -1;

    private MenuItem menuItem;

    //  private final String STORED_MOVIES = "stored_movies";
//THE NEW CODE
    private FavoritsViewModel favoritsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        //  database = new DatabaseClient(this);

        //THE NEW CODE

        favoritsViewModel = ViewModelProviders.of(this).get(FavoritsViewModel.class);



        if (savedInstanceState != null) {

            Select = savedInstanceState.getInt(MENU_SELECTED);
            //  favMovie.clear();
            //favMovie.addAll(savedInstanceState.<FavouritMovie>getParcelableArrayList(STORED_MOVIES));

        }


        openCall();


    }

  /* public void setData(List<FavouritMovie> favMovie ) {
        this.favMovie = favMovie;
        notifyDataSetChanged();
    }*/


  /*  @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt(MENU_SELECTED, Select);

        super.onSaveInstanceState(savedInstanceState);

        //outState.putParcelableArrayList(STORED_MOVIES, (ArrayList<? extends Parcelable>) favMovie);


    }*/

    /*  @Override
      protected void onRestoreInstanceState(Bundle savedInstanceState) {
          Select = savedInstanceState.getInt(MENU_SELECTED);
      }
      */
/*
    @Override
    protected void onResume() {
        super.onResume();

        if (listState != null) {
            mLayoutManager.onRestoreInstanceState(listState);
        }
    }*/
/*

  if (mSavedInstanceState != null && selected == mSavedInstanceState.getInt(BUNDLE_PREF)) {
            if (selected == FAVORITES) {
                mBinding.moviesList.clearOnScrollListeners();
            } else {
                mScrollListener.setState(
                        mSavedInstanceState.getInt(BUNDLE_PAGE),
                        mSavedInstanceState.getInt(BUNDLE_COUNT));
                mBinding.moviesList.addOnScrollListener(mScrollListener);
            }
            mGridLayoutManager
                    .onRestoreInstanceState(mSavedInstanceState.getParcelable(BUNDLE_RECYCLER));
        } else {
            if (selected == FAVORITES) {
                mBinding.moviesList.clearOnScrollListeners();
            } else {
                mScrollListener.resetState();
                mBinding.moviesList.addOnScrollListener(mScrollListener);
            }
        }
    }



 */





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);


        if (Select == -1) {
            return true;
        }

        switch (Select) {
            case R.id.Pouplar:
                menuItem = menu.findItem(R.id.Pouplar);
                menuItem.setChecked(true);
                break;

            case R.id.TopRate:
                menuItem = menu.findItem(R.id.TopRate);
                menuItem.setChecked(true);
                break;

            case R.id.Favourits:
                menuItem = menu.findItem(R.id.Favourits);
                menuItem.setChecked(true);
                break;
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.Pouplar) {
            Select = id;
            item.setChecked(true);
            recyclerView.setAdapter(new AdapterActivity(PopularList, MainActivity.this));
        }
        if (id == R.id.TopRate) {

            Select = id;
            item.setChecked(true);
            recyclerView.setAdapter(new AdapterActivity(TopRateList, MainActivity.this));
        }
        if (id == R.id.Favourits) {
            Select = id;
            item.setChecked(true);
            favoritsViewModel.loadAllMovies().observe(this, new Observer<List<FavouritMovie>>() {
                @Override
                public void onChanged(List<FavouritMovie> favouritMovies) {
                    adapter.addMoviesList(favouritMovies);
                }


            });

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

 /*   @SuppressLint("StaticFieldLeak")

    public void loadfav() {
        recyclerView = findViewById(R.id.recyclerView);
        mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
            //    favMovie = dao.loadAllMovies();
                //Toast();
                return null;
            }


            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                adapter.notifyDataSetChanged();
            }

        }.execute();


    }*/
}













