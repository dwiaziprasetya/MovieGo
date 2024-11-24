package com.example.moviedatabase.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviedatabase.data.remote.response.CastandCrewResponse
import com.example.moviedatabase.data.remote.response.DetailMovieResponse
import com.example.moviedatabase.data.remote.response.NowPlayingMovieItem
import com.example.moviedatabase.data.remote.response.PopularMovieItem
import com.example.moviedatabase.data.remote.response.UpComingMovieItem
import com.example.moviedatabase.data.remote.retrofit.ApiConfig

class MovieDatabaseRepository {
    private val dataNowPlayingMovie = MutableLiveData<List<NowPlayingMovieItem>>()
    private val dataPopularMovie = MutableLiveData<List<PopularMovieItem>>()
    private val dataUpComingMovieItem = MutableLiveData<List<UpComingMovieItem>>()
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    suspend fun getMovieCredits(movieId : Int) : CastandCrewResponse? {
        return try {
            val response = ApiConfig.getApiService().getMovieCredits(movieId)
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            Log.d("Home Repository", "Get Movie Credits : ${e.message}")
            null
        }
    }

    suspend fun getMovieDetail(movieId : Int) : DetailMovieResponse? {
        return try {
            val response = ApiConfig.getApiService().getMovieDetail(movieId)
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            Log.d("Home Repository", "Get Movie Detail : ${e.message}")
            null
        }
    }

    suspend fun getUpComingMovieData() : LiveData<List<UpComingMovieItem>>{
        _isLoading.postValue(true)
        try {
            val response = ApiConfig.getApiService().getUpComingMovies()
            if (response.isSuccessful){
                dataUpComingMovieItem.postValue(response.body()?.results)
            }
        } catch (e: Exception) {
            Log.d("Home Repository", "Get UpComingMovies : ${e.message}")
        } finally {
            _isLoading.postValue(false)
        }
        return dataUpComingMovieItem
    }

    suspend fun getNowPlayingMovieData() : LiveData<List<NowPlayingMovieItem>> {
        _isLoading.postValue(true)
        try {
            val response = ApiConfig.getApiService().getNowPlayingMovies()
            if (response.isSuccessful){
                dataNowPlayingMovie.postValue(response.body()?.results)
            }
        } catch (e: Exception) {
            Log.d("Home Repository", "GetNowPlayingMovies : ${e.message}")
        } finally {
            _isLoading.postValue(false)
        }
        return dataNowPlayingMovie
    }

    suspend fun getPopularMovieData() : LiveData<List<PopularMovieItem>> {
        _isLoading.postValue(true)
        try {
            val response = ApiConfig.getApiService().getPopularMovies()
            if (response.isSuccessful){
                dataPopularMovie.postValue(response.body()?.results)
            }
        } catch (e: Exception) {
            Log.d("Home Repository", "GetPopularMovies : ${e.message}")
        } finally {
            _isLoading.postValue(false)
        }
        return dataPopularMovie
    }
}