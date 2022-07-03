package com.app.lastplayer.ui.viewModels.more

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.app.lastplayer.data.remote.Album
import javax.inject.Inject

class MoreAlbumsViewModel @Inject constructor(
    private val albumsPagingSource: PagingSource<Int, Album>
) : ViewModel() {

    val albumsList = Pager(
        config = PagingConfig(
            pageSize = 2,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            albumsPagingSource
        }
    ).flow.cachedIn(viewModelScope)
}