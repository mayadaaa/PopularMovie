package com.example.popularmovie.database;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

//import com.example.popularmovie.models.FavouritMovie;


import com.example.popularmovie.models.FavouritMovie;

import java.util.List;
@Dao
public interface DAO {
    @Query("SELECT * FROM FavMovies ORDER BY id")
    LiveData<List<FavouritMovie>> loadAllMovies();

    @Insert
    void insert(FavouritMovie favMovie);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(FavouritMovie favMovie);

    @Delete
    void delete(FavouritMovie favMovie);

    @Query("SELECT * FROM FavMovies WHERE id = :id")
    FavouritMovie loadMovieById(int id);
}

