package com.example.moviedatabase.ui.fragment.home

import android.annotation.SuppressLint
import android.content.Intent
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
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.moviedatabase.R
import com.example.moviedatabase.data.local.model.ImageData
import com.example.moviedatabase.data.remote.response.NowPlayingMovieItem
import com.example.moviedatabase.data.remote.response.PopularMovieItem
import com.example.moviedatabase.data.remote.response.UpComingMovieItem
import com.example.moviedatabase.databinding.FragmentHomeBinding
import com.example.moviedatabase.ui.activity.detail.DetailActivity
import com.example.moviedatabase.ui.adapter.ImageSliderAdapter
import com.example.moviedatabase.ui.adapter.RvNowPlayingMovieAdapter
import com.example.moviedatabase.ui.adapter.RvPopularMovieAdapter
import com.example.moviedatabase.ui.adapter.RvUpComingMovieAdapter
import com.example.moviedatabase.utils.ViewModelFactory
import kotlin.math.abs

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter : ImageSliderAdapter
    private val list = ArrayList<ImageData>()
    private lateinit var dots : ArrayList<TextView>
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
    private var adapterUpComingMovie = RvUpComingMovieAdapter()
    private var adapterPopularMovie = RvPopularMovieAdapter()
    private var adapterNowPlayingMovie = RvNowPlayingMovieAdapter()

    private lateinit var factory: ViewModelFactory
    private val viewModel by viewModels<HomeViewModel> { factory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dots = ArrayList()
        handler = Handler(Looper.getMainLooper())
        handler.post { binding.viewPager.currentItem = 2 }
        runnable = Runnable { binding.viewPager.currentItem++ }

        factory = ViewModelFactory.getInstance()

        with(viewModel) {
            dataUpComingMovieItem.observe(requireActivity()) {
                setUpComingMovieData(it)
            }
            dataPopularMovieItem.observe(requireActivity()) {
                setPopularMovieData(it)
            }
            dataNowPlayingMovieItem.observe(requireActivity()) {
                setNowPlayingMovieData(it)
            }
            isLoading.observe(requireActivity()) {
                showLoading(it)
            }
        }

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                selectedDot(position)
                handler.removeCallbacks(runnable)
                Log.d("Posisi", position.toString())
                handler.postDelayed(runnable, 4000)
                super.onPageSelected(position)
            }
        })

        adapterNowPlayingMovie.setOnItemClickCallback(object : RvNowPlayingMovieAdapter.OnItemClickCallback {
            override fun onItemClicked(movieId: Int) {
                val intent = Intent(requireActivity(), DetailActivity::class.java)
                intent.putExtra(DetailActivity.MOVIE_ID, movieId)
                startActivity(intent)
            }
        })

        adapterPopularMovie.setOnItemClickCallback(object : RvPopularMovieAdapter.OnItemClickCallback {
            override fun onItemClicked(movieId: Int) {
                val intent = Intent(requireActivity(), DetailActivity::class.java)
                intent.putExtra(DetailActivity.MOVIE_ID, movieId)
                Log.d("HomeFragment", "Movie ID: $movieId")
                startActivity(intent)
            }

        })

        adapterUpComingMovie.setOnItemClickCallback(object : RvUpComingMovieAdapter.OnItemClickCallback {
            override fun onItemClicked(movieId: Int) {
                val intent = Intent(requireActivity(), DetailActivity::class.java)
                intent.putExtra(DetailActivity.MOVIE_ID, movieId)
                startActivity(intent)
            }
        })

        showImageSlider()
        setIndicator()
        showRecyclerViewUpComingMovie()
        showRecyclerViewPopularMovie()
        showRecyclerViewNowPlayingMovie()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

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

    private fun setPopularMovieData(movies: List<PopularMovieItem>){
        adapterPopularMovie.submitList(movies)
        binding.rvPopularMovies.adapter = adapterPopularMovie
    }

    private fun setUpComingMovieData(movies : List<UpComingMovieItem>){
        adapterUpComingMovie.submitList(movies)
        binding.rvUpcomingMovies.adapter = adapterUpComingMovie
    }

    private fun setNowPlayingMovieData(movies : List<NowPlayingMovieItem>){
        adapterNowPlayingMovie.submitList(movies)
        binding.rvNowPlayingMovies.adapter = adapterNowPlayingMovie
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

    private fun showImageSlider() {
        list.addAll(getListImageSlider())
        adapter = ImageSliderAdapter(list, binding.viewPager)
        with(binding){
            viewPager.adapter = adapter
            viewPager.offscreenPageLimit = 3
            viewPager.clipChildren = false
            viewPager.clipToPadding = false
            viewPager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }

        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.14f
        }

        binding.viewPager.setPageTransformer(transformer)
    }

    private fun selectedDot(position: Int) {
        val dotIndex = position % dots.size
        for (i in dots.indices) {
            if (i == dotIndex) {
                dots[i].setTextColor(ContextCompat.getColor(requireActivity(), R.color.red_netflix))
            } else {
                dots[i].setTextColor(ContextCompat.getColor(requireActivity(), R.color.grey))
            }
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
        for (i in 0 until list.size) {
            val textView = TextView(requireActivity())
            textView.text = Html.fromHtml("&#9679", Html.FROM_HTML_MODE_LEGACY).toString()
            textView.textSize = 14f
            textView.setTextColor(ContextCompat.getColor(requireActivity(), R.color.grey))

            val layoutParams = ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            layoutParams.marginStart = 8
            layoutParams.marginEnd = 8
            textView.layoutParams = layoutParams

            binding.dotsIndicator.addView(textView)
            dots.add(textView)
        }
    }

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

    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable, 4000)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }
}