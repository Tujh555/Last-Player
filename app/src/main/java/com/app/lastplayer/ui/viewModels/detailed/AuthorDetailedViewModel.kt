package com.app.lastplayer.ui.viewModels.detailed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.lastplayer.data.remote.Track
import com.app.lastplayer.exceptionHandler
import com.app.lastplayer.requireBody
import com.app.lastplayer.usecases.GetTracksUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthorDetailedViewModel @Inject constructor(
    private val getTracksUseCase: GetTracksUseCase
) : ViewModel() {
    private val _tracksList = MutableStateFlow(listOf<Track>())
    val trackList = _tracksList.asStateFlow()

    fun getTracksFromAuthor(authorId: String) {
        viewModelScope.launch(exceptionHandler) {
            getTracksUseCase(authorId = authorId).collect { response ->
                _tracksList.emit(requireBody(response))
            }
        }
    }
}