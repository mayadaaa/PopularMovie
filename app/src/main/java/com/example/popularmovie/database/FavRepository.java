package com.example.popularmovie.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.popularmovie.models.FavouritMovie;

import java.util.List;

public class FavRepository {
    private DAO FavDAO;
    private LiveData<List<FavouritMovie>> AllMovies;


    public FavRepository(Application application) {
        appdatabase database = appdatabase.getInstance(application);
        FavDAO = database.FavDAO();
        AllMovies = FavDAO.loadAllMovies();
    }


    public void insert(FavouritMovie favMovie) {
        new InsertNoteAysncTask(FavDAO).execute(favMovie);
    }


    public void update(FavouritMovie favMovie) {
        new upadteNoteAysncTask(FavDAO).execute(favMovie);

    }

    public void delete(FavouritMovie favMovie) {
        new deleteNoteAysncTask(FavDAO).execute(favMovie);

    }

    public LiveData<List<FavouritMovie>> loadAllMovies() {
        return AllMovies;

    }

    private static class InsertNoteAysncTask extends AsyncTask<FavouritMovie, Void, Void> {
        private DAO FavDAO;

        private InsertNoteAysncTask(DAO FavDAO) {
            this.FavDAO = FavDAO;


        }

        @Override
        protected Void doInBackground(FavouritMovie... favouritMovies) {
            FavDAO.insert(favouritMovies[0]);
            return null;
        }
    }


    private static class upadteNoteAysncTask extends AsyncTask<FavouritMovie, Void, Void> {
        private DAO FavDAO;

        private upadteNoteAysncTask(DAO FavDAO) {
            this.FavDAO = FavDAO;


        }

        @Override
        protected Void doInBackground(FavouritMovie... favouritMovies) {
            FavDAO.update(favouritMovies[0]);
            return null;
        }
    }

    private static class deleteNoteAysncTask extends AsyncTask<FavouritMovie, Void, Void> {
        private DAO FavDAO;

        private deleteNoteAysncTask(DAO FavDAO) {
            this.FavDAO = FavDAO;


        }

        @Override
        protected Void doInBackground(FavouritMovie... favouritMovies) {
            FavDAO.delete(favouritMovies[0]);
            return null;
        }
    }


}
