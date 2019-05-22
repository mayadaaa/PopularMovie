package com.example.popularmovie;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.popularmovie.database.appdatabase;
import com.example.popularmovie.models.FavouritMovie;

//import com.example.popularmovie.models.FavouritMovie;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private LiveData<List<FavouritMovie>> favS;

    public MainViewModel(@NonNull Application application) {
        super(application);
        appdatabase database=appdatabase.getInstance(this.getApplication());
        favS=database.FavDAO().loadAllMovies();

    }
    public LiveData<List<FavouritMovie>>getFavS(){
        return favS;
    }


}
