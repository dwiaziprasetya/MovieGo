package com.example.moviedatabase.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.moviedatabase.databinding.ActivityDetailBinding
import com.example.moviedatabase.response.DiscoverMovieResultsItem
import java.io.Serializable

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_MOVIES = "extra_movies"
    }

    private lateinit var binding : ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movie = intent.getParcelableExtra<DiscoverMovieResultsItem>(EXTRA_MOVIES)

        if (movie != null){
            setData(movie)
        }

    }

    private fun setData(movie: DiscoverMovieResultsItem){
        val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500/"
        binding.tvDetailMovieName.text = movie.title
        binding.tvDetailMovieRelease.text = movie.releaseDate
        binding.ratingBar.rating = movie.movieRate().toFloat()
        binding.tvDetailMovieOverviewData.text = movie.overview
        Glide.with(baseContext)
            .load(IMAGE_BASE_URL + movie.backdropPath)
            .fitCenter()
            .into(binding.imgDetailMoviePhoto)
    }
}