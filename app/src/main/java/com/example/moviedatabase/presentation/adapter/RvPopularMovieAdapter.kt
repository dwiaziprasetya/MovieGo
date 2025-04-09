package com.example.moviedatabase.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviedatabase.BuildConfig
import com.example.moviedatabase.R
import com.example.moviedatabase.data.remote.response.PopularMovieItem
import com.example.moviedatabase.databinding.ItemMoviesBinding

class RvPopularMovieAdapter : ListAdapter<PopularMovieItem, RvPopularMovieAdapter.MyViewHolder>(
    DIFF_CALLBACK
){
    private lateinit var onItemCallback : OnItemClickCallback

    class MyViewHolder(val binding: ItemMoviesBinding): RecyclerView.ViewHolder(binding.root){
        private val imageBaseUrl = BuildConfig.BASE_IMAGE_URL_MOVIE_DB_W500
        fun bindMovie(movie : PopularMovieItem){
            binding.tvMovieTitle.text = movie.title
            Glide.with(itemView)
                .load(imageBaseUrl + movie.posterPath)
                .into(binding.imgItemPhoto)
        }
    }
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PopularMovieItem>() {
            override fun areItemsTheSame(
                oldItem: PopularMovieItem,
                newItem: PopularMovieItem,
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: PopularMovieItem,
                newItem: PopularMovieItem,
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
        holder.binding.root.setOnClickListener {
            onItemCallback.onItemClicked(movie.id)
        }

        holder.binding.itemMovies.startAnimation(android.view.animation.AnimationUtils.loadAnimation(holder.binding.itemMovies.context, R.anim.scale_up))
    }


    fun setOnItemClickCallback(onItemClickCallback : OnItemClickCallback){
        this.onItemCallback = onItemClickCallback
    }
    interface OnItemClickCallback {
        fun onItemClicked(movieId : Int)
    }
}