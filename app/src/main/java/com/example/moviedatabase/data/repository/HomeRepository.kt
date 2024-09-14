package com.example.moviedatabase.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviedatabase.data.remote.response.NowPlayingMovieItem
import com.example.moviedatabase.data.remote.response.PopularMovieItem
import com.example.moviedatabase.data.remote.response.UpComingMovieItem
import com.example.moviedatabase.data.remote.retrofit.ApiConfig

class HomeRepository {
    private val dataNowPlayingMovie = MutableLiveData<List<NowPlayingMovieItem>>()
    private val dataPopularMovie = MutableLiveData<List<PopularMovieItem>>()
    private val dataUpComingMovieItem = MutableLiveData<List<UpComingMovieItem>>()
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    suspend fun getUpComingMovieData() : LiveData<List<UpComingMovieItem>>{
        _isLoading.postValue(true)
        try {
            val response = ApiConfig.getApiService().getUpComingMovies()
            if (response.isSuccessful){
                Log.d("UpComingMovie", "run in ${Thread.currentThread().name}")
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
                Log.d("NowPlayingMovie", "run in ${Thread.currentThread().name}")
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
                Log.d("PopularMovie", "run in ${Thread.currentThread().name}")
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