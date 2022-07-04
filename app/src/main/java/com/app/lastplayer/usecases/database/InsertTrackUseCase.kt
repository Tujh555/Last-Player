package com.app.lastplayer.usecases.database

import com.app.lastplayer.database.entities.TrackEntity

interface InsertTrackUseCase {
    operator fun invoke(userId: String, track: TrackEntity)
}