package com.app.lastplayer.paging.authors

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.app.lastplayer.Constants.START_PAGING_INDEX
import com.app.lastplayer.data.remote.Author
import com.app.lastplayer.usecases.paging.GetPagingAuthorsUseCase
import javax.inject.Inject

class AuthorsPagingSource @Inject constructor(
    private val getPagingAuthorsUseCase: GetPagingAuthorsUseCase
) : PagingSource<Int, Author>() {
    override fun getRefreshKey(state: PagingState<Int, Author>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(10)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(10)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Author> {
        val pageIndex = params.key ?: START_PAGING_INDEX

        return try {

            val body = getPagingAuthorsUseCase(pageIndex)

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