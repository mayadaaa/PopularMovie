package com.example.popularmovie;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {FavouritMovie.class}, version = 1, exportSchema = false)
public abstract class FavDatabase extends RoomDatabase {

    public abstract DAO FavDAO();
}