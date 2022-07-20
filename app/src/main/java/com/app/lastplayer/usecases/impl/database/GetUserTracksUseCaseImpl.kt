package com.app.lastplayer.usecases.impl.database

import com.app.lastplayer.database.entities.TrackEntity
import com.app.lastplayer.repositories.DatabaseRepository
import com.app.lastplayer.usecases.database.GetUserTracksUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetUserTracksUseCaseImpl @Inject constructor(
    private val databaseRepository: DatabaseRepository
) : GetUserTracksUseCase {
    override fun invoke(userId: String): Flow<List<TrackEntity>> = flow {
        val lst = databaseRepository.getUserTracks(userId)
        emit(
            lst
        )
    }.flowOn(Dispatchers.IO)
}