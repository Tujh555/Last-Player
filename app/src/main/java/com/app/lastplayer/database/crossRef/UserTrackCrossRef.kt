package com.app.lastplayer.database.crossRef

import androidx.room.Entity

@Entity(primaryKeys = ["trackId", "uid"])
data class UserTrackCrossRef(
    val trackId: String,
    val uid: String
)