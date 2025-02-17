package com.example.moviedatabase.ui.fragment.movie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedatabase.data.remote.response.DiscoverMovieItem
import com.example.moviedatabase.repository.MovieDatabaseRepository
import kotlinx.coroutines.launch

class MovieViewModel(
    private val repository: MovieDatabaseRepository
): ViewModel() {
    private val _dataDiscoverMovieItem = MutableLiveData<List<DiscoverMovieItem>>()
    val dataDiscoverMovieItem: MutableLiveData<List<DiscoverMovieItem>> = _dataDiscoverMovieItem

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: MutableLiveData<Boolean> = _isLoading

    init {
        getDiscoverMovieData()
    }

    private fun getDiscoverMovieData() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.getDiscoverMovieData()
                _dataDiscoverMovieItem.value = response.results
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}