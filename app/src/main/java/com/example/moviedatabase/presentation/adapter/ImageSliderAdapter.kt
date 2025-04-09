package com.example.moviedatabase.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.moviedatabase.data.local.model.ImageData
import com.example.moviedatabase.databinding.ItemSlideBinding

class ImageSliderAdapter(private val items: ArrayList<ImageData>, private val viewPager2: ViewPager2) : RecyclerView.Adapter<ImageSliderAdapter.ImageViewHolder>(){
    inner class ImageViewHolder(itemView : ItemSlideBinding) : RecyclerView.ViewHolder(itemView.root){
        private val binding = itemView
        fun bind(data: ImageData){
            with(binding){
                Glide.with(itemView)
                    .load(data.image)
                    .into(ivSlider)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(ItemSlideBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(items[position])
        if (position == items.size - 2){
            viewPager2.post(runnable)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private val runnable = Runnable {
        items.addAll(items)
        notifyDataSetChanged()
    }
}