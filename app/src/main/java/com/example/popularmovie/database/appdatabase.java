package com.example.popularmovie.database;


import com.example.popularmovie.models.FavouritMovie;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {FavouritMovie.class}, version = 1)
public abstract class appdatabase extends RoomDatabase {
    public abstract DAO FavDAO();
}