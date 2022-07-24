package com.app.lastplayer.usecases.impl.database

import android.util.Log
import com.app.lastplayer.database.crossRef.UserTrackCrossRef
import com.app.lastplayer.database.entities.TrackEntity
import com.app.lastplayer.repositories.DatabaseRepository
import com.app.lastplayer.usecases.database.InsertTrackUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class InsertTrackUseCaseImpl @Inject constructor(
    private val databaseRepository: DatabaseRepository
) : InsertTrackUseCase {
    override suspend fun invoke(userId: String, track: TrackEntity) {
        withContext(Dispatchers.IO) {
            Log.d("MyLogs", "insertTrackUseCase = ${track.name}")

            databaseRepository.insertTrack(track, userId)
        }
    }
}