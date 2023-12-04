package com.example.moviedatabase.response

data class GenreResponse(
	val genres: List<GenresItem>
)

data class GenresItem(
	val name: String,
	val id: Int
)

