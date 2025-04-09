package com.example.moviedatabase.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedatabase.databinding.ShimmerItemMoviesGridBinding

class ShimmerItemMovieAdapter : RecyclerView.Adapter<ShimmerItemMovieAdapter.ShimmerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShimmerViewHolder {
        val binding = ShimmerItemMoviesGridBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ShimmerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShimmerViewHolder, position: Int) {}

    override fun getItemCount(): Int = 10

    class ShimmerViewHolder(private val itemBinding: ShimmerItemMoviesGridBinding) : RecyclerView.ViewHolder(itemBinding.root)
}