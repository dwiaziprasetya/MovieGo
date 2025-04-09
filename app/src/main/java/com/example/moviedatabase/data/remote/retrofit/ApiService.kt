package com.example.moviedatabase.data.remote.retrofit

import com.example.moviedatabase.data.remote.response.CastandCrewResponse
import com.example.moviedatabase.data.remote.response.DetailMovieResponse
import com.example.moviedatabase.data.remote.response.DiscoverMovieResponse
import com.example.moviedatabase.data.remote.response.NowPlayingMovieResponse
import com.example.moviedatabase.data.remote.response.PopularMovieResponse
import com.example.moviedatabase.data.remote.response.UpComingMovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/3/discover/movie")
    suspend fun getDiscoverMovies(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 5
    ): DiscoverMovieResponse

    @GET("/3/movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 5
    ):  NowPlayingMovieResponse

    @GET("/3/movie/popular")
    suspend fun getPopularMovies(): PopularMovieResponse

    @GET("/3/movie/upcoming")
    suspend fun getUpComingMovies(): UpComingMovieResponse

    @GET("/3/movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId : Int
    ): DetailMovieResponse

    @GET("/3/movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path("movie_id") movieId : Int
    ) : CastandCrewResponse
}


