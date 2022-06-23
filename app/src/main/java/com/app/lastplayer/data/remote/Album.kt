package com.app.lastplayer.data.remote

import com.google.gson.annotations.SerializedName

data class Album(
    val id: String = "",

    val name: String = "",

    @SerializedName("releasedate")
    val releaseDate: String = "",

    @SerializedName("artist_id")
    val authorId: String = "",

    @SerializedName("artist_name")
    val authorName: String = "",

    val image: String = "",

    @SerializedName("zip")
    val downloadArchiveUrl: String = "",

    @SerializedName("shorturl")
    val shortUrl: String = "",

    @SerializedName("shareurl")
    val shareUrl: String = "",

    @SerializedName("zip_allowed")
    val isDownloadable: Boolean = false,

    val tracks: List<Track> = emptyList()
)