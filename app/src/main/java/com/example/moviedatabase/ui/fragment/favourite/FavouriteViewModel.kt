package com.example.moviedatabase.ui.fragment.favourite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviedatabase.data.local.entity.Favourite
import com.example.moviedatabase.repository.MovieDatabaseRepository
import kotlinx.coroutines.launch

class FavouriteViewModel(private val repository:  MovieDatabaseRepository) : ViewModel() {

    private val _favourites = MutableLiveData<List<Favourite>>()
    val favourites: LiveData<List<Favourite>> get() = _favourites

    fun getAllFavourites() {
        viewModelScope.launch {
            _favourites.postValue(repository.getAllFavourites())
        }
    }
}
