package com.app.lastplayer.paging.playlists

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.app.lastplayer.Constants.START_PAGING_INDEX
import com.app.lastplayer.data.remote.Playlist
import com.app.lastplayer.usecases.paging.GetPagingPlaylistUseCase
import javax.inject.Inject

class PlaylistsPagingSource @Inject constructor(
    private val getPagingPlaylistUseCase: GetPagingPlaylistUseCase
) : PagingSource<Int, Playlist>() {
    override fun getRefreshKey(state: PagingState<Int, Playlist>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(10)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(10)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Playlist> {
        val pageIndex = params.key ?: START_PAGING_INDEX

        return try {

            val body = getPagingPlaylistUseCase(pageIndex)

            val nextKey = if (body.isEmpty()) {
                null
            } else {
                pageIndex + 10
            }

            LoadResult.Page(
                data = body,
                prevKey = if (pageIndex == START_PAGING_INDEX) null else pageIndex,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}