package com.app.lastplayer

import android.app.Application
import com.app.lastplayer.di.AppComponent
import com.app.lastplayer.di.DaggerAppComponent

class MainApplication : Application() {
    private var _appComponent: AppComponent? = null
    val appComponent: AppComponent
        get() = requireNotNull(_appComponent) {
            "Application component wasn't initialized"
        }

    override fun onCreate() {
        super.onCreate()

        _appComponent = DaggerAppComponent.builder()
            .appContext(this)
            .build()
    }
}