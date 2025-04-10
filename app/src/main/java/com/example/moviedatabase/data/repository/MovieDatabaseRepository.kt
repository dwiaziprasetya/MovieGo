package com.example.moviedatabase.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.moviedatabase.data.local.dao.FavouriteDao
import com.example.moviedatabase.data.local.entity.Favourite
import com.example.moviedatabase.data.remote.paging.DiscoverMoviePagingSource
import com.example.moviedatabase.data.remote.paging.NowPlayingMoviePagingSource
import com.example.moviedatabase.data.remote.paging.PopularMoviePagingSource
import com.example.moviedatabase.data.remote.paging.UpComingMoviePagingSource
import com.example.moviedatabase.data.remote.response.CastandCrewResponse
import com.example.moviedatabase.data.remote.response.DetailMovieResponse
import com.example.moviedatabase.data.remote.response.DiscoverMovieItem
import com.example.moviedatabase.data.remote.response.NowPlayingMovieItem
import com.example.moviedatabase.data.remote.response.NowPlayingMovieResponse
import com.example.moviedatabase.data.remote.response.PopularMovieItem
import com.example.moviedatabase.data.remote.response.PopularMovieResponse
import com.example.moviedatabase.data.remote.response.UpComingMovieItem
import com.example.moviedatabase.data.remote.response.UpComingMovieResponse
import com.example.moviedatabase.data.remote.retrofit.ApiService

class MovieDatabaseRepository(
    private val apiService: ApiService,
    private val favouriteDao: FavouriteDao
) {
    fun getDiscoverMovieDataPaging(): LiveData<PagingData<DiscoverMovieItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                DiscoverMoviePagingSource(apiService)
            }
        ).liveData
    }

    fun getNowPlayingMovieDataPaging(): LiveData<PagingData<NowPlayingMovieItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                NowPlayingMoviePagingSource(apiService)
            }
        ).liveData
    }

    fun getPopularMovieDataPaging(): LiveData<PagingData<PopularMovieItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                PopularMoviePagingSource(apiService)
            }
        ).liveData
    }

    fun getUpComingMovieDataPaging(): LiveData<PagingData<UpComingMovieItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                UpComingMoviePagingSource(apiService)
            }
        ).liveData
    }

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

    suspend fun deleteToFavourite(movieId: Int) {
        favouriteDao.deleteByMovieId(movieId)
    }

    suspend fun addToFavourite(favourite: Favourite) {
        favouriteDao.insert(favourite)
    }

    suspend fun getAllFavourites(): List<Favourite> {
        return favouriteDao.getAllFavourites()
    }

    fun isMovieFavourite(movieId: Int): LiveData<Boolean> = favouriteDao.isMovieFavourite(movieId)

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