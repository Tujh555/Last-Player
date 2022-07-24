package com.app.lastplayer.di.appComponent.modules

import com.app.lastplayer.usecases.database.*
import com.app.lastplayer.usecases.impl.database.*
import dagger.Binds
import dagger.Module

@Module
interface DatabaseUseCasesModule {

    @Binds
    fun bindGetUserTracksUseCase(
        getUserTracksUseCase: GetUserTracksUseCaseImpl
    ): GetUserTracksUseCase

    @Binds
    fun bindTrackUserUseCase(
        deleteTrackUseCaseImpl: DeleteTrackUseCaseImpl
    ): DeleteTrackUseCase

    @Binds
    fun bindInsertTrackUseCase(
        insertTrackUseCaseImpl: InsertTrackUseCaseImpl
    ): InsertTrackUseCase

    @Binds
    fun bindInsertUserUseCase(
        insertUserUseCaseImpl: InsertUserUseCaseImpl
    ): InsertUserUseCase

    @Binds
    fun bindGetUserWithTracksUseCase(
        getUserWithTracksUseCaseImpl: GetUserWithTracksUseCaseImpl
    ): GetUserWithTracksUseCase
}