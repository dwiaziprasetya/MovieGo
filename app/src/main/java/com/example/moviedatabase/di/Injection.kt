package com.example.moviedatabase.di

import com.example.moviedatabase.data.remote.retrofit.ApiConfig
import com.example.moviedatabase.repository.MovieDatabaseRepository

object Injection {
    fun provideRepository(): MovieDatabaseRepository {
        val apiService = ApiConfig.getApiService()
        return MovieDatabaseRepository.getInstance(apiService)
    }
}