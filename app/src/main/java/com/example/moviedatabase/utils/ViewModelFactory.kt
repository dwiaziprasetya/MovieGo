package com.example.moviedatabase.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviedatabase.data.repository.MovieDatabaseRepository
import com.example.moviedatabase.di.Injection
import com.example.moviedatabase.presentation.screen.detail.DetailViewModel
import com.example.moviedatabase.presentation.screen.favourite.FavouriteViewModel
import com.example.moviedatabase.presentation.screen.home.HomeViewModel
import com.example.moviedatabase.presentation.screen.movie.MovieViewModel
import com.example.moviedatabase.presentation.screen.nowplaying.NowPlayingViewModel
import com.example.moviedatabase.presentation.screen.popular.PopularViewModel
import com.example.moviedatabase.presentation.screen.upcoming.UpComingViewModel

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
        } else if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
            return MovieViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(FavouriteViewModel::class.java)) {
            return FavouriteViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(NowPlayingViewModel::class.java)) {
            return NowPlayingViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(PopularViewModel::class.java)) {
            return PopularViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(UpComingViewModel::class.java)) {
            return UpComingViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

    companion object {
        fun getInstance(
            context: Context,
            movieId: Int = -1,
        ) = ViewModelFactory(Injection.provideRepository(context), movieId)
    }
}