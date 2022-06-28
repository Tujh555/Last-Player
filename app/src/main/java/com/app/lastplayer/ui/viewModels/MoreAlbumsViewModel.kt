package com.app.lastplayer.ui.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.lastplayer.Constants.OFFSET_INCREASE
import com.app.lastplayer.data.remote.Album
import com.app.lastplayer.requireBody
import com.app.lastplayer.requireSuccessful
import com.app.lastplayer.usecases.GetAlbumsUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class MoreAlbumsViewModel @Inject constructor(
    private val getAlbumsUseCase: GetAlbumsUseCase
) : ViewModel() {
    private val _albumsList = MutableStateFlow(listOf<Album>())
    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        Log.e("MyLogs", "$throwable in $coroutineContext")
    }
    private var offset = 0
        get() {
            field += OFFSET_INCREASE
            return field
        }

    val albumsList = _albumsList.asStateFlow()
    var isAlbumsListScrolling = false

    fun getAlbums() {
        Log.d("MyLogs", "ViewModel.getAlbums()")

        viewModelScope.launch(exceptionHandler) {
            getAlbumsUseCase(offset = offset).collect { response ->
                _albumsList.emit(requireBody(response))  //TODO try catch
            }
        }
    }
}