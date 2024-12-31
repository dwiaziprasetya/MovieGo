package com.example.moviedatabase.repository

import com.example.moviedatabase.data.local.dao.FavouriteDao
import com.example.moviedatabase.data.local.entity.Favourite
import com.example.moviedatabase.data.remote.response.CastandCrewResponse
import com.example.moviedatabase.data.remote.response.DetailMovieResponse
import com.example.moviedatabase.data.remote.response.NowPlayingMovieResponse
import com.example.moviedatabase.data.remote.response.PopularMovieResponse
import com.example.moviedatabase.data.remote.response.UpComingMovieResponse
import com.example.moviedatabase.data.remote.retrofit.ApiService

class MovieDatabaseRepository(
    private val apiService: ApiService,
    private val favouriteDao: FavouriteDao
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

    suspend fun addToFavourite(favourite: Favourite) {
        favouriteDao.insert(favourite)
    }

    suspend fun getAllFavourites() : List<Favourite> {
        return favouriteDao.getAllFavourites()
    }

    companion object {
        fun getInstance(
            apiService: ApiService,
            favouriteDao: FavouriteDao
        ): MovieDatabaseRepository =
            MovieDatabaseRepository(
                apiService,
                favouriteDao
            )
    }
}