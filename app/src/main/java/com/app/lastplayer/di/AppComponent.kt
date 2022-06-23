package com.app.lastplayer.di

import com.app.lastplayer.di.modules.MainModule
import dagger.Component

@Component(modules = [ MainModule::class ])
interface AppComponent