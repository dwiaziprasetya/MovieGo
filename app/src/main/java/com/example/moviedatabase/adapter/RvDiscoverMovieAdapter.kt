package com.example.moviedatabase.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviedatabase.databinding.ItemMovieListBinding
import com.example.moviedatabase.response.DiscoverMovieResultsItem
import com.example.moviedatabase.response.GenresItem
import java.text.DecimalFormat

class RvDiscoverMovieAdapter: ListAdapter<DiscoverMovieResultsItem, RvDiscoverMovieAdapter.MyViewHolder>(DIFF_CALLBACK) {
    private lateinit var onItemCallback :OnitemClickCallback
    private var genreList: List<GenresItem> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setGenreList(genres: List<GenresItem>){
        genreList = genres
        notifyDataSetChanged()
    }

    class MyViewHolder(val binding : ItemMovieListBinding) : RecyclerView.ViewHolder(binding.root) {
        val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500/"
        fun bind(movie : DiscoverMovieResultsItem){
            val decimalFormat = DecimalFormat("#.#")
            binding.tvMovieListName.text = movie.title
            binding.rbRatingMovie.rating = movie.movieRate().toFloat()
            Glide.with(itemView)
                .load(IMAGE_BASE_URL + movie.posterPath)
                .into(binding.imgMovieListPhoto)
            binding.tvRatingMovie.text = decimalFormat.format(movie.movieRate())
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DiscoverMovieResultsItem>() {
            override fun areItemsTheSame(
                oldItem: DiscoverMovieResultsItem,
                newItem: DiscoverMovieResultsItem,
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: DiscoverMovieResultsItem,
                newItem: DiscoverMovieResultsItem,
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
        fun onItemClicked(data : DiscoverMovieResultsItem)
    }
}