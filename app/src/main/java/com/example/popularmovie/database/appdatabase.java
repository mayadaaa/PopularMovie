package com.example.popularmovie.database;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.popularmovie.models.FavouritMovie;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {FavouritMovie.class}, version = 1,exportSchema = false)
public abstract class appdatabase extends RoomDatabase {

    private static final String LOG_TAG = appdatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String database_name = "todolist";
    private static appdatabase instance;



    public static synchronized appdatabase getInstance(Context context) {

        if (instance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new database instance ");
                instance = Room.databaseBuilder(context.getApplicationContext(),
                        appdatabase.class, appdatabase.database_name)
                        .build();
            }

        }
        return instance;
    }
    public abstract DAO FavDAO();
}





