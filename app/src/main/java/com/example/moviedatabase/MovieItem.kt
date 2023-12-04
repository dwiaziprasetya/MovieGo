package com.example.moviedatabase

interface MovieItem {
    val id: Int
    val title: String
    val backdropPath: String
    val genreIds: List<Int>
    val overview: String
    fun movieRate(): Double
}