package com.app.lastplayer.ui.viewModels.more

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import com.app.lastplayer.data.remote.Playlist
import com.app.lastplayer.pagingConfig
import javax.inject.Inject

class MorePlaylistsViewModel @Inject constructor(
    private val playlistsPagingSource: PagingSource<Int, Playlist>
) : ViewModel() {

    val playlistList = Pager(
        config = pagingConfig,
        pagingSourceFactory = {
            playlistsPagingSource
        }
    ).flow.cachedIn(viewModelScope)
}