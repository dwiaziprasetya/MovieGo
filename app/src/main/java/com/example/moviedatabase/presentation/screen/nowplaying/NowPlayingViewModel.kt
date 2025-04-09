package com.example.moviedatabase.presentation.screen.nowplaying

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.moviedatabase.data.remote.response.NowPlayingMovieItem
import com.example.moviedatabase.data.repository.MovieDatabaseRepository

class NowPlayingViewModel(
    private val repository: MovieDatabaseRepository
): ViewModel() {
    val movie: LiveData<PagingData<NowPlayingMovieItem>> =
        repository.getNowPlayingMovieDataPaging().cachedIn(viewModelScope)
}