package com.app.lastplayer.ui.viewModels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.app.lastplayer.data.remote.Track
import com.app.lastplayer.database.UserDao
import com.app.lastplayer.exceptionHandler
import com.app.lastplayer.usecases.database.GetUserWithTracksUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.util.*
import java.util.concurrent.Executors
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(
    private val userDao: UserDao,
    private val getUserWithTracksUseCase: GetUserWithTracksUseCase
) : ViewModel() {
    private val _tracks = MutableStateFlow(listOf<Track>())
    val tracks = _tracks.asStateFlow()

    fun getUserTracks(uid: String) {
        viewModelScope.launch {
            getUserWithTracksUseCase(uid).collect { lst ->
                _tracks.emit(lst.map { it.toTrack() })
            }
        }
    }
}