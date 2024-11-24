package com.example.moviedatabase.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class NowPlayingMovieResponse(

    @field:SerializedName("dates")
	val dates: NowPlayingDates,

    @field:SerializedName("page")
	val page: Int,

    @field:SerializedName("total_pages")
	val totalPages: Int,

    @field:SerializedName("results")
	val results: List<NowPlayingMovieItem>,

    @field:SerializedName("total_results")
	val totalResults: Int
): Parcelable

@Parcelize
data class NowPlayingMovieItem(

	@field:SerializedName("overview") val overview: String,

	@field:SerializedName("original_language")
	val originalLanguage: String,

	@field:SerializedName("original_title")
	val originalTitle: String,

	@field:SerializedName("video")
	val video: Boolean,

	@field:SerializedName("title") val title: String,

	@field:SerializedName("genre_ids") val genreIds: List<Int>,

	@field:SerializedName("poster_path")
	val posterPath: String,

	@field:SerializedName("backdrop_path") val backdropPath: String,

	@field:SerializedName("release_date")
	val releaseDate: String,

	@field:SerializedName("popularity")
	val popularity: @RawValue Any,

	@field:SerializedName("vote_average")
	val voteAverage: @RawValue Any,

	@field:SerializedName("id") val id: Int,

	@field:SerializedName("adult")
	val adult: Boolean,

	@field:SerializedName("vote_count")
	val voteCount: Int
): Parcelable

@Parcelize
data class NowPlayingDates(

	@field:SerializedName("maximum")
	val maximum: String,

	@field:SerializedName("minimum")
	val minimum: String
): Parcelable
