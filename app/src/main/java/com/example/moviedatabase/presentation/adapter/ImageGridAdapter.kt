package com.example.moviedatabase.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviedatabase.databinding.ItemMoviesGridBinding

class ImageGridAdapter(
    private val images: List<Int>
) : RecyclerView.Adapter<ImageGridAdapter.ViewHolder>() {


    class ViewHolder(
        val binding: ItemMoviesGridBinding
    ) : RecyclerView.ViewHolder(binding.root){
        fun bind(imageResId: Int) {
            Glide.with(itemView)
                .load(imageResId)
                .centerCrop()
                .into(binding.imgItemPhoto)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMoviesGridBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = images.size * 50

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val realPosition = position % images.size
        holder.bind(images[realPosition])
    }
}