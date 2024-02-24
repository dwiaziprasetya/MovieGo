package com.example.moviedatabase.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.moviedatabase.repository.HomeRepository
import com.example.moviedatabase.response.NowPlayingMovieItem
import com.example.moviedatabase.response.PopularMovieItem
import com.example.moviedatabase.response.UpComingMovieItems

class HomeViewModel : ViewModel() {
    private val mRepository = HomeRepository()

    val dataUpComingMovieItems: LiveData<List<UpComingMovieItems>> = mRepository.getUpComingMovieData()

    val dataPopularMovieItem: LiveData<List<PopularMovieItem>> = mRepository.getPopularMovieData()

    val dataNowPlayingMovieItem: LiveData<List<NowPlayingMovieItem>> = mRepository.getNowPlayingMovieData()

    val isLoading: LiveData<Boolean> = mRepository.isLoading
}