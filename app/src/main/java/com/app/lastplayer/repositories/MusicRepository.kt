package com.app.lastplayer.repositories

import com.app.lastplayer.data.TrackSharedData
import kotlinx.coroutines.flow.Flow

interface MusicRepository {
    val nextTrack: TrackSharedData
    val previousTrack: TrackSharedData
    val currentTrack: TrackSharedData
    val trackCount: Int

    fun getTrackByIndex(index: Int): TrackSharedData

    fun fetchTracksFromAlbum(albumId: String): Flow<Unit>

    fun refreshTrackList(list: List<TrackSharedData>, currentPosition: Int? = 0)
}