package com.app.lastplayer.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.lastplayer.data.TrackSharedData
import com.app.lastplayer.data.remote.Track

@Entity
data class TrackEntity(
    @PrimaryKey val trackId: String,
    val audio: String,
    val name: String,
    val image: String,
    val author: String,
    val duration: String,
    val album: String,
    val userKey: String
) {

    fun toTrack() = Track(
        id = trackId,
        image = image,
        name = name,
        authorName = author,
        audioUrl = audio,
        duration = duration,
        albumName = album
    )
}