package com.app.lastplayer.ui.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.lastplayer.data.MainListData
import com.app.lastplayer.data.MainListItem
import com.app.lastplayer.data.remote.*
import com.app.lastplayer.requireSuccessful
import com.app.lastplayer.ui.MainDataType
import com.app.lastplayer.usecases.GetAlbumsUseCase
import com.app.lastplayer.usecases.GetAuthorsUseCase
import com.app.lastplayer.usecases.GetFeedsUseCase
import com.app.lastplayer.usecases.GetPlaylistsUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getAlbumsUseCase: GetAlbumsUseCase,
    private val getAuthorsUseCase: GetAuthorsUseCase,
    private val getPlaylistsUseCase: GetPlaylistsUseCase,
    private val getFeedsUseCase: GetFeedsUseCase
) : ViewModel() {
    private val _mainListItems = MutableStateFlow(listOf<MainListItem>())
    private val items = mutableListOf<MainListItem>()

    val mainListItems = _mainListItems.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        Log.e("MyLogs", "$throwable in $coroutineContext")
        retryConnection()
    }

    private val albumCollector = FlowCollector<JamendoResponse<Album>> { response ->
        requireSuccessful(response) {
            items.add(
                MainListItem(
                    title = "Albums",
                    body.map { album -> MainListData.AlbumItem(album) },
                    MainDataType.ALBUM.ordinal
                )
            )
        }
    }

    private val authorsCollector = FlowCollector<JamendoResponse<Author>> { response ->
        requireSuccessful(response) {
            items.add(
                MainListItem(
                    title = "Authors",  //TODO resources
                    body.map { author -> MainListData.AuthorItem(author) },
                    MainDataType.AUTHOR.ordinal
                )
            )
        }
    }

    private val playlistsCollector = FlowCollector<JamendoResponse<Playlist>> { response ->
        requireSuccessful(response) {
            items.add(
                MainListItem(
                    title = "Playlists",
                    response.body.map { playlist -> MainListData.PlaylistItem(playlist) },
                    MainDataType.PLAYLIST.ordinal
                )
            )
        }
    }

    private val feedsCollector = FlowCollector<JamendoResponse<JamendoFeed>> { response ->
        requireSuccessful(response) {
            items.add(
                MainListItem(
                    title = "Feeds",
                    response.body.map { feed -> MainListData.FeedItem(feed) }.toMutableList(),
                    MainDataType.FEED.ordinal
                )
            )
        }
    }

    private fun retryConnection() {
        viewModelScope.launch(exceptionHandler) {
            delay(3000L)
            initListItems()
        }
    }

    fun initListItems() {
        viewModelScope.launch(exceptionHandler) {
            getAlbumsUseCase().collect(albumCollector)

            getAuthorsUseCase().collect(authorsCollector)

            getPlaylistsUseCase().collect(playlistsCollector)

            getFeedsUseCase().collect(feedsCollector)

            _mainListItems.emit(items)
        }
    }
}