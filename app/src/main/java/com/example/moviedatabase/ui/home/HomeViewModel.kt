package com.example.moviedatabase.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviedatabase.response.NowPlayingMovieItem
import com.example.moviedatabase.response.NowPlayingMovieResponse
import com.example.moviedatabase.response.PopularMovieItem
import com.example.moviedatabase.response.PopularMovieResponse
import com.example.moviedatabase.response.UpComingMovieItems
import com.example.moviedatabase.response.UpComingMovieResponse
import com.example.moviedatabase.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {
    private val _dataUpComingMovie = MutableLiveData<List<UpComingMovieItems>>()
    val dataUpComingMovieItems: LiveData<List<UpComingMovieItems>> = _dataUpComingMovie

    private val _dataPopularMovie = MutableLiveData<List<PopularMovieItem>>()
    val dataPopularMovieItem: LiveData<List<PopularMovieItem>> = _dataPopularMovie

    private val _dataNowPlayingMovie = MutableLiveData<List<NowPlayingMovieItem>>()
    val dataNowPlayingMovieItem: LiveData<List<NowPlayingMovieItem>> = _dataNowPlayingMovie

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        getUpComingMovieData()
        getPopularMovieData()
        getNowPlayingMovieData()
    }

    private fun getNowPlayingMovieData() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getNowPlayingMovies()
        client.enqueue(object: Callback<NowPlayingMovieResponse> {
            override fun onResponse(
                call: Call<NowPlayingMovieResponse>,
                response: Response<NowPlayingMovieResponse>,
            ) {
                _isLoading.value = false
                if (response.isSuccessful) _dataNowPlayingMovie.value = response.body()?.results
            }

            override fun onFailure(call: Call<NowPlayingMovieResponse>, t: Throwable) {
                _isLoading.value = false
            }

        })
    }

    private fun getPopularMovieData() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getPopularMovies()
        client.enqueue(object : Callback<PopularMovieResponse> {
            override fun onResponse(
                call: Call<PopularMovieResponse>,
                response: Response<PopularMovieResponse>,
            ) {
                _isLoading.value = false
                if (response.isSuccessful) _dataPopularMovie.value = response.body()?.results
            }

            override fun onFailure(call: Call<PopularMovieResponse>, t: Throwable) {
               _isLoading.value = false
            }
        })
    }

    private fun getUpComingMovieData() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUpComingMovies()
        client.enqueue(object : Callback<UpComingMovieResponse> {
            override fun onResponse(call: Call<UpComingMovieResponse>, response: Response<UpComingMovieResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) _dataUpComingMovie.value = response.body()?.results
            }

            override fun onFailure(call: Call<UpComingMovieResponse>, t: Throwable) {
                _isLoading.value = false
            }
        })
    }
}