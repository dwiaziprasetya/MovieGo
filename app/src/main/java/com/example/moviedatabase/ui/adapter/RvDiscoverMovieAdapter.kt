package com.example.moviedatabase.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviedatabase.BuildConfig
import com.example.moviedatabase.data.remote.response.DiscoverMovieItem
import com.example.moviedatabase.data.remote.response.GenreItem
import com.example.moviedatabase.databinding.ItemMovieListBinding
import java.text.DecimalFormat

class RvDiscoverMovieAdapter: ListAdapter<DiscoverMovieItem, RvDiscoverMovieAdapter.MyViewHolder>(
    DIFF_CALLBACK
) {
    private lateinit var onItemCallback : OnitemClickCallback
    private var genreList: List<GenreItem> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setGenreList(genres: List<GenreItem>){
        genreList = genres
        notifyDataSetChanged()
    }

    class MyViewHolder(val binding : ItemMovieListBinding) : RecyclerView.ViewHolder(binding.root) {
        val imageBaseUrl = BuildConfig.BASE_IMAGE_URL_MOVIE_DB_W500
        fun bind(movie : DiscoverMovieItem){
            val decimalFormat = DecimalFormat("#.#")
            binding.tvMovieListName.text = movie.title
            binding.rbRatingMovie.rating = movie.movieRate().toFloat()
            Glide.with(itemView)
                .load(imageBaseUrl + movie.posterPath)
                .into(binding.imgMovieListPhoto)
            binding.tvRatingMovie.text = decimalFormat.format(movie.movieRate())
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DiscoverMovieItem>() {
            override fun areItemsTheSame(
                oldItem: DiscoverMovieItem,
                newItem: DiscoverMovieItem,
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: DiscoverMovieItem,
                newItem: DiscoverMovieItem,
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemMovieListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
        val genreNames = getGenreNames(movie.genreIds)
        holder.binding.tvMovieGenre.text = genreNames
        holder.binding.root.setOnClickListener {
            onItemCallback.onItemClicked(movie)
        }
    }

    private fun getGenreNames(genreIds: List<Int>) : String {
        val genreNames = mutableListOf<String>()
        for (genreId in genreIds) {
            val genre = genreList.find { it.id == genreId }
            genre?.let {
                genreNames.add(it.name)
            }
        }
        return genreNames.joinToString(", ")
    }

//    @SuppressLint("NotifyDataSetChanged")
//    fun clear(){
//        movie.clear()
//        notifyDataSetChanged()
//    }

    fun setOnItemClickCallback(onItemClickCallback: OnitemClickCallback){
        this.onItemCallback = onItemClickCallback
    }

    interface OnitemClickCallback {
        fun onItemClicked(data : DiscoverMovieItem)
    }
}