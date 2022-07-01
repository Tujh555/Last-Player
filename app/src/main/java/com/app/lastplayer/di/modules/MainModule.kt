package com.app.lastplayer.di.modules

import com.app.lastplayer.di.modules.viewModels.ViewModelModule
import dagger.Module

@Module(
    includes = [
        NetworkModule::class,
        RepositoryModule::class,
        ViewModelModule::class,
        MediaModule::class,
    ]
)
interface MainModule