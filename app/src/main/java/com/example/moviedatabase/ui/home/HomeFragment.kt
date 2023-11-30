package com.example.moviedatabase.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviedatabase.R
import com.example.moviedatabase.adapter.ImageSliderAdapter
import com.example.moviedatabase.adapter.RvNowPlayingMovieAdapter
import com.example.moviedatabase.adapter.RvPopularMovieAdapter
import com.example.moviedatabase.adapter.RvUpComingMovieAdapter
import com.example.moviedatabase.databinding.FragmentHomeBinding
import com.example.moviedatabase.model.ImageData
import com.example.moviedatabase.response.NowPlayingMovieResponse
import com.example.moviedatabase.response.NowPlayingMovieResultsItem
import com.example.moviedatabase.response.PopularMovieResponse
import com.example.moviedatabase.response.PopularMovieResultsItem
import com.example.moviedatabase.response.ResultsItem
import com.example.moviedatabase.response.UpComingMovieResponse
import com.example.moviedatabase.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val TAG = "HomeFragment"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showImageSlider()

        showRecyclerViewUpComingMovie()
        getUpComingMovieData()

        showRecyclerViewPopularMovie()
        getPopularMovieData()

        showRecyclerViewNowPlayingMovie()
        getNowPlayingMovieData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Function Get Movie Data
    private fun getUpComingMovieData(){ // Up Coming
        val client = ApiConfig.getApiService().getUpComingMovies()
        client.enqueue(object : Callback<UpComingMovieResponse> {
            override fun onResponse(call: Call<UpComingMovieResponse>, response: Response<UpComingMovieResponse>) {
                showLoading(false)
                if (response.isSuccessful){
                    val responseBody = response.body()
                    if (response.body() != null){
                        setUpComingMovieData(responseBody!!.results)
                    }
                } else {
                    Log.e(TAG, "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UpComingMovieResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure : ${t.message}")
            }
        })
    }

    private fun getPopularMovieData(){ // Popular
        val client = ApiConfig.getApiService().getPopularMovies()
        client.enqueue(object : Callback<PopularMovieResponse> {
            override fun onResponse(
                call: Call<PopularMovieResponse>,
                response: Response<PopularMovieResponse>,
            ) {
                showLoading(false)
                if (response.isSuccessful){
                    val responseBody = response.body()
                    if (responseBody != null){
                        setPopularMovieData(responseBody.results)
                    } else {
                        Log.e(TAG, "onFailure : ${response.message()}")
                    }
                }
            }

            override fun onFailure(call: Call<PopularMovieResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure : ${t.message}")
            }

        })
    }

    private fun getNowPlayingMovieData(){ // Now Playing
        val client = ApiConfig.getApiService().getNowPlayingMovies()
        client.enqueue(object: Callback<NowPlayingMovieResponse> {
            override fun onResponse(
                call: Call<NowPlayingMovieResponse>,
                response: Response<NowPlayingMovieResponse>,
            ) {
                showLoading(false)
                if (response.isSuccessful){
                    val responseBody = response.body()
                    if (responseBody != null){
                        setNowPlayingMovieData(responseBody.results)
                    } else {
                        Log.e(TAG, "onFailure : ${response.message()}")
                    }
                }
            }

            override fun onFailure(call: Call<NowPlayingMovieResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure : ${t.message}")
            }

        })
    }

    // Function Show RecyclerView
    private fun showRecyclerViewUpComingMovie() {
        binding.rvUpcomingMovies.layoutManager = LinearLayoutManager(requireActivity(),LinearLayoutManager.HORIZONTAL,false)
        binding.rvUpcomingMovies.hasFixedSize()
    }

    private fun showRecyclerViewPopularMovie(){
        binding.rvPopularMovies.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvPopularMovies.hasFixedSize()
    }

    private fun showRecyclerViewNowPlayingMovie(){
        binding.rvNowPlayingMovies.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvNowPlayingMovies.hasFixedSize()
    }

    // Function Set Data Movie
    private fun setPopularMovieData(movies: List<PopularMovieResultsItem>){
        val adapter = RvPopularMovieAdapter()
        adapter.submitList(movies)
        binding.rvPopularMovies.adapter = adapter
    }

    private fun setUpComingMovieData(movies : List<ResultsItem>){
        val adapter = RvUpComingMovieAdapter()
        adapter.submitList(movies)
        binding.rvUpcomingMovies.adapter = adapter
    }

    private fun setNowPlayingMovieData(movies : List<NowPlayingMovieResultsItem>){
        val adapter = RvNowPlayingMovieAdapter()
        adapter.submitList(movies)
        binding.rvNowPlayingMovies.adapter = adapter
    }

    // Function Image Slider
    private fun showImageSlider() {
        val adapter : ImageSliderAdapter
        val list = ArrayList<ImageData>()
        list.addAll(getListImageSlider())
        adapter = ImageSliderAdapter(list)
        binding.viewPager.adapter = adapter
    }

    @SuppressLint("Recycle")
    private fun getListImageSlider() : ArrayList<ImageData> {
        val dataImage = resources.obtainTypedArray(R.array.image_slider)
        val listImage = ArrayList<ImageData>()
        for (i in 0 until dataImage.length()) {
            val image = ImageData(dataImage.getResourceId(i,0))
            listImage.add(image)
        }
        return listImage
    }

    // Function Show Loading
    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.pb1.visibility = View.VISIBLE
            binding.pb2.visibility = View.VISIBLE
            binding.pb3.visibility = View.VISIBLE
        } else {
            binding.pb1.visibility = View.GONE
            binding.pb2.visibility = View.GONE
            binding.pb3.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}