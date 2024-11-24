package com.example.moviedatabase.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviedatabase.data.remote.response.CastItem
import com.example.moviedatabase.databinding.ItemMoviesBinding

class RvCastMovieAdapter : ListAdapter<CastItem, RvCastMovieAdapter.MyViewHolder>(DIFF_CALLBACK) {
    class MyViewHolder(val binding : ItemMoviesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cast : CastItem){
            binding.tvMovieTitle.setText(cast.name)
            if (!cast.profilePath.isNullOrBlank()){
                Glide.with(itemView)
                    .load("https://image.tmdb.org/t/p/w500/" + cast.profilePath)
                    .into(binding.imgItemPhoto)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CastItem>(){
            override fun areItemsTheSame(oldItem: CastItem, newItem: CastItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: CastItem, newItem: CastItem): Boolean {
                return oldItem == newItem
            }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val cast = getItem(position)
        holder.bind(cast)
    }
}