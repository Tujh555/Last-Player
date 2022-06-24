package com.app.lastplayer.di.modules

import com.app.lastplayer.usecases.*
import com.app.lastplayer.usecases.impl.*
import dagger.Binds
import dagger.Module

@Module
interface UseCasesModule {

    @Binds
    fun bindGetAlbumsUseCase(
        getAlbumsUseCaseImpl: GetAlbumsUseCaseImpl
    ): GetAlbumsUseCase

    @Binds
    fun bindGetAuthorsUseCase(
        getAuthorsUseCaseImpl: GetAuthorsUseCaseImpl
    ): GetAuthorsUseCase

    @Binds
    fun bindGetAuthorUseCase(
        getAuthorUseCaseImpl: GetAuthorUseCaseImpl
    ): GetAuthorUseCase

    @Binds
    fun bindGetFeedsUseCase(
        getFeedsUseCaseImpl: GetFeedsUseCaseImpl
    ): GetFeedsUseCase

    @Binds
    fun bindGetPlaylistsUseCase(
        getPlaylistsUseCaseImpl: GetPlaylistsUseCaseImpl
    ): GetPlaylistsUseCase

    @Binds
    fun bindGetTracksUseCase(
        getTracksUseCaseImpl: GetTracksUseCaseImpl
    ): GetTracksUseCase
}