package com.app.lastplayer.data.remote

import com.google.gson.annotations.SerializedName

data class Playlist(
    val id: String,

    val name: String,

    @SerializedName("creationdate")
    val creationDate: String,

    @SerializedName("user_id")
    val userId: String,

    @SerializedName("user_name")
    val userName: String,

    @SerializedName("zip")
    val downloadArchiveUrl: String,

    @SerializedName("shorturl")
    val shortUrl: String,

    @SerializedName("shareurl")
    val shareUrl: String,

    val tracks: List<Track> = emptyList()
)