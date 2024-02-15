package com.example.moviedatabase.ui.movie

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.moviedatabase.adapter.RvDiscoverMovieAdapter
import com.example.moviedatabase.databinding.FragmentMovieBinding
import com.example.moviedatabase.response.DiscoverMovieResponse
import com.example.moviedatabase.response.DiscoverMovieResultsItem
import com.example.moviedatabase.response.GenreResponse
import com.example.moviedatabase.retrofit.ApiConfig
import com.example.moviedatabase.ui.activity.DetailActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {
    private var _binding : FragmentMovieBinding? = null
    private val binding get() = _binding!!
    private var page = 1
    private lateinit var layoutManager : LinearLayoutManager
    private var totalPage = 1
    private var isLoading = false
    private var adapter = RvDiscoverMovieAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layoutManager = LinearLayoutManager(requireActivity())
        binding.rvDiscoverMovie.layoutManager = layoutManager
        binding.rvDiscoverMovie.adapter = adapter
        binding.rvDiscoverMovie.hasFixedSize()
        binding.swipeRefresh.setOnRefreshListener(this)
        getMovieGenre()
        getDiscoverMovie(false)

        binding.rvDiscoverMovie.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visibleItemCount = layoutManager.childCount
                val pastVisibleItem = layoutManager.findFirstVisibleItemPosition()
                val total = adapter.itemCount
                if (!isLoading && page < totalPage){
                    if (visibleItemCount + pastVisibleItem >= total){
                        page++
                        getDiscoverMovie(false)
                    }
                }
                Log.d("MovieFragment", "Page = ${page}, visibleItemCount: $visibleItemCount, pastVisibleItem: $pastVisibleItem, total: $total")
                super.onScrolled(recyclerView, dx, dy)
            }
        })

        adapter.setOnItemClickCallback(object : RvDiscoverMovieAdapter.OnitemClickCallback{
            override fun onItemClicked(data: DiscoverMovieResultsItem) {
                val intent = Intent(requireActivity(), DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_MOVIES, data)
                startActivity(intent)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun getDiscoverMovie(isOnRefresh : Boolean){
        isLoading = true
        if (!isOnRefresh){
            binding.pbDiscoverMovie.visibility = View.VISIBLE
        }
        val parameters = HashMap<String,String>()
        parameters["page"] = page.toString()
        val client = ApiConfig.getApiService().getDiscoverMovies(parameters)
        client.enqueue(object : Callback<DiscoverMovieResponse> {
            override fun onResponse(
                call: Call<DiscoverMovieResponse>,
                response: Response<DiscoverMovieResponse>,
            ) {
                totalPage = response.body()?.totalPages!!
                val listResponse = response.body()?.results
                if (listResponse != null){
                    setDiscoverMovieData(listResponse)
                }

                if (page == totalPage){
                    binding.pbDiscoverMovie.visibility = View.GONE
                } else {
                    binding.pbDiscoverMovie.visibility = View.GONE
                }
                isLoading = false
                binding.swipeRefresh.isRefreshing = false
            }

            override fun onFailure(call: Call<DiscoverMovieResponse>, t: Throwable) {
                Log.e("MovieFragment", "onFailure : ${t.message}")
            }
        })
    }

    private fun getMovieGenre() {
        val client = ApiConfig.getApiService().getMovieGenre()
        client.enqueue(object : Callback<GenreResponse> {
            override fun onResponse(call: Call<GenreResponse>, response: Response<GenreResponse>) {
                val genreList = response.body()?.genres ?: emptyList()
                adapter.setGenreList(genreList)
            }

            override fun onFailure(call: Call<GenreResponse>, t: Throwable) {
                Log.e("MovieFragment", "onFailure: ${t.message}")
            }

        })
    }

    private fun setDiscoverMovieData(movies: List<DiscoverMovieResultsItem>){
        val currentList = adapter.currentList.toMutableList()
        currentList.addAll(movies)
        adapter.submitList(currentList.toList())
    }

    override fun onRefresh() {
        page = 1
        Log.d("Ini page ke -> ", "$page")
        adapter.submitList(emptyList())
        getDiscoverMovie(true)
    }
}