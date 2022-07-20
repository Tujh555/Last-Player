package com.app.lastplayer.di.appComponent.modules

import com.app.lastplayer.repositories.DatabaseRepository
import com.app.lastplayer.repositories.JamendoApiRepository
import com.app.lastplayer.repositories.MusicRepository
import com.app.lastplayer.repositories.impl.DatabaseRepositoryImpl
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

    @Binds
    @Singleton
    fun bindDatabaseRepository(databaseRepositoryImpl: DatabaseRepositoryImpl): DatabaseRepository
}