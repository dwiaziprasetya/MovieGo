package com.example.moviedatabase.presentation.screen.upcoming

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
import com.example.moviedatabase.databinding.ActivityUpComingBinding
import com.example.moviedatabase.presentation.adapter.LoadingStateAdapter
import com.example.moviedatabase.presentation.adapter.PagingRvUpComingMovieAdapter
import com.example.moviedatabase.presentation.adapter.ShimmerItemMovieAdapter
import com.example.moviedatabase.presentation.screen.detail.DetailActivity
import com.example.moviedatabase.utils.ViewModelFactory

class UpComingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpComingBinding
    private lateinit var factory: ViewModelFactory
    private val viewModel by viewModels<UpComingViewModel> { factory }
    private val adapter = PagingRvUpComingMovieAdapter()
    private lateinit var shimmerAdapter : ShimmerItemMovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpComingBinding.inflate(layoutInflater)
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

        factory = ViewModelFactory.getInstance(this)
        showRecyclerViewUpComingMovie()
        setupShimmer()

        adapter.setOnItemClickCallback(object : PagingRvUpComingMovieAdapter.OnitemClickCallback {
            override fun onItemClicked(movieId: Int) {
                val intent = Intent(this@UpComingActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.MOVIE_ID, movieId)
                startActivity(intent)
            }
        })

        binding.rvUpComingMovies.addItemDecoration(object : RecyclerView.ItemDecoration() {
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

        binding.swipeRefreshLayout.setOnRefreshListener {
            adapter.refresh()
        }

        binding.swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.background_theme)

        binding.swipeRefreshLayout.setColorSchemeResources(R.color.red_netflix)
    }


    private fun setupShimmer() {
        shimmerAdapter = ShimmerItemMovieAdapter()

        binding.rvShimmerGrid.shimmerRecyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.rvShimmerGrid.shimmerRecyclerView.adapter = shimmerAdapter

        binding.shimmerLoading.startShimmer()
    }

    private fun showRecyclerViewUpComingMovie() {
        binding.rvUpComingMovies.setHasFixedSize(true)

        val gridLayoutManager = GridLayoutManager(this, 2)

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position == binding.rvUpComingMovies.adapter?.itemCount?.minus(1)) {
                    2
                } else {
                    1
                }
            }
        }

        binding.rvUpComingMovies.layoutManager = gridLayoutManager
        binding.rvUpComingMovies.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter()
        )

        viewModel.movie.observe(this) {
            adapter.submitData(lifecycle, it)
        }

        adapter.addLoadStateListener { loadState ->
            val isLoading = loadState.refresh is LoadState.Loading
            binding.shimmerLoading.isVisible = isLoading
            binding.rvUpComingMovies.isVisible = !isLoading
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }
}