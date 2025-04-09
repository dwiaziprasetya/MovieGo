package com.example.moviedatabase.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Favourite(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "movie_id")
    var movieId: Int,

    @ColumnInfo(name = "movie_name")
    var movieName: String,

    @ColumnInfo(name = "movie_photo")
    var moviePhoto: String,

    @ColumnInfo(name = "genres_item")
    var genres: String
) : Parcelable
