package com.example.moviedatabase.data.repository

import androidx.lifecycle.MutableLiveData
import com.example.moviedatabase.data.remote.response.DiscoverMovieItem
import com.example.moviedatabase.data.remote.response.GenreItem

class MovieRepository {
    private val dataDiscoverMovie = MutableLiveData<List<DiscoverMovieItem>>()
    private val dataGenre = MutableLiveData<List<GenreItem>>()

//    suspend fun getDiscoverMovie(): LiveData<List<DiscoverMovieItem>> {
//
//    }
}