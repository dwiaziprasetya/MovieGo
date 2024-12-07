package com.example.moviedatabase.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviedatabase.BuildConfig
import com.example.moviedatabase.R
import com.example.moviedatabase.data.remote.response.UpComingMovieItem
import com.example.moviedatabase.databinding.ItemMoviesUpComingBinding
import com.example.moviedatabase.utils.MovieUtils

class RvUpComingMovieAdapter : ListAdapter<UpComingMovieItem, RvUpComingMovieAdapter.MyViewHolder>(
    DIFF_CALLBACK
) {
    private lateinit var onItemCallback : OnItemClickCallback
    class MyViewHolder(val binding: ItemMoviesUpComingBinding): RecyclerView.ViewHolder(binding.root){
        private val imageBaseUrl = BuildConfig.BASE_IMAGE_URL_MOVIE_DB_W500
        fun bindMovie(movie : UpComingMovieItem){
            val genreNames = MovieUtils.getGenreNames(movie.genreIds)
            binding.tvMovieTitle.text = movie.title
            binding.tvMovieGenre.text = genreNames
            Glide.with(itemView)
                .load(imageBaseUrl + movie.backdropPath)
                .into(binding.imgItemPhoto)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UpComingMovieItem>() {
            override fun areContentsTheSame(oldItem: UpComingMovieItem, newItem: UpComingMovieItem): Boolean {
                return oldItem == newItem
            }
            override fun areItemsTheSame(oldItem: UpComingMovieItem, newItem: UpComingMovieItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemMoviesUpComingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(movieId : Int)
    }
}