package com.app.lastplayer.data.remote

import com.google.gson.annotations.SerializedName

data class Headers(
    val status: String,

    val code: Int,

    @SerializedName("error_message")
    val errorMessage: String,

    val warnings: String,

    @SerializedName("results_count")
    val resultsCount: Int,

    @SerializedName("next")
    val nextUrl: String = ""
)