package com.app.lastplayer.ui.viewModels.more

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import com.app.lastplayer.data.remote.Author
import com.app.lastplayer.pagingConfig
import javax.inject.Inject

class MoreAuthorsViewModel @Inject constructor(
    private val authorsPagingSource: PagingSource<Int, Author>
) : ViewModel() {

    val authorsList = Pager(
        config = pagingConfig,
        pagingSourceFactory = {
            authorsPagingSource
        }
    ).flow.cachedIn(viewModelScope)
}