package com.app.lastplayer

import android.content.Context
import android.media.DeniedByServerException
import com.app.lastplayer.data.remote.JamendoResponse
import com.app.lastplayer.di.AppComponent

val Context.appComponent: AppComponent
    get() = if (this is MainApplication) {
        this.appComponent
    } else {
        this.applicationContext.appComponent
    }

fun <T> requireSuccessful(response: JamendoResponse<T>, block: JamendoResponse<T>.() -> Unit) {
    if (response.isSuccessfull) {
        response.block()
    } else {
        throw throw DeniedByServerException(response.headers.errorMessage)
    }
}

fun <T> requireBody(response: JamendoResponse<T>) = if (response.isSuccessfull) {
    response.body
} else {
    throw DeniedByServerException("Response hasn't body: ${response.headers.errorMessage}")
}