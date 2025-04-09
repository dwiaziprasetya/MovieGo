package com.example.moviedatabase.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedatabase.databinding.ShimmerItemMoviesBinding

class ShimmerItemMoviePopularAdapter : RecyclerView.Adapter<ShimmerItemMoviePopularAdapter.ShimmerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShimmerViewHolder {
        val binding = ShimmerItemMoviesBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ShimmerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShimmerViewHolder, position: Int) {}

    override fun getItemCount(): Int = 5

    class ShimmerViewHolder(itemBinding: ShimmerItemMoviesBinding) : RecyclerView.ViewHolder(itemBinding.root)
}