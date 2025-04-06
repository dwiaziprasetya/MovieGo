package com.example.moviedatabase.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviedatabase.data.local.entity.Favourite

@Dao
interface FavouriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favourite: Favourite)

    @Delete
    suspend fun delete(favourite: Favourite)

    @Query("SELECT * FROM favourite")
    suspend fun getAllFavourites(): List<Favourite>

    @Query("SELECT COUNT(*) from favourite WHERE movie_name = :movieName")
    fun isMovieFavourite(movieName: String): LiveData<Boolean>
}