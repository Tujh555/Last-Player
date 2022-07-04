package com.app.lastplayer.usecases.impl.database

import com.app.lastplayer.database.entities.TrackEntity
import com.app.lastplayer.repositories.DatabaseRepository
import com.app.lastplayer.usecases.database.DeleteTrackUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteTrackUseCaseImpl @Inject constructor(
    private val databaseRepository: DatabaseRepository
) : DeleteTrackUseCase {
    override suspend fun invoke(userId: String, track: TrackEntity) {
        withContext(Dispatchers.IO) {
            databaseRepository.deleteTrack(track)
        }
    }
}