package com.app.lastplayer.data.remote

import com.google.gson.annotations.SerializedName

class JamendoResponse<T> (
    val headers: Headers,

    @SerializedName("results")
    val body: List<T>
) {
    val isSuccessfull: Boolean
        get() = headers.status == "success"
}