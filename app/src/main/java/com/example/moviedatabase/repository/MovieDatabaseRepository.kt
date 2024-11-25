package com.example.moviedatabase.repository

import com.example.moviedatabase.data.remote.response.CastandCrewResponse
import com.example.moviedatabase.data.remote.response.DetailMovieResponse
import com.example.moviedatabase.data.remote.response.NowPlayingMovieResponse
import com.example.moviedatabase.data.remote.response.PopularMovieResponse
import com.example.moviedatabase.data.remote.response.UpComingMovieResponse
import com.example.moviedatabase.data.remote.retrofit.ApiService

class MovieDatabaseRepository(
    private val apiService: ApiService,
) {
    suspend fun getUpComingMovieData() : UpComingMovieResponse {
        return apiService.getUpComingMovies()
    }

    suspend fun getMovieCredits(movieId : Int) : CastandCrewResponse {
        return apiService.getMovieCredits(movieId)
    }

    suspend fun getMovieDetail(movieId : Int) : DetailMovieResponse {
        return apiService.getMovieDetail(movieId)
    }

    suspend fun getPopularMovieData() : PopularMovieResponse {
        return apiService.getPopularMovies()
    }

    suspend fun getNowPlayingMovieData() : NowPlayingMovieResponse {
        return apiService.getNowPlayingMovies()
    }

    companion object {
        fun getInstance(apiService: ApiService): MovieDatabaseRepository =
            MovieDatabaseRepository(apiService)
    }
}