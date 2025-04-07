package com.example.moviedatabase.ui.adapter

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

class FavouriteAdapter : ListAdapter<Favourite, FavouriteAdapter.FavouriteViewHolder>(DIFF_CALLBACK) {

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
}
