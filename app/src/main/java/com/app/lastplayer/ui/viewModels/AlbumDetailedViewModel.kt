package com.app.lastplayer.ui.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.lastplayer.data.remote.JamendoResponse
import com.app.lastplayer.data.remote.Track
import com.app.lastplayer.requireBody
import com.app.lastplayer.requireSuccessful
import com.app.lastplayer.usecases.GetTracksUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AlbumDetailedViewModel @Inject constructor(
    private val getTracksUseCase: GetTracksUseCase
) : ViewModel() {
    private val _tracksList = MutableStateFlow(listOf<Track>())
    val trackList = _tracksList.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        Log.e("MyLogs", "$throwable in $coroutineContext")
    }

    fun getTracksFromAlbum(albumId: String) {
        viewModelScope.launch(exceptionHandler) {
            getTracksUseCase(albumId = albumId).collect { response ->
                _tracksList.emit(requireBody(response))  //TODO try...catch
            }
        }
    }
}