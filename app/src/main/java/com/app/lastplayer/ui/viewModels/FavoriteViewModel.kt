package com.app.lastplayer.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.lastplayer.data.remote.Track
import com.app.lastplayer.database.entities.TrackEntity
import com.app.lastplayer.usecases.database.DeleteTrackUseCase
import com.app.lastplayer.usecases.database.GetUserWithTracksUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(
    private val deleteTrackUseCase: DeleteTrackUseCase,
    private val getUserWithTracksUseCase: GetUserWithTracksUseCase
) : ViewModel() {
    private val _tracks = MutableStateFlow(listOf<Track>())
    val tracks = _tracks.asStateFlow()

    fun deleteTrack(track: TrackEntity) {
        viewModelScope.launch {
            deleteTrackUseCase(track.userKey, track)
        }
    }

    fun getUserTracks(uid: String) {
        viewModelScope.launch {
            getUserWithTracksUseCase(uid).collect { lst ->
                _tracks.emit(lst.map { it.toTrack() })
            }
        }
    }
}