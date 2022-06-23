package com.app.lastplayer

import android.content.Context
import com.app.lastplayer.di.AppComponent

val Context.appComponent: AppComponent
    get() = if (this is MainApplication) {
        this.appComponent
    } else {
        this.applicationContext.appComponent
    }