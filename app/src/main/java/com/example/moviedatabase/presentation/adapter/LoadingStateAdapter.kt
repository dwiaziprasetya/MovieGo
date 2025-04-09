package com.example.moviedatabase.presentation.adapter

import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedatabase.databinding.ItemLoadingBinding

class LoadingStateAdapter : LoadStateAdapter<LoadingStateAdapter.LoadingStateViewHolder>() {
    class LoadingStateViewHolder(
        private val binding: ItemLoadingBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                binding.errorMsg.text = loadState.error.localizedMessage
            }

            binding.progressBar.isVisible = loadState is LoadState.Loading
            binding.errorMsg.isVisible = loadState is LoadState.Error
        }
    }

    override fun onBindViewHolder(holder: LoadingStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): LoadingStateViewHolder {
        val binding = ItemLoadingBinding.inflate(
            android.view.LayoutInflater.from(parent.context),
            parent,
            false
        )

        return LoadingStateViewHolder(binding)
    }
}