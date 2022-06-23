package com.app.lastplayer.data.remote

import com.google.gson.annotations.SerializedName

class JamendoText(
    @SerializedName("en")
    val english: String,

    @SerializedName("ru")
    val russian: String
)