package com.example.moviedatabase.di

import android.content.Context
import com.example.moviedatabase.data.local.database.MovieGoRoomDatabase
import com.example.moviedatabase.data.remote.retrofit.ApiConfig
import com.example.moviedatabase.data.repository.MovieDatabaseRepository

object Injection {
    fun provideRepository(context: Context): MovieDatabaseRepository {
        val apiService = ApiConfig.getApiService()
        val favouriteDatabase = MovieGoRoomDatabase.getDatabase(context).favouriteDao()
        return MovieDatabaseRepository.getInstance(apiService, favouriteDatabase)
    }
}