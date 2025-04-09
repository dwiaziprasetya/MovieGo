package com.example.moviedatabase.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviedatabase.BuildConfig
import com.example.moviedatabase.R
import com.example.moviedatabase.data.local.entity.Favourite
import com.example.moviedatabase.databinding.ItemMovieListBinding

class RvFavouriteAdapter : ListAdapter<Favourite, RvFavouriteAdapter.FavouriteViewHolder>(DIFF_CALLBACK) {

    private lateinit var onItemCallback : OnItemClickCallback

    private var animateItems = true

    @SuppressLint("NotifyDataSetChanged")
    fun setAnimateItems(animate: Boolean) {
        animateItems = animate
        notifyDataSetChanged()
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Favourite>() {
            override fun areItemsTheSame(oldItem: Favourite, newItem: Favourite): Boolean =
                oldItem.movieId == newItem.movieId

            override fun areContentsTheSame(oldItem: Favourite, newItem: Favourite): Boolean =
                oldItem == newItem
        }
    }

    inner class FavouriteViewHolder(val binding: ItemMovieListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val imageBaseUrl = BuildConfig.BASE_IMAGE_URL_MOVIE_DB_W500
        @SuppressLint("SetTextI18n")
        fun bind(favourite: Favourite) {
            binding.tvMovieListName.text = favourite.movieName
            binding.tvMovieGenre.text = favourite.genres
            binding.tvReleaseDate.text = "${favourite.releaseDate.substring(0, 4)} • ${formatRuntime(favourite.runtime)}"
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
        holder.binding.root.setOnClickListener {
            onItemCallback.onItemClicked(getItem(position).movieId)
        }

        if (animateItems) {
            holder.binding.itemMoviesList.startAnimation(
                android.view.animation.AnimationUtils.loadAnimation(
                    holder.binding.itemMoviesList.context,
                    R.anim.scale_up
                )
            )
        }
    }

    fun setOnItemClickCallback(onItemClickCallback : OnItemClickCallback){
        this.onItemCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(movieId : Int)
    }

    private fun formatRuntime(runtime: Int): String {
        val hours = runtime / 60
        val minutes = runtime % 60
        return "$hours h $minutes m"
    }
}
