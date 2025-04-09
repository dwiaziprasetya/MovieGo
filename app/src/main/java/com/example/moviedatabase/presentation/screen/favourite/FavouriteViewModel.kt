package com.example.moviedatabase.presentation.screen.favourite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedatabase.data.local.entity.Favourite
import com.example.moviedatabase.data.repository.MovieDatabaseRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FavouriteViewModel(private val repository: MovieDatabaseRepository) : ViewModel() {

    private val _favourites = MutableLiveData<List<Favourite>>()
    val favourites: LiveData<List<Favourite>> get() = _favourites

    private val _isLoading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _isLoading

    init {
        getAllFavourites()
    }

    fun getAllFavourites() {
        viewModelScope.launch {
            _isLoading.value = true
            delay(500)
            try {
                val response = repository.getAllFavourites()
                _favourites.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {  _isLoading.value = false }
        }
    }
}
