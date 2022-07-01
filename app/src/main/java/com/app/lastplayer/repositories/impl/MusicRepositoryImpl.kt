package com.app.lastplayer.repositories.impl

import android.util.Log
import com.app.lastplayer.data.TrackSharedData
import com.app.lastplayer.data.remote.Track
import com.app.lastplayer.repositories.MusicRepository
import com.app.lastplayer.requireSuccessful
import com.app.lastplayer.usecases.GetTracksUseCase
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MusicRepositoryImpl @Inject constructor(
    private val getTracksUseCase: GetTracksUseCase
) : MusicRepository {
    private var currentItemIndex = 0
    private val tracks = mutableListOf<TrackSharedData>()

    init {
        Log.d("MyLogs", "in Repository INIT $tracks")
    }

    override val currentTrack: TrackSharedData
        get() = tracks[currentItemIndex]

    override val nextTrack: TrackSharedData
        get() {
            if (currentItemIndex == tracks.size - 1) {
                currentItemIndex = 0
            } else {
                currentItemIndex++
            }

            return currentTrack
        }

    override val previousTrack: TrackSharedData
        get() {
            if (currentItemIndex == 0) {
                currentItemIndex = tracks.size - 1
            } else {
                currentItemIndex--
            }

            return currentTrack
        }

    override val trackCount: Int
        get() = tracks.size

    override fun getTrackByIndex(index: Int) = tracks[index]

    override fun fetchTracksFromAlbum(albumId: String) = flow<Unit> {
        getTracksUseCase(albumId).collect { response ->
            requireSuccessful(response) {
                tracks.addAll(body.map { it.sharedData })
            }
        }
    }

    override fun refreshTrackList(list: List<TrackSharedData>, currentPosition: Int?) {
        if (currentPosition == null) {
            tracks.addAll(list)
        } else {
            tracks.clear()
            tracks.addAll(list)
            currentItemIndex = currentPosition
        }
        Log.d("MyLogs", "in Repository $tracks")
    }
}