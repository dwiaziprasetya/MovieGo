package com.example.moviedatabase.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviedatabase.data.repository.MovieDatabaseRepository
import com.example.moviedatabase.ui.activity.detail.DetailViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private val repository: MovieDatabaseRepository,
    private val movieId: Int = -1
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(repository, movieId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}