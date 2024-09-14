package com.example.moviedatabase.data.remote.response

import android.os.Parcelable
import com.example.moviedatabase.utils.MovieItem
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

data class UpComingMovieResponse(

	@field:SerializedName("dates")
	val dates: Dates,

	@field:SerializedName("page")
	val page: Int,

	@field:SerializedName("total_pages")
	val totalPages: Int,

	@field:SerializedName("results")
	val results: List<UpComingMovieItem>,

	@field:SerializedName("total_results")
	val totalResults: Int
)

data class Dates(

	@field:SerializedName("maximum")
	val maximum: String,

	@field:SerializedName("minimum")
	val minimum: String
)

@Parcelize
data class UpComingMovieItem(

	@field:SerializedName("overview")
	override val overview: String,

	@field:SerializedName("original_language")
	val originalLanguage: String,

	@field:SerializedName("original_title")
	val originalTitle: String,

	@field:SerializedName("video")
	val video: Boolean,

	@field:SerializedName("title")
	override val title: String,

	@field:SerializedName("genre_ids")
	override val genreIds: List<Int>,

	@field:SerializedName("poster_path")
	val posterPath: String,

	@field:SerializedName("backdrop_path")
	override val backdropPath: String,

	@field:SerializedName("release_date")
	val releaseDate: String,

	@field:SerializedName("popularity")
	val popularity: @RawValue Any,

	@field:SerializedName("vote_average")
	val voteAverage: @RawValue Any,

	@field:SerializedName("id")
	override val id: Int,

	@field:SerializedName("adult")
	val adult: Boolean,

	@field:SerializedName("vote_count")
	val voteCount: Int
) : Parcelable, MovieItem {
	override fun movieRate(): Double {
		return voteAverage as Double / 2
	}
}
