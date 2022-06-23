package com.app.lastplayer.data.remote

import com.google.gson.annotations.SerializedName

data class Track(
    @SerializedName("audiodownload_allowed")
    val isDownloadable: Boolean = false,

    val image: String = "",

    @SerializedName("shorturl")
    val shortUrl: String = "",

    @SerializedName("shareurl")
    val shareUrl: String = "",

    @SerializedName("audiodownload")
    val downloadUrl: String = "",

    @SerializedName("audio")
    val audioUrl: String = "",

    @SerializedName("album_image")
    val albumImage: String = "",

    @SerializedName("releasedate")
    val releaseDate: String = "",

    @SerializedName("album_id")
    val albumId: String = "",

    @SerializedName("album_name")
    val albumName: String = "",

    @SerializedName("artist_id")
    val authorId: String = "",

    @SerializedName("artist_name")
    val authorName: String = "",

    val duration: String = "",

    val name: String = "",

    val id: String = ""
)