package com.app.lastplayer.di.modules

import com.app.lastplayer.repositories.JamendoApiRepository
import com.app.lastplayer.repositories.MusicRepository
import com.app.lastplayer.repositories.impl.JamendoApiRepositoryImpl
import com.app.lastplayer.repositories.impl.MusicRepositoryImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface RepositoryModule {

    @Singleton
    @Binds
    fun bindJamendoApiRepository(jamendoApiRepositoryImpl: JamendoApiRepositoryImpl): JamendoApiRepository

    @Binds
    @Singleton
    fun bindMusicRepositoryImpl(musicRepositoryImpl: MusicRepositoryImpl): MusicRepository
}