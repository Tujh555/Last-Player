package com.app.lastplayer.database

import androidx.room.*
import com.app.lastplayer.database.crossRef.UserTrackCrossRef
import com.app.lastplayer.database.entities.TrackEntity
import com.app.lastplayer.database.entities.UserEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM TrackEntity WHERE userKey = (:id)")
    suspend fun getUserWithTracks(id: String): List<TrackEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrack(track: TrackEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCrossRef(ref: UserTrackCrossRef)

    @Delete
    suspend fun deleteTrack(track: TrackEntity)

    @Delete
    suspend fun deleteCrossRef(ref: UserTrackCrossRef)
}