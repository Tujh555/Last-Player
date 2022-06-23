package com.app.lastplayer.data.remote

import com.google.gson.annotations.SerializedName

class JamendoTitle(
    @SerializedName("en")
    val english: String,

    @SerializedName("ru")
    val russian: String
)