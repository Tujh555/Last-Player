package com.app.lastplayer.di.modules

import com.app.lastplayer.usecases.GetAlbumsUseCase
import com.app.lastplayer.usecases.impl.GetAlbumsUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
interface UseCasesModule {

    @Binds
    fun bindGetAlbumsUseCase(getAlbumsUseCaseImpl: GetAlbumsUseCaseImpl): GetAlbumsUseCase
}