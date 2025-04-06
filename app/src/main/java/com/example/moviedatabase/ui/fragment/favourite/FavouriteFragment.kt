package com.example.moviedatabase.ui.fragment.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviedatabase.R
import com.example.moviedatabase.databinding.FragmentFavouriteBinding
import com.example.moviedatabase.ui.adapter.FavouriteAdapter
import com.example.moviedatabase.utils.ViewModelFactory

class FavouriteFragment : Fragment() {

    private var _binding: FragmentFavouriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: FavouriteAdapter

    private lateinit var factory: ViewModelFactory
    private val viewModel by viewModels<FavouriteViewModel> { factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        factory = ViewModelFactory.getInstance(requireActivity())

        adapter = FavouriteAdapter()
        binding.rvFavouriteMovie.adapter = adapter
        binding.rvFavouriteMovie.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        binding.rvFavouriteMovie.setHasFixedSize(true)

        binding.swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.background_theme)

        binding.swipeRefreshLayout.setColorSchemeResources(
            R.color.red_netflix,
        )

        viewModel.favourites.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.rvFavouriteMovie.visibility = View.GONE
                binding.shimmerLoading.visibility = View.VISIBLE
                binding.shimmerLoading.startShimmer()
            } else {
                binding.shimmerLoading.visibility = View.GONE
                binding.shimmerLoading.stopShimmer()
                binding.rvFavouriteMovie.visibility = View.VISIBLE
            }
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getAllFavourites()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }
}