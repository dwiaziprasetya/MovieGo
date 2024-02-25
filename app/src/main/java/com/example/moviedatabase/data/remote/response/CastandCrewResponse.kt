package com.example.moviedatabase.data.remote.response

import com.google.gson.annotations.SerializedName

data class CastandCrewResponse(
    @field:SerializedName("cast")
	val cast: List<CastItem>,

    val id: Int,
    val crew: List<CrewItem>
)

data class CrewItem(
	val gender: Int,
	val creditId: String,
	val knownForDepartment: String,
	val originalName: String,
	val popularity: Any,
	val name: String,
	val profilePath: String,
	val id: Int,
	val adult: Boolean,
	val department: String,
	val job: String
)

data class CastItem(
	val castId: Int,
	val character: String,
	val gender: Int,
	val creditId: String,
	val knownForDepartment: String,
	val originalName: String,
	val popularity: Any,
	@field:SerializedName("name")
	val name: String,
	@field:SerializedName("profile_path")
	val profilePath: String,
	val id: Int,
	val adult: Boolean,
	val order: Int
)

