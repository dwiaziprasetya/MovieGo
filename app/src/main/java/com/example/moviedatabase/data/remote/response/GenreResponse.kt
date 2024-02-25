package com.example.moviedatabase.data.remote.response

data class GenreResponse(
	val genres: List<GenresItem>
)

data class GenresItem(
	val name: String,
	val id: Int
)

