package com.example.moviedatabase.data.remote.retrofit

import com.example.moviedatabase.data.remote.response.CastandCrewResponse
import com.example.moviedatabase.data.remote.response.DetailMovieResponse
import com.example.moviedatabase.data.remote.response.NowPlayingMovieResponse
import com.example.moviedatabase.data.remote.response.PopularMovieResponse
import com.example.moviedatabase.data.remote.response.UpComingMovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("/3/movie/upcoming")
    suspend fun getUpComingMovies(): Response<UpComingMovieResponse>

    @GET("/3/movie/popular")
    suspend fun getPopularMovies(): Response<PopularMovieResponse>

    @GET("/3/movie/now_playing")
    suspend fun getNowPlayingMovies(): Response<NowPlayingMovieResponse>

    @GET("/3/movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId : Int
    ): Response<DetailMovieResponse>

    @GET("/3/movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path("movie_id") movieId : Int
    ) : Response<CastandCrewResponse>
}


