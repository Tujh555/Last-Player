package com.app.lastplayer.di.appComponent.modules

import android.content.Context
import androidx.room.Room
import com.app.lastplayer.database.Database
import com.app.lastplayer.database.UserDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ DatabaseUseCasesModule::class ])
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(appContext: Context): Database =
        Room.databaseBuilder(
            appContext,
            Database::class.java,
            "track-db"
        ).fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideUserDao(database: Database): UserDao = database.userDao()
}