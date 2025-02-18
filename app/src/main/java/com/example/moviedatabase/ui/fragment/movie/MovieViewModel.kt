package com.example.moviedatabase.ui.fragment.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.moviedatabase.data.remote.response.DiscoverMovieItem
import com.example.moviedatabase.repository.MovieDatabaseRepository

class MovieViewModel(
    private val repository: MovieDatabaseRepository
): ViewModel() {
    val movie: LiveData<PagingData<DiscoverMovieItem>> =
        repository.getDiscoverMovieDataPaging().cachedIn(viewModelScope)


//    private val _dataDiscoverMovieItem = MutableLiveData<List<DiscoverMovieItem>>()
//    val dataDiscoverMovieItem: MutableLiveData<List<DiscoverMovieItem>> = _dataDiscoverMovieItem
//
//    private val _isLoading = MutableLiveData<Boolean>()
//    val isLoading: MutableLiveData<Boolean> = _isLoading
//
//    init {
//        getDiscoverMovieData()
//    }
//
//    private fun getDiscoverMovieData() {
//        viewModelScope.launch {
//            _isLoading.value = true
//            try {
//                val response = repository.getDiscoverMovieData()
//                _dataDiscoverMovieItem.value = response.results
//            } catch (e: Exception) {
//                e.printStackTrace()
//            } finally {
//                _isLoading.value = false
//            }
//        }
//    }
}