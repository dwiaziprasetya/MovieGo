package com.example.moviedatabase.ui.fragment.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.moviedatabase.data.remote.response.NowPlayingMovieItem
import com.example.moviedatabase.data.remote.response.PopularMovieItem
import com.example.moviedatabase.data.remote.response.UpComingMovieItem
import com.example.moviedatabase.data.repository.MovieDatabaseRepository
import kotlinx.coroutines.Dispatchers

class HomeViewModel : ViewModel() {
    private val mRepository = MovieDatabaseRepository()

    val dataUpComingMovieItem: LiveData<List<UpComingMovieItem>> = liveData(
        context = viewModelScope.coroutineContext + Dispatchers.IO
    ) {
        val data = mRepository.getUpComingMovieData()
        emitSource(data)
    }

    val dataPopularMovieItem: LiveData<List<PopularMovieItem>> = liveData(
        context = viewModelScope.coroutineContext + Dispatchers.IO
    ) {
        val data = mRepository.getPopularMovieData()
        emitSource(data)
    }

    val dataNowPlayingMovieItem: LiveData<List<NowPlayingMovieItem>> = liveData(
        context = viewModelScope.coroutineContext + Dispatchers.IO
    ) {
        val data = mRepository.getNowPlayingMovieData()
        emitSource(data)
    }

    val isLoading: LiveData<Boolean> = liveData(
        context = viewModelScope.coroutineContext + Dispatchers.IO
    ) {
        val data = mRepository.isLoading
        emitSource(data)
    }
}