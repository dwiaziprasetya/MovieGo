package com.example.moviedatabase.presentation.screen.popular

import android.content.Intent
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedatabase.R
import com.example.moviedatabase.databinding.ActivityPopularBinding
import com.example.moviedatabase.presentation.adapter.LoadingStateAdapter
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

        binding.swipeRefreshLayout.setOnRefreshListener {
            adapter.refresh()
        }

        binding.rvPopularMovies.addItemDecoration(object : RecyclerView.ItemDecoration() {
            private val space = 24

            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                outRect.left = space / 2
                outRect.right = space / 2
                outRect.top = space / 2
                outRect.bottom = space / 2
            }
        })

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

    private fun showRecyclerViewPopularMovie() {
        binding.rvPopularMovies.setHasFixedSize(true)

        val gridLayoutManager = GridLayoutManager(this, 2)

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position == binding.rvPopularMovies.adapter?.itemCount?.minus(1)) {
                    2
                } else {
                    1
                }
            }
        }

        binding.rvPopularMovies.layoutManager = gridLayoutManager
        binding.rvPopularMovies.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter()
        )

        viewModel.movie.observe(this) {
            adapter.submitData(lifecycle, it)
        }

        adapter.addLoadStateListener { loadState ->
            val isLoading = loadState.refresh is LoadState.Loading
            binding.shimmerLoading.isVisible = isLoading
            binding.rvPopularMovies.isVisible = !isLoading
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }
}