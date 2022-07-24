package com.app.lastplayer.usecases.database

import com.app.lastplayer.database.entities.UserWithTracks
import kotlinx.coroutines.flow.Flow

interface GetUserWithTracksUseCase {
    suspend operator fun invoke(id: String): Flow<UserWithTracks?>
}