package com.example.moviedatabase.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isEmpty
import androidx.core.view.isNotEmpty
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
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
    private lateinit var adapter : ImageSliderAdapter
    private val list = ArrayList<ImageData>()
    private lateinit var dots : ArrayList<TextView>
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable

    companion object {
        private const val TAG = "HomeFragment"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handler = Handler(Looper.getMainLooper())
        runnable = object : Runnable {
            var index = 0
            override fun run() {
                if (index == list.size) index = 0
                Log.e("Runnable","$index")
                binding.viewPager.currentItem = index
                index++
                handler.postDelayed(this,2000)
            }

        }

        showImageSlider()

        showRecyclerViewUpComingMovie()
        getUpComingMovieData()

        showRecyclerViewPopularMovie()
        getPopularMovieData()

        showRecyclerViewNowPlayingMovie()
        getNowPlayingMovieData()

        dots = ArrayList()
        setIndicator()
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                selectedDot(position)
                super.onPageSelected(position)
            }
        })
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

    private fun showImageSlider() {
        list.addAll(getListImageSlider())
        adapter = ImageSliderAdapter(list)
        binding.viewPager.adapter = adapter
    }

    // Dot Slider
    private fun selectedDot(position: Int){
        for (i in 0 until list.size){
            if (i == position)
                dots[i].setTextColor(ContextCompat.getColor(requireActivity(), R.color.white))
            else
                dots[i].setTextColor(ContextCompat.getColor(requireActivity(), R.color.grey))
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun resetListSize() {
        list.clear()
        list.addAll(getListImageSlider())
        adapter.notifyDataSetChanged()
    }

    private fun setIndicator() {
        binding.dotsIndicator.removeAllViews()
        dots.clear()
        resetListSize()
        for (i in 0 until list.size){
            dots.add(TextView(requireActivity()))
            dots[i].text = Html.fromHtml("&#9679", Html.FROM_HTML_MODE_LEGACY).toString()
            dots[i].textSize = 14f
            binding.dotsIndicator.addView(dots[i])
        }
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

    override fun onStart() {
        super.onStart()
        handler.post(runnable)
    }

    override fun onStop() {
        super.onStop()
        handler.removeCallbacks(runnable)
    }
}