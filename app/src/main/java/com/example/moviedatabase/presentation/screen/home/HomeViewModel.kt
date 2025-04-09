package com.example.moviedatabase.presentation.screen.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedatabase.data.remote.response.NowPlayingMovieItem
import com.example.moviedatabase.data.remote.response.PopularMovieItem
import com.example.moviedatabase.data.remote.response.UpComingMovieItem
import com.example.moviedatabase.data.repository.MovieDatabaseRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: MovieDatabaseRepository
) : ViewModel() {

    private val _dataUpComingMovieItem = MutableLiveData<List<UpComingMovieItem>>()
    val dataUpComingMovieItem: MutableLiveData<List<UpComingMovieItem>> = _dataUpComingMovieItem

    private val _dataPopularMovieItem = MutableLiveData<List<PopularMovieItem>>()
    val dataPopularMovieItem: MutableLiveData<List<PopularMovieItem>> = _dataPopularMovieItem

    private val _dataNowPlayingMovieItem = MutableLiveData<List<NowPlayingMovieItem>>()
    val dataNowPlayingMovieItem: MutableLiveData<List<NowPlayingMovieItem>> = _dataNowPlayingMovieItem

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: MutableLiveData<Boolean> = _isLoading

    init {
        getUpComingMovieData()
        getPopularMovieData()
        getNowPlayingMovieData()
    }

    private fun getNowPlayingMovieData() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.getNowPlayingMovieData()
                _dataNowPlayingMovieItem.value = response.results
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun getPopularMovieData() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.getPopularMovieData()
                _dataPopularMovieItem.value = response.results
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun getUpComingMovieData() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.getUpComingMovieData()
                _dataUpComingMovieItem.value = response.results
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}