package com.example.moviedatabase.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.moviedatabase.adapter.RvCastMovieAdapter
import com.example.moviedatabase.databinding.ActivityDetailBinding
import com.example.moviedatabase.response.CastItem
import com.example.moviedatabase.response.CastandCrewResponse
import com.example.moviedatabase.response.DiscoverMovieResultsItem
import com.example.moviedatabase.response.GenreResponse
import com.example.moviedatabase.response.GenresItem
import com.example.moviedatabase.retrofit.ApiConfig
import com.example.moviedatabase.ui.home.HomeFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_MOVIES = "extra_movies"
    }

    private lateinit var binding : ActivityDetailBinding
    private var adapter = RvCastMovieAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        WindowCompat.setDecorFitsSystemWindows(window,false)
        val movie = intent.getParcelableExtra<DiscoverMovieResultsItem>(EXTRA_MOVIES)
        val movieId = movie?.id!!

        setData(movie)

        showRecycleView()
        getMovieCast(movieId)
        getMovieGenre(movie.genreIds)
    }

    private fun getMovieGenre(movieGenreIds: List<Int>){
        val client = ApiConfig.getApiService().getMovieGenre()
        client.enqueue(object : Callback<GenreResponse> {
            override fun onResponse(call: Call<GenreResponse>, response: Response<GenreResponse>) {
                if (response.isSuccessful){
                    val responseBody = response.body()
                    if (responseBody != null){
                        val genres = responseBody.genres
                        val movieGenre = genres.filter { it.id in movieGenreIds}
                        val genreNames = movieGenre.map { it.name }
                        binding.tvDetailMovieRelease.text = genreNames.joinToString(", ")
                    }
                }
            }

            override fun onFailure(call: Call<GenreResponse>, t: Throwable) {

            }

        })
    }

    private fun setData(movie: DiscoverMovieResultsItem){
        val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500/"
        binding.tvDetailMovieName.text = movie.title
        binding.ratingBar.rating = movie.movieRate().toFloat()
        binding.tvDetailMovieOverviewData.text = movie.overview
        Glide.with(baseContext)
            .load(IMAGE_BASE_URL + movie.backdropPath)
            .fitCenter()
            .into(binding.imgDetailMoviePhoto)
    }

    private fun getMovieCast(id : Int){
        val client = ApiConfig.getApiService().getCastMovie(id)
        client.enqueue(object : Callback<CastandCrewResponse> {
            override fun onResponse(
                call: Call<CastandCrewResponse>,
                response: Response<CastandCrewResponse>,
            ) {
                if (response.isSuccessful){
                    showLoading(false)
                    val responseBody = response.body()
                    if (responseBody != null){
                        for (i in 0 until responseBody.cast.size){
                            Log.d("DetailActivity",  "item cast items: ${responseBody.cast[i]}")
                        }
                        setCaster(responseBody.cast.take(6))
                    } else {
                        Log.e("DetailActvity", "onFailure : ${response.message()}")
                    }
                }
            }

            override fun onFailure(call: Call<CastandCrewResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun showRecycleView(){
        binding.rvCastMovie.layoutManager = LinearLayoutManager(this@DetailActivity, LinearLayoutManager.HORIZONTAL, false)
        binding.rvCastMovie.hasFixedSize()
        binding.rvCastMovie.adapter = adapter
    }

    private fun setCaster(cast : List<CastItem>){
        adapter.submitList(cast)
    }

    private fun showLoading(isLoading : Boolean){
        if (isLoading){
            binding.pbCaster.visibility = View.VISIBLE
        } else {
            binding.pbCaster.visibility = View.GONE
        }
    }
}