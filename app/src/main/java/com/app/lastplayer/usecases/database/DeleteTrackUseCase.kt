package com.app.lastplayer.usecases.database

import com.app.lastplayer.database.entities.TrackEntity

interface DeleteTrackUseCase {
    operator fun invoke(userId: String, track: TrackEntity)
}