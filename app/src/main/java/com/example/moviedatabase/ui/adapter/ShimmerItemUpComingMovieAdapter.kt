package com.example.moviedatabase.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedatabase.databinding.ShimmerItemUpComingMoviesBinding

class ShimmerItemUpComingMovieAdapter : RecyclerView.Adapter<ShimmerItemUpComingMovieAdapter.ShimmerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShimmerViewHolder {
        val binding = ShimmerItemUpComingMoviesBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ShimmerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShimmerViewHolder, position: Int) {}

    override fun getItemCount(): Int = 5

    class ShimmerViewHolder(private val itemBinding: ShimmerItemUpComingMoviesBinding) : RecyclerView.ViewHolder(itemBinding.root)
}