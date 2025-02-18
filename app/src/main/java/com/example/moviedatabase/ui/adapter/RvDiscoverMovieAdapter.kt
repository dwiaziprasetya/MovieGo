package com.example.moviedatabase.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviedatabase.BuildConfig
import com.example.moviedatabase.data.remote.response.DiscoverMovieItem
import com.example.moviedatabase.databinding.ItemMovies2Binding

class RvDiscoverMovieAdapter: PagingDataAdapter<DiscoverMovieItem, RvDiscoverMovieAdapter.MyViewHolder>(
    DIFF_CALLBACK
) {
//    private lateinit var onItemCallback : OnitemClickCallback

    class MyViewHolder(val binding : ItemMovies2Binding) : RecyclerView.ViewHolder(binding.root) {
        private val imageBaseUrl = BuildConfig.BASE_IMAGE_URL_MOVIE_DB_W500
        fun bind(movie : DiscoverMovieItem){
//            binding.tvMovieTitle.text = movie.title
            Glide.with(itemView)
                .load(imageBaseUrl + movie.posterPath)
                .into(binding.imgItemPhoto)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DiscoverMovieItem>() {
            override fun areItemsTheSame(
                oldItem: DiscoverMovieItem,
                newItem: DiscoverMovieItem,
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: DiscoverMovieItem,
                newItem: DiscoverMovieItem,
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemMovies2Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie!!)
//        holder.binding.root.setOnClickListener {
//            onItemCallback.onItemClicked(movie)
//        }
    }


//    fun setOnItemClickCallback(onItemClickCallback: OnitemClickCallback){
//        this.onItemCallback = onItemClickCallback
//    }
//
//    interface OnitemClickCallback {
//        fun onItemClicked(data : DiscoverMovieItem)
//    }
}