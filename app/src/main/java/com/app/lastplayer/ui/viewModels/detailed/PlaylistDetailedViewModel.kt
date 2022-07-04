package com.app.lastplayer.ui.viewModels.detailed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.lastplayer.data.remote.Track
import com.app.lastplayer.database.entities.TrackEntity
import com.app.lastplayer.exceptionHandler
import com.app.lastplayer.repositories.DatabaseRepository
import com.app.lastplayer.requireBody
import com.app.lastplayer.usecases.GetPlaylistsUseCase
import com.app.lastplayer.usecases.database.InsertTrackUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class PlaylistDetailedViewModel @Inject constructor(
    private val insertTrackUseCase: InsertTrackUseCase,
    private val getPlaylistsUseCase: GetPlaylistsUseCase
) : ViewModel() {
    private val _tracksList = MutableStateFlow(listOf<Track>())
    var playlistName = ""
        private set
    val trackList = _tracksList.asStateFlow()

    fun insertTrack(track: TrackEntity) {
        viewModelScope.launch(exceptionHandler) {
            insertTrackUseCase(track.userKey, track)
        }
    }


    fun getTracksFromPlaylist(playlistId: String) {
        viewModelScope.launch(exceptionHandler) {
            getPlaylistsUseCase(playlistId = playlistId, tracks = "tracks").collect { response ->
                _tracksList.emit(requireBody(response).firstOrNull()?.tracks ?: emptyList())
                playlistName = response.body.firstOrNull()?.name ?: ""
            }
        }
    }
}