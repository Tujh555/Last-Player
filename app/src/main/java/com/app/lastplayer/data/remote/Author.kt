package com.app.lastplayer.data.remote

import com.google.gson.annotations.SerializedName

data class Author(
    val id: String = "",

    val name: String = "",

    val website: String = "",

    @SerializedName("joindate")
    val dateOfAccession: String = "",

    val image: String = "",

    @SerializedName("shorturl")
    val shortUrl: String = "",

    @SerializedName("shareurl")
    val sharetUrl: String = "",

    val tracks: List<Track> = emptyList(),

    val albums: List<Album> = emptyList()
)