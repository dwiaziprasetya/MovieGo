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

class RvDiscoverMovieAdapter: ListAdapter<DiscoverMovieResultsItem, RvDiscoverMovieAdapter.MyViewHolder>(DIFF_CALLBACK) {
    private lateinit var onItemCallback :OnitemClickCallback
    private var movie = ArrayList<DiscoverMovieResultsItem>()
    class MyViewHolder(val binding : ItemMovieListBinding) : RecyclerView.ViewHolder(binding.root) {
        val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500/"
        fun bind(movie : DiscoverMovieResultsItem){
            binding.tvMovieListName.text = movie.title
            binding.tvMovieReleaseDate.text = movie.releaseDate
            binding.rbRatingMovie.rating = movie.movieRate().toFloat()
            Glide.with(itemView)
                .load(IMAGE_BASE_URL + movie.posterPath)
                .into(binding.imgMovieListPhoto)
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
        holder.binding.root.setOnClickListener {
            onItemCallback.onItemClicked(movie)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addList(item : ArrayList<DiscoverMovieResultsItem>){
        movie.addAll(item)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clear(){
        movie.clear()
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnitemClickCallback){
        this.onItemCallback = onItemClickCallback
    }

    interface OnitemClickCallback {
        fun onItemClicked(data : DiscoverMovieResultsItem)
    }
}