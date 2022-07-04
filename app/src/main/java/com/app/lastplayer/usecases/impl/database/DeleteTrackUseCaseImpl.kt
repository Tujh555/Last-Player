package com.app.lastplayer.usecases.impl.database

import com.app.lastplayer.database.crossRef.UserTrackCrossRef
import com.app.lastplayer.database.entities.TrackEntity
import com.app.lastplayer.repositories.DatabaseRepository
import com.app.lastplayer.usecases.database.DeleteTrackUseCase
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteTrackUseCaseImpl @Inject constructor(
    private val databaseRepository: DatabaseRepository
) : DeleteTrackUseCase {
    override fun invoke(userId: String, track: TrackEntity) {
        flow<Unit> {
            databaseRepository.deleteTrack(track)
        }
    }
}