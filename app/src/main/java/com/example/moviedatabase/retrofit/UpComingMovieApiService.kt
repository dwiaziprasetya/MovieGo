package com.example.moviedatabase.retrofit

import com.example.moviedatabase.response.UpComingMovieResponse
import retrofit2.Call
import retrofit2.http.*
interface ApiService {
    @GET("/3/movie/upcoming?api_key=82256da64f15d00832814203f0657b91")
    fun getMovie(): Call<UpComingMovieResponse>
}