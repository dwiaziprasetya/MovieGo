package com.example.moviedatabase.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviedatabase.databinding.ItemMoviesBinding
import com.example.moviedatabase.data.remote.response.UpComingMovieItems

class RvUpComingMovieAdapter : ListAdapter<UpComingMovieItems, RvUpComingMovieAdapter.MyViewHolder>(
    DIFF_CALLBACK
) {
    private lateinit var onItemCallback : OnItemClickCallback
    class MyViewHolder (val binding: ItemMoviesBinding): RecyclerView.ViewHolder(binding.root){
        private val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500/"
        fun bindMovie(movie : UpComingMovieItems){
            binding.tvMovieTitle.text = movie.title
            Glide.with(itemView)
                .load(IMAGE_BASE_URL + movie.posterPath)
                .into(binding.imgItemPhoto)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UpComingMovieItems>() {
            override fun areContentsTheSame(oldItem: UpComingMovieItems, newItem: UpComingMovieItems): Boolean {
                return oldItem == newItem
            }
            override fun areItemsTheSame(oldItem: UpComingMovieItems, newItem: UpComingMovieItems): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bindMovie(movie)
        holder.binding.root.setOnClickListener {
            onItemCallback.onItemClicked(movie)
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data : UpComingMovieItems)
    }
}