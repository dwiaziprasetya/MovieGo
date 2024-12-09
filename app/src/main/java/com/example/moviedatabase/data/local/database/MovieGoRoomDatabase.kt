package com.example.moviedatabase.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.moviedatabase.data.local.dao.FavouriteDao
import com.example.moviedatabase.data.local.entity.Favourite

@Database(entities = [Favourite::class, ], version = 1)
abstract class MovieGoRoomDatabase : RoomDatabase() {
    abstract fun favouriteDao(): FavouriteDao

    companion object {
        @Volatile
        private var INSTANCE: MovieGoRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): MovieGoRoomDatabase {
            if (INSTANCE == null) {
                synchronized(MovieGoRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        MovieGoRoomDatabase::class.java, "moviego_database")
                        .build()
                }
            }
            return INSTANCE as MovieGoRoomDatabase
        }
    }
}