package com.app.lastplayer.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.lastplayer.database.crossRef.UserTrackCrossRef
import com.app.lastplayer.database.entities.TrackEntity
import com.app.lastplayer.database.entities.UserEntity

@Database(
    entities = [
        TrackEntity::class,
        UserEntity::class,
        UserTrackCrossRef::class
    ],
    version = 1
)
abstract class Database : RoomDatabase() {
    abstract fun userDao(): UserDao
}