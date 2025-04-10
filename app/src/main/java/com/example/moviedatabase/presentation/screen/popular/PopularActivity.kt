package com.example.moviedatabase.presentation.screen.popular

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.moviedatabase.databinding.ActivityPopularBinding
import com.example.moviedatabase.presentation.adapter.PagingRvPopularMovieAdapter
import com.example.moviedatabase.presentation.adapter.ShimmerItemMovieAdapter
import com.example.moviedatabase.presentation.screen.detail.DetailActivity
import com.example.moviedatabase.utils.ViewModelFactory

class PopularActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPopularBinding
    private lateinit var factory: ViewModelFactory
    private val viewModel by viewModels<PopularViewModel> { factory }
    private val adapter = PagingRvPopularMovieAdapter()
    private lateinit var shimmerAdapter : ShimmerItemMovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPopularBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(
                Color.TRANSPARENT,
            )
        )
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        adapter.setOnItemClickCallback(object : PagingRvPopularMovieAdapter.OnitemClickCallback {
            override fun onItemClicked(movieId: Int) {
                val intent = Intent(this@PopularActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.MOVIE_ID, movieId)
                startActivity(intent)
            }
        })

        factory = ViewModelFactory.getInstance(this)
        showRecyclerViewPopularMovie()
        setupShimmer()
    }

    private fun setupShimmer() {

    }

    private fun showRecyclerViewPopularMovie() {

    }
}