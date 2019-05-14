package com.example.popularmovie.database;


import android.content.Context;

import com.example.popularmovie.models.FavouritMovie;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {FavouritMovie.class}, version = 1,exportSchema = false)
public abstract class appdatabase extends RoomDatabase {
    private static appdatabase INSTANCE;
    private static final String DB_NAME="MyToDos";
    public abstract DAO FavDAO();

    public static appdatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (appdatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context,
                            appdatabase.class, DB_NAME)
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}