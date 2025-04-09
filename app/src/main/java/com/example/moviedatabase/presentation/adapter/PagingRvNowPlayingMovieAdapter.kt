package com.example.moviedatabase.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviedatabase.BuildConfig
import com.example.moviedatabase.R
import com.example.moviedatabase.data.remote.response.NowPlayingMovieItem
import com.example.moviedatabase.databinding.ItemMovies2Binding

class PagingRvNowPlayingMovieAdapter: PagingDataAdapter<NowPlayingMovieItem, PagingRvNowPlayingMovieAdapter.MyViewHolder>(
    DIFF_CALLBACK) {
    private lateinit var onItemCallback : OnitemClickCallback

    class MyViewHolder(val binding : ItemMovies2Binding) : RecyclerView.ViewHolder(binding.root) {
        private val imageBaseUrl = BuildConfig.BASE_IMAGE_URL_MOVIE_DB_W500
        fun bind(movie : NowPlayingMovieItem){
            Glide.with(itemView)
                .load(imageBaseUrl + movie.posterPath)
                .into(binding.imgItemPhoto)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<NowPlayingMovieItem>() {
            override fun areItemsTheSame(
                oldItem: NowPlayingMovieItem,
                newItem: NowPlayingMovieItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: NowPlayingMovieItem,
                newItem: NowPlayingMovieItem
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie!!)
        holder.binding.root.setOnClickListener {
            onItemCallback.onItemClicked(movie.id)
        }
        holder.binding.itemMovies.startAnimation(android.view.animation.AnimationUtils.loadAnimation(holder.binding.itemMovies.context, R.anim.scale_up))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemMovies2Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnitemClickCallback){
        this.onItemCallback = onItemClickCallback
    }

    interface OnitemClickCallback {
        fun onItemClicked(movieId: Int)
    }
}
