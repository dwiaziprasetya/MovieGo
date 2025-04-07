package com.example.moviedatabase.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviedatabase.data.local.entity.Favourite

@Dao
interface FavouriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favourite: Favourite)

    @Query("DELETE FROM favourite WHERE movie_id = :movieId")
    suspend fun deleteByMovieId(movieId: Int)

    @Query("SELECT * FROM favourite")
    suspend fun getAllFavourites(): List<Favourite>

    @Query("SELECT EXISTS(SELECT 1 FROM favourite WHERE movie_id = :movieId)")
    fun isMovieFavourite(movieId: Int): LiveData<Boolean>
}