package com.app.lastplayer.repositories

import com.app.lastplayer.database.entities.TrackEntity
import com.app.lastplayer.database.entities.UserEntity
import com.app.lastplayer.database.entities.UserWithTracks

interface DatabaseRepository {
    suspend fun getUserTracks(id: String): List<TrackEntity>

    suspend fun getUserWithTracks(id: String): UserWithTracks?

    suspend fun insertUser(user: UserEntity)

    suspend fun insertTrack(track: TrackEntity, uid: String)

    suspend fun deleteTrack(track: TrackEntity, uid: String)
}