package com.app.lastplayer.di.appComponent.modules

import com.app.lastplayer.di.appComponent.modules.viewModels.ViewModelModule
import dagger.Module

@Module(
    includes = [
        NetworkModule::class,
        RepositoryModule::class,
        ViewModelModule::class,
        MediaModule::class,
        DatabaseModule::class
    ]
)
interface MainModule