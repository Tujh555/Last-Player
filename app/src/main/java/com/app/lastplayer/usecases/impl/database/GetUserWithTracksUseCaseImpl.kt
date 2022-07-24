package com.app.lastplayer.usecases.impl.database

import com.app.lastplayer.database.entities.UserWithTracks
import com.app.lastplayer.repositories.DatabaseRepository
import com.app.lastplayer.usecases.database.GetUserWithTracksUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetUserWithTracksUseCaseImpl @Inject constructor(
    private val databaseRepository: DatabaseRepository
) : GetUserWithTracksUseCase {
    override suspend fun invoke(id: String): Flow<UserWithTracks?> = flow {
        emit(
            databaseRepository.getUserWithTracks(id)
        )
    }.flowOn(Dispatchers.IO)
}