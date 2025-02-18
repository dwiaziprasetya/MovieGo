package com.example.moviedatabase.ui.fragment.movie

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
import com.example.moviedatabase.data.LoadingStateAdapter
import com.example.moviedatabase.databinding.FragmentMovieBinding
import com.example.moviedatabase.ui.activity.detail.DetailActivity
import com.example.moviedatabase.ui.adapter.RvDiscoverMovieAdapter
import com.example.moviedatabase.utils.ViewModelFactory

class MovieFragment : Fragment() {
    private var _binding : FragmentMovieBinding? = null
    private val binding get() = _binding!!


    private lateinit var factory: ViewModelFactory
    private val viewModel by viewModels<MovieViewModel> { factory }
    private val adapter = RvDiscoverMovieAdapter()


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
            binding.progressBar.isVisible = isLoading
            binding.rvDiscoverMovies.isVisible = !isLoading
        }
    }


}