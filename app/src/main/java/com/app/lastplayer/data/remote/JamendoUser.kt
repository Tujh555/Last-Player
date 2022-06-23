package com.app.lastplayer.data.remote

import com.google.gson.annotations.SerializedName

data class JamendoUser(
    val name: String,

    @SerializedName("dispname")
    val displayName: String,

    val id: String,

    val lang: String,

    val image: String
)