package com.app.lastplayer.repositories.impl

import android.util.Log
import com.app.lastplayer.database.UserDao
import com.app.lastplayer.database.crossRef.UserTrackCrossRef
import com.app.lastplayer.database.entities.TrackEntity
import com.app.lastplayer.database.entities.UserEntity
import com.app.lastplayer.repositories.DatabaseRepository
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor (
    private val userDao: UserDao
) : DatabaseRepository {
    override suspend fun getUserTracks(id: String): List<TrackEntity> {
        Log.d("MyLogs", "userId = $id")
        return userDao.getUserTracks(id)
    }

    override suspend fun insertUser(user: UserEntity) {
        userDao.insertUser(user)
    }

    override suspend fun insertTrack(track: TrackEntity) {
        Log.d("MyLogs", "REPOSITORY insertTrackName = ${track.name}")
        userDao.insertTrack(track)
    }

    override suspend fun insertCrossRef(ref: UserTrackCrossRef) {
        userDao.insertCrossRef(ref)
    }

    override suspend fun deleteTrack(track: TrackEntity) {
        userDao.deleteTrack(track)
    }

    override suspend fun deleteCrossRef(ref: UserTrackCrossRef) {
        userDao.deleteCrossRef(ref)
    }
}