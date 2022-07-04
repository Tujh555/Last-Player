package com.app.lastplayer.repositories

import com.app.lastplayer.database.crossRef.UserTrackCrossRef
import com.app.lastplayer.database.entities.TrackEntity
import com.app.lastplayer.database.entities.UserEntity
import com.app.lastplayer.database.entities.UserWithTracks

interface DatabaseRepository {
    suspend fun getUserWithTracks(id: String): List<TrackEntity>

    suspend fun insertUser(user: UserEntity)

    suspend fun insertTrack(track: TrackEntity)

    suspend fun insertCrossRef(ref: UserTrackCrossRef)

    suspend fun deleteTrack(track: TrackEntity)

    suspend fun deleteCrossRef(ref: UserTrackCrossRef)
}