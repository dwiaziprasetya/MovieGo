package com.example.moviedatabase.data.remote.retrofit

import com.example.moviedatabase.data.remote.response.CastandCrewResponse
import com.example.moviedatabase.data.remote.response.DiscoverMovieResponse
import com.example.moviedatabase.data.remote.response.GenreResponse
import com.example.moviedatabase.data.remote.response.NowPlayingMovieResponse
import com.example.moviedatabase.data.remote.response.PopularMovieResponse
import com.example.moviedatabase.data.remote.response.UpComingMovieResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface ApiService {
    @GET("/3/movie/upcoming")
    suspend fun getUpComingMovies(): Response<UpComingMovieResponse>

    @GET("/3/movie/popular")
    suspend fun getPopularMovies(): Response<PopularMovieResponse>

    @GET("/3/movie/now_playing")
    suspend fun getNowPlayingMovies(): Response<NowPlayingMovieResponse>

    @GET("/3/discover/movie")
    suspend fun getDiscoverMovies(
        @QueryMap parameters : HashMap<String,String>
    ): Call<DiscoverMovieResponse>

    @GET("/3/movie/{movie_id}/credits")
    fun getCastMovie(
        @Path("movie_id") movieId : Int
    ): Call<CastandCrewResponse>

    @GET("/3/genre/movie/list")
    suspend fun getMovieGenre() : Response<GenreResponse>
}


