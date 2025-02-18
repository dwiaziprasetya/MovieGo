package com.example.moviedatabase.ui.fragment.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviedatabase.databinding.FragmentMovieBinding
import com.example.moviedatabase.ui.adapter.RvDiscoverMovieAdapter
import com.example.moviedatabase.utils.ViewModelFactory

class MovieFragment : Fragment() {
    private var _binding : FragmentMovieBinding? = null
    private val binding get() = _binding!!

    private var adapterDiscover = RvDiscoverMovieAdapter()

    private lateinit var factory: ViewModelFactory
    private val viewModel by viewModels<MovieViewModel> { factory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        factory = ViewModelFactory.getInstance(requireActivity())

//        with(viewModel){
//            dataDiscoverMovieItem.observe(requireActivity()){
//                setDiscoverMovieData(it)
//            }
//            isLoading.observe(requireActivity()){
//                showLoading(it)
//            }
//        }
        getData()
        showRecyclerViewDiscoverMovie()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun showRecyclerViewDiscoverMovie(){
        binding.rvDiscoverMovies.setHasFixedSize(true)
        binding.rvDiscoverMovies.layoutManager = GridLayoutManager(requireActivity(), 2)
    }

//    private fun setDiscoverMovieData(movies : List<DiscoverMovieItem>){
//        adapterDiscover.submitList(movies)
//        binding.rvDiscoverMovies.adapter = adapterDiscover
//    }

    private fun getData() {
        val adapter = RvDiscoverMovieAdapter()
        binding.rvDiscoverMovies.adapter = adapter
        viewModel.movie.observe(requireActivity(), {
            adapter.submitData(lifecycle, it)
        })
    }

//    private fun showLoading(isLoading: Boolean) {
//        if (isLoading) {
//            binding.progressBar.visibility = View.VISIBLE
//        } else {
//            binding.progressBar.visibility = View.GONE
//        }
//    }
}