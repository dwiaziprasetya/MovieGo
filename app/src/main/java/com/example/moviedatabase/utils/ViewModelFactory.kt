package com.example.moviedatabase.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviedatabase.di.Injection
import com.example.moviedatabase.repository.MovieDatabaseRepository
import com.example.moviedatabase.ui.activity.detail.DetailViewModel
import com.example.moviedatabase.ui.fragment.home.HomeViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private val repository: MovieDatabaseRepository,
    private val movieId: Int = -1
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(repository, movieId) as T
        } else if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

    companion object {
        fun getInstance(
            movieId: Int = -1,
        ) = ViewModelFactory(Injection.provideRepository(), movieId)
    }
}