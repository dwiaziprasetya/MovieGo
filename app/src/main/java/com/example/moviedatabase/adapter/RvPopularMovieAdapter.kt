package com.example.moviedatabase.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviedatabase.databinding.ItemMoviesBinding
import com.example.moviedatabase.response.PopularMovieResultsItem

class RvPopularMovieAdapter : ListAdapter<PopularMovieResultsItem, RvPopularMovieAdapter.MyViewHolder>(DIFF_CALLBACK){

    class MyViewHolder(val binding: ItemMoviesBinding): RecyclerView.ViewHolder(binding.root){
        val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500/"
        fun bindMovie(movie : PopularMovieResultsItem){
            binding.tvMovieTitle.text = movie.title
            Glide.with(itemView)
                .load(IMAGE_BASE_URL + movie.posterPath)
                .into(binding.imgItemPhoto)
        }
    }
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PopularMovieResultsItem>() {
            override fun areItemsTheSame(
                oldItem: PopularMovieResultsItem,
                newItem: PopularMovieResultsItem,
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: PopularMovieResultsItem,
                newItem: PopularMovieResultsItem,
            ): Boolean {
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
    }

}