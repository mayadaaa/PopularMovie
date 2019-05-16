package com.example.popularmovie.database;


import android.content.Context;
import android.os.AsyncTask;

import com.example.popularmovie.models.FavouritMovie;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {FavouritMovie.class}, version = 1,exportSchema = false)
public abstract class appdatabase extends RoomDatabase {

private static appdatabase instance;

    public abstract DAO FavDAO();

    public static synchronized appdatabase getInstance(Context context){

        if(instance==null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    appdatabase.class, "fav_Dtatabase")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();

        }
            return instance ;
        }
        private static RoomDatabase.Callback roomCallback=new RoomDatabase.Callback(){

            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                new PopulateDbAsyncTask(instance).execute();
            }
        };

    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void>{
private DAO FavDAO;


private PopulateDbAsyncTask(appdatabase db){
    FavDAO=db.FavDAO();
}
        @Override
        protected Void doInBackground(Void... voids) {
   FavDAO.loadAllMovies();
            return null;
        }
    }
    }





