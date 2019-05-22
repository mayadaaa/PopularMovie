package com.example.popularmovie.database;


import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.popularmovie.models.FavouritMovie;

//import com.example.popularmovie.models.FavouritMovie;

@Database(entities = {FavouritMovie.class}, version = 1,exportSchema = false)
public abstract class appdatabase extends RoomDatabase {

    private static final String LOG_TAG = appdatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String database_name = "todolist";
    private static appdatabase instance;

   /*static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE FavMovies "
                    +"ADD COLUMN address voteCount");
        }
    };*/



    public static synchronized appdatabase getInstance(Context context) {

        if (instance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new database instance ");
                instance = Room.databaseBuilder(context.getApplicationContext(),
                        appdatabase.class, appdatabase.database_name)
                       // .addMigrations(MIGRATION_1_2)
                        .build();
            }

        }
        return instance;
    }
    public abstract DAO FavDAO();
}





