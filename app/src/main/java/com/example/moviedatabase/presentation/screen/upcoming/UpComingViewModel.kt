package com.example.moviedatabase.presentation.screen.upcoming

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.moviedatabase.data.remote.response.UpComingMovieItem
import com.example.moviedatabase.data.repository.MovieDatabaseRepository

class UpComingViewModel(
    private val repository: MovieDatabaseRepository
): ViewModel() {
    val movie: LiveData<PagingData<UpComingMovieItem>> =
        repository.getUpComingMovieDataPaging().cachedIn(viewModelScope)
}