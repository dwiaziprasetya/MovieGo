package com.example.moviedatabase.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedatabase.databinding.ShimmerItemMoviesGridBinding

class ShimmerAdapter : RecyclerView.Adapter<ShimmerAdapter.ShimmerViewHolder>() {

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

    class ShimmerViewHolder(val itemBinding: ShimmerItemMoviesGridBinding) : RecyclerView.ViewHolder(itemBinding.root)
}