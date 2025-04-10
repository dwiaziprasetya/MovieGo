package com.example.moviedatabase.presentation.screen.popular

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.moviedatabase.data.remote.response.PopularMovieItem
import com.example.moviedatabase.data.repository.MovieDatabaseRepository

class PopularViewModel(
    private val repository: MovieDatabaseRepository
): ViewModel() {
    val movie: LiveData<PagingData<PopularMovieItem>> =
        repository.getPopularMovieDataPaging().cachedIn(viewModelScope)
}