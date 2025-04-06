package com.example.moviedatabase.ui.activity.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedatabase.data.local.entity.Favourite
import com.example.moviedatabase.data.remote.response.CastandCrewResponse
import com.example.moviedatabase.data.remote.response.DetailMovieResponse
import com.example.moviedatabase.repository.MovieDatabaseRepository
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: MovieDatabaseRepository,
    private val movieId: Int
) : ViewModel() {

    private val _movieDetail = MutableLiveData<DetailMovieResponse?>()
    val movieDetail: MutableLiveData<DetailMovieResponse?> = _movieDetail

    private val _movieCredits = MutableLiveData<CastandCrewResponse?>()
    val movieCredits: MutableLiveData<CastandCrewResponse?> = _movieCredits

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        getMovieDetail(movieId)
        getMovieCredits(movieId)
    }

    private fun getMovieCredits(movieId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.getMovieCredits(movieId)
                _movieCredits.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun getMovieDetail(movieId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.getMovieDetail(movieId)
                _movieDetail.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun isMovieFavourite(movieName: String): LiveData<Boolean> = repository.isMovieFavourite(movieName)

    fun addToFavorite(
        movieId: Int,
        movieName: String,
        moviePhoto: String
    ) {
        viewModelScope.launch {
            val movie = Favourite(
                movieId = movieId,
                movieName = movieName,
                moviePhoto = moviePhoto
            )
            repository.addToFavourite(movie)
        }
    }
}