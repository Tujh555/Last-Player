package com.app.lastplayer

import android.content.Context
import android.media.DeniedByServerException
import android.text.TextUtils
import android.util.Log
import android.widget.EditText
import androidx.paging.PagingConfig
import com.app.lastplayer.data.remote.JamendoResponse
import com.app.lastplayer.di.AppComponent
import kotlinx.coroutines.CoroutineExceptionHandler

fun EditText.requireText(): String {
    if (!TextUtils.isEmpty(this.text)) {
        return this.text.toString()
    } else {
        throw IllegalStateException("Text was null")
    }
}

val Context.appComponent: AppComponent
    get() = if (this is MainApplication) {
        this.appComponent
    } else {
        this.applicationContext.appComponent
    }

inline fun <T> requireSuccessful(response: JamendoResponse<T>, block: JamendoResponse<T>.() -> Unit) {
    if (response.isSuccessful) {
        response.block()
    } else {
        throw DeniedByServerException(response.headers.errorMessage)
    }
}

fun <T> requireBody(response: JamendoResponse<T>) = if (response.isSuccessful) {
    response.body
} else {
    throw DeniedByServerException("Response hasn't body: ${response.headers.errorMessage}")
}

fun String.toTrackDuration() = if (this.all { it.isDigit() }) {
    val time = this.toInt()
    val minutes = (time / 60).toString()
    val seconds = (time % 60).toString()

    if (seconds.length == 1) {
        "$minutes:0$seconds"
    } else {
        "$minutes:$seconds"
    }
} else {
    "--:--"
}

val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
    Log.e("MyLogs", "$throwable in $coroutineContext")
}

val pagingConfig = PagingConfig(
    pageSize = 2,
    enablePlaceholders = false
)
