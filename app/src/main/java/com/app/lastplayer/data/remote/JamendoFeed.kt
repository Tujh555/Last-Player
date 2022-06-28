package com.app.lastplayer.data.remote

import com.app.lastplayer.data.remote.JamendoImages
import com.app.lastplayer.data.remote.JamendoText
import com.app.lastplayer.data.remote.JamendoTitle
import com.google.gson.annotations.SerializedName

data class JamendoFeed(
    val id: String = "",

    val title: JamendoTitle? = null,

    val link: String = "",

    val text: JamendoText? = null,

    val type: String = "",

    @SerializedName("joindid")
    val joinId: String = "",

    val images: JamendoImages? = null
)
