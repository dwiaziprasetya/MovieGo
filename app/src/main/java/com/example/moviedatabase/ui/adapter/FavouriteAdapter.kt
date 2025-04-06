package com.example.moviedatabase.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviedatabase.BuildConfig
import com.example.moviedatabase.data.local.entity.Favourite
import com.example.moviedatabase.databinding.ItemMovieListBinding

class FavouriteAdapter : ListAdapter<Favourite, FavouriteAdapter.FavouriteViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Favourite>() {
            override fun areItemsTheSame(oldItem: Favourite, newItem: Favourite): Boolean =
                oldItem.movieId == newItem.movieId

            override fun areContentsTheSame(oldItem: Favourite, newItem: Favourite): Boolean =
                oldItem == newItem
        }
    }

    inner class FavouriteViewHolder(private val binding: ItemMovieListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val imageBaseUrl = BuildConfig.BASE_IMAGE_URL_MOVIE_DB_W500
        fun bind(favourite: Favourite) {
            binding.tvMovieListName.text = favourite.movieName
            binding.tvMovieGenre.text = "Action, Supranatural"
            Glide.with(binding.root)
                .load(imageBaseUrl + favourite.moviePhoto)
                .into(binding.imgMovieListPhoto)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        val binding = ItemMovieListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavouriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
