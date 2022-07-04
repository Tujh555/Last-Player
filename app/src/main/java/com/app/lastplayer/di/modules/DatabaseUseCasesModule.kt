package com.app.lastplayer.di.modules

import com.app.lastplayer.usecases.database.DeleteTrackUseCase
import com.app.lastplayer.usecases.database.GetUserWithTracksUseCase
import com.app.lastplayer.usecases.database.InsertTrackUseCase
import com.app.lastplayer.usecases.database.InsertUserUseCase
import com.app.lastplayer.usecases.impl.database.DeleteTrackUseCaseImpl
import com.app.lastplayer.usecases.impl.database.GetUserWithTracksUseCaseImpl
import com.app.lastplayer.usecases.impl.database.InsertTrackUseCaseImpl
import com.app.lastplayer.usecases.impl.database.InsertUserUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
interface DatabaseUseCasesModule {

    @Binds
    fun bindGetUserWithTracksUseCase(
        getUserWithTracksUseCase: GetUserWithTracksUseCaseImpl
    ): GetUserWithTracksUseCase

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
}