package com.example.moviedatabase.presentation.screen.nowplaying

import android.content.Intent
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviedatabase.R
import com.example.moviedatabase.databinding.ActivityNowPlayingBinding
import com.example.moviedatabase.presentation.adapter.LoadingStateAdapter
import com.example.moviedatabase.presentation.adapter.PagingRvNowPlayingMovieAdapter
import com.example.moviedatabase.presentation.adapter.ShimmerItemMovieAdapter
import com.example.moviedatabase.presentation.screen.detail.DetailActivity
import com.example.moviedatabase.utils.ViewModelFactory

class NowPlayingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNowPlayingBinding
    private lateinit var factory: ViewModelFactory
    private val viewModel by viewModels<NowPlayingViewModel> { factory }
    private val adapter = PagingRvNowPlayingMovieAdapter()
    private lateinit var shimmerAdapter : ShimmerItemMovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNowPlayingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(
                android.graphics.Color.TRANSPARENT,
            )
        )
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        adapter.setOnItemClickCallback(object : PagingRvNowPlayingMovieAdapter.OnitemClickCallback {
            override fun onItemClicked(movieId: Int) {
                val intent = Intent(this@NowPlayingActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.MOVIE_ID, movieId)
                startActivity(intent)
            }
        })

        factory = ViewModelFactory.getInstance(this)
        showRecyclerViewNowPlayingMovie()
        setupShimmer()

        binding.swipeRefreshLayout.setOnRefreshListener {
            adapter.refresh()
        }

        binding.swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.background_theme)

        binding.swipeRefreshLayout.setColorSchemeResources(
            R.color.red_netflix,
        )
    }

    private fun setupShimmer() {
        shimmerAdapter = ShimmerItemMovieAdapter()

        binding.rvShimmerGrid.shimmerRecyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.rvShimmerGrid.shimmerRecyclerView.adapter = shimmerAdapter

        binding.shimmerLoading.startShimmer()
    }

    private fun showRecyclerViewNowPlayingMovie() {
        binding.rvNowPlayingMovies.setHasFixedSize(true)

        val gridLayoutManager = GridLayoutManager(this, 2)

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position == binding.rvNowPlayingMovies.adapter?.itemCount?.minus(1)) {
                    2
                } else {
                    1
                }
            }
        }

        binding.rvNowPlayingMovies.layoutManager = gridLayoutManager
        binding.rvNowPlayingMovies.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter()
        )

        viewModel.movie.observe(this) {
            adapter.submitData(lifecycle, it)
        }

        adapter.addLoadStateListener { loadState ->
            val isLoading = loadState.refresh is LoadState.Loading
            binding.shimmerLoading.isVisible = isLoading
            binding.rvNowPlayingMovies.isVisible = !isLoading
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }
}