package com.example.moviedatabase.presentation.screen.movie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviedatabase.R
import com.example.moviedatabase.presentation.adapter.LoadingStateAdapter
import com.example.moviedatabase.databinding.FragmentMovieBinding
import com.example.moviedatabase.presentation.screen.detail.DetailActivity
import com.example.moviedatabase.presentation.adapter.RvDiscoverMovieAdapter
import com.example.moviedatabase.presentation.adapter.ShimmerItemMovieAdapter
import com.example.moviedatabase.utils.ViewModelFactory

class MovieFragment : Fragment() {
    private var _binding : FragmentMovieBinding? = null
    private val binding get() = _binding!!


    private lateinit var factory: ViewModelFactory
    private val viewModel by viewModels<MovieViewModel> { factory }
    private val adapter = RvDiscoverMovieAdapter()
    private lateinit var shimmerAdapter: ShimmerItemMovieAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        factory = ViewModelFactory.getInstance(requireActivity())

        adapter.setOnItemClickCallback(object : RvDiscoverMovieAdapter.OnitemClickCallback {
            override fun onItemClicked(movieId: Int) {
                val intent = Intent(requireActivity(), DetailActivity::class.java)
                intent.putExtra(DetailActivity.MOVIE_ID, movieId)
                startActivity(intent)
            }
        })

        showRecyclerViewDiscoverMovie()
        setupShimmer()

        binding.swipeRefreshLayout.setOnRefreshListener {
            adapter.refresh()
        }

        binding.swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.background_theme)

        binding.swipeRefreshLayout.setColorSchemeResources(
            R.color.red_netflix,
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun showRecyclerViewDiscoverMovie() {
        binding.rvDiscoverMovies.setHasFixedSize(true)

        val gridLayoutManager = GridLayoutManager(requireActivity(), 2)

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position == binding.rvDiscoverMovies.adapter?.itemCount?.minus(1)) {
                    2
                } else {
                    1
                }
            }
        }

        binding.rvDiscoverMovies.layoutManager = gridLayoutManager

        binding.rvDiscoverMovies.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter()
        )

        viewModel.movie.observe(viewLifecycleOwner) {
            adapter.submitData(lifecycle, it)
        }

        adapter.addLoadStateListener { loadState ->
            val isLoading = loadState.refresh is LoadState.Loading
            binding.shimmerLoading.isVisible = isLoading
            binding.rvDiscoverMovies.isVisible = !isLoading
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun setupShimmer() {
        shimmerAdapter = ShimmerItemMovieAdapter()

        binding.rvShimmerGrid.shimmerRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvShimmerGrid.shimmerRecyclerView.adapter = shimmerAdapter

        binding.shimmerLoading.startShimmer()
    }

    override fun onStart() {
        super.onStart()
        binding.shimmerLoading.startShimmer()
    }

    override fun onPause() {
        super.onPause()
        binding.shimmerLoading.stopShimmer()
    }
}