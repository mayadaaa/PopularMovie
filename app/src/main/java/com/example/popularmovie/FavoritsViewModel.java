package com.example.popularmovie;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.popularmovie.database.FavRepository;
import com.example.popularmovie.models.FavouritMovie;

import java.util.List;

public class FavoritsViewModel extends AndroidViewModel {
    private FavRepository repository;
    private LiveData<List<FavouritMovie>> AllMovies;


    public FavoritsViewModel(@NonNull Application application) {
        super(application);
        repository = new FavRepository(application);
        AllMovies = repository.loadAllMovies();

    }

    public void insert(FavouritMovie favMovie) {
        repository.insert(favMovie);

    }

    public void update(FavouritMovie favMovie) {
        repository.update(favMovie);

    }

    public void delete(FavouritMovie favMovie) {
        repository.delete(favMovie);
    }

    public LiveData<List<FavouritMovie>> loadAllMovies(){
  return AllMovies;
    }

}