package com.app.lastplayer.repositories.impl

import android.util.Log
import com.app.lastplayer.database.UserDao
import com.app.lastplayer.database.crossRef.UserTrackCrossRef
import com.app.lastplayer.database.entities.TrackEntity
import com.app.lastplayer.database.entities.UserEntity
import com.app.lastplayer.database.entities.UserWithTracks
import com.app.lastplayer.repositories.DatabaseRepository
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor (
    private val userDao: UserDao
) : DatabaseRepository {
    override suspend fun getUserTracks(id: String): List<TrackEntity> {
        Log.d("MyLogs", "userId = $id")
        return userDao.getUserTracks()
    }

    override suspend fun getUserWithTracks(id: String): UserWithTracks? {
        val userWithTracks = userDao.getUserWithTrack(id)

        Log.d("MyLogs", "DatabaseRepository $userWithTracks")

        return userWithTracks
    }

    override suspend fun insertUser(user: UserEntity) {
        Log.d("MyLogs", "DatabaseRepository user $user")
        userDao.insertUser(user)
    }

    override suspend fun insertTrack(track: TrackEntity, uid: String) {
        Log.d("MyLogs", "DatabaseRepository insertTrackName = ${track.name}")
        userDao.insertTrack(track)
        insertCrossRef(uid, track.trackId)
    }

    private suspend fun insertCrossRef(uid: String, userTrackId: String) {
        userDao.insertCrossRef(
            UserTrackCrossRef(userTrackId, uid)
        )
    }

    override suspend fun deleteTrack(track: TrackEntity, uid: String) {
        userDao.deleteTrack(track)
        deleteCrossRef(uid, track.trackId)
    }

    private suspend fun deleteCrossRef(uid: String, userTrackId: String) {
        userDao.deleteCrossRef(
            UserTrackCrossRef(userTrackId, uid)
        )
    }
}