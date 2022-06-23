package com.app.lastplayer.data.remote

import com.google.gson.annotations.SerializedName

class JamendoTags(
    val genres: List<String>,

    val instruments: List<String>,

    @SerializedName("vartags")
    val varTags: List<String>
)