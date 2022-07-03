package com.app.lastplayer.di.modules

import com.app.lastplayer.usecases.*
import com.app.lastplayer.usecases.impl.*
import com.app.lastplayer.usecases.impl.paging.GetPagingAlbumsUseCaseImpl
import com.app.lastplayer.usecases.impl.paging.GetPagingAuthorsUseCaseImpl
import com.app.lastplayer.usecases.impl.paging.GetPagingPlaylistsUseCaseImpl
import com.app.lastplayer.usecases.paging.GetPagingAlbumsUseCase
import com.app.lastplayer.usecases.paging.GetPagingAuthorsUseCase
import com.app.lastplayer.usecases.paging.GetPagingPlaylistUseCase
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

    @Binds
    fun bindGetPagingAlbumsUseCase(
        getPagingAlbumsUseCaseImpl: GetPagingAlbumsUseCaseImpl
    ): GetPagingAlbumsUseCase

    @Binds
    fun bindGetPagingAuthorsUseCase(
        getPagingAuthorsUseCaseImpl: GetPagingAuthorsUseCaseImpl
    ): GetPagingAuthorsUseCase

    @Binds
    fun bindGetPagingPlaylistsUseCase(
        getPagingPlaylistsUseCaseImpl: GetPagingPlaylistsUseCaseImpl
    ): GetPagingPlaylistUseCase
}