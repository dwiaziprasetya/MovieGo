package com.example.moviedatabase.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.moviedatabase.data.MoviePagingSource
import com.example.moviedatabase.data.local.dao.FavouriteDao
import com.example.moviedatabase.data.local.entity.Favourite
import com.example.moviedatabase.data.remote.response.CastandCrewResponse
import com.example.moviedatabase.data.remote.response.DetailMovieResponse
import com.example.moviedatabase.data.remote.response.DiscoverMovieItem
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

    fun getDiscoverMovieDataPaging(): LiveData<PagingData<DiscoverMovieItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                MoviePagingSource(apiService)
            }
        ).liveData
    }

//    suspend fun getDiscoverMovieData() : DiscoverMovieResponse {
//        return apiService.getDiscoverMovies()
//    }

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