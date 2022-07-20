package com.app.lastplayer.di.appComponent.modules

import com.app.lastplayer.usecases.database.DeleteTrackUseCase
import com.app.lastplayer.usecases.database.GetUserTracksUseCase
import com.app.lastplayer.usecases.database.InsertTrackUseCase
import com.app.lastplayer.usecases.database.InsertUserUseCase
import com.app.lastplayer.usecases.impl.database.DeleteTrackUseCaseImpl
import com.app.lastplayer.usecases.impl.database.GetUserTracksUseCaseImpl
import com.app.lastplayer.usecases.impl.database.InsertTrackUseCaseImpl
import com.app.lastplayer.usecases.impl.database.InsertUserUseCaseImpl
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
}