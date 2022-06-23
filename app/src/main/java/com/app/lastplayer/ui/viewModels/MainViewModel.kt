package com.app.lastplayer.ui.viewModels

import android.media.DeniedByServerException
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.lastplayer.data.remote.Album
import com.app.lastplayer.usecases.GetAlbumsUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getAlbumsUseCase: GetAlbumsUseCase
) : ViewModel() {
    private var _albums = MutableStateFlow<List<Album>>(listOf())
    val albums = _albums.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        Log.e("Exception", "$throwable in $coroutineContext")
    }

    fun getAlbums(authorName: String) {
        viewModelScope.launch(exceptionHandler) {
            getAlbumsUseCase(authorName).collect { response ->
                if (response.headers.status == "failed")
                    throw DeniedByServerException(response.headers.errorMessage)

                _albums.emit(response.body)
            }
        }
    }
}