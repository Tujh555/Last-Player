package com.app.lastplayer.database.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.app.lastplayer.database.crossRef.UserTrackCrossRef

data class UserWithTracks(
    @Embedded val userEntity: UserEntity,
    @Relation(
        parentColumn = "uid",
        entityColumn = "trackId",
        associateBy = Junction(UserTrackCrossRef::class)
    )
    val tracks: List<TrackEntity>
)
