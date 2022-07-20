package com.app.lastplayer.usecases.database

import com.app.lastplayer.database.entities.TrackEntity
import com.app.lastplayer.database.entities.UserWithTracks
import kotlinx.coroutines.flow.Flow

interface GetUserTracksUseCase {
    operator fun invoke(userId: String): Flow<List<TrackEntity>>
}