package com.example.moviedatabase.data.remote.retrofit

import com.example.moviedatabase.data.remote.response.CastandCrewResponse
import com.example.moviedatabase.data.remote.response.DiscoverMovieResponse
import com.example.moviedatabase.data.remote.response.GenreResponse
import com.example.moviedatabase.data.remote.response.NowPlayingMovieResponse
import com.example.moviedatabase.data.remote.response.PopularMovieResponse
import com.example.moviedatabase.data.remote.response.UpComingMovieResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
interface ApiService {
    @GET("/3/movie/upcoming?api_key=82256da64f15d00832814203f0657b91")
    suspend fun getUpComingMovies(): Response<UpComingMovieResponse>

    @GET("/3/movie/popular?api_key=82256da64f15d00832814203f0657b91")
    suspend fun getPopularMovies(): Response<PopularMovieResponse>

    @GET("/3/movie/now_playing?api_key=82256da64f15d00832814203f0657b91")
    suspend fun getNowPlayingMovies(): Response<NowPlayingMovieResponse>

    @GET("/3/discover/movie?api_key=82256da64f15d00832814203f0657b91")
    fun getDiscoverMovies(
        @QueryMap parameters : HashMap<String,String>
    ): Call<DiscoverMovieResponse>

    @GET("/3/movie/{movie_id}/credits?api_key=82256da64f15d00832814203f0657b91")
    fun getCastMovie(
        @Path("movie_id") movieId : Int
    ): Call<CastandCrewResponse>

    @GET("/3/genre/movie/list?api_key=82256da64f15d00832814203f0657b91")
    fun getMovieGenre() : Call<GenreResponse>
}


