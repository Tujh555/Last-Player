package com.app.lastplayer.di.modules

import com.app.lastplayer.repositories.JamendoApiRepository
import com.app.lastplayer.repositories.impl.JamendoApiRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {

    @Binds
    fun bindJamendoApiRepository(jamendoApiRepositoryImpl: JamendoApiRepositoryImpl): JamendoApiRepository
}