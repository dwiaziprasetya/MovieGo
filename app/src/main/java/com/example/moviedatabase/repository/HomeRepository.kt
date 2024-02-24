package com.example.moviedatabase.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

class HomeRepository {
    private val dataNowPlayingMovie = MutableLiveData<List<NowPlayingMovieItem>>()
    private val dataPopularMovie = MutableLiveData<List<PopularMovieItem>>()
    private val dataUpComingMovieItems = MutableLiveData<List<UpComingMovieItems>>()
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUpComingMovieData() : LiveData<List<UpComingMovieItems>> {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUpComingMovies()
        client.enqueue(object : Callback<UpComingMovieResponse> {
            override fun onResponse(call: Call<UpComingMovieResponse>, response: Response<UpComingMovieResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) dataUpComingMovieItems.value = response.body()?.results
            }

            override fun onFailure(call: Call<UpComingMovieResponse>, t: Throwable) {
                _isLoading.value = false
            }
        })

        return dataUpComingMovieItems
    }

    fun getNowPlayingMovieData() : LiveData<List<NowPlayingMovieItem>> {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getNowPlayingMovies()
        client.enqueue(object: Callback<NowPlayingMovieResponse> {
            override fun onResponse(
                call: Call<NowPlayingMovieResponse>,
                response: Response<NowPlayingMovieResponse>,
            ) {
                _isLoading.value = false
                if (response.isSuccessful) dataNowPlayingMovie.value = response.body()?.results
            }

            override fun onFailure(call: Call<NowPlayingMovieResponse>, t: Throwable) {
                _isLoading.value = false
            }
        })

        return dataNowPlayingMovie
    }

    fun getPopularMovieData() : LiveData<List<PopularMovieItem>> {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getPopularMovies()
        client.enqueue(object : Callback<PopularMovieResponse> {
            override fun onResponse(
                call: Call<PopularMovieResponse>,
                response: Response<PopularMovieResponse>,
            ) {
                _isLoading.value = false
                if (response.isSuccessful) dataPopularMovie.value = response.body()?.results
            }

            override fun onFailure(call: Call<PopularMovieResponse>, t: Throwable) {
                _isLoading.value = false
            }
        })

        return dataPopularMovie
    }
}