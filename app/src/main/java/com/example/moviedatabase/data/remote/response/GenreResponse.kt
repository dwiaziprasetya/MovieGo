package com.example.moviedatabase.data.remote.response

data class GenreResponse(
	val genres: List<GenreItem>
)

data class GenreItem(
	val name: String,
	val id: Int
)

