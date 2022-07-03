package com.app.lastplayer.usecases.paging

import com.app.lastplayer.data.remote.Author

interface GetPagingAuthorsUseCase {
    suspend operator fun invoke(offset: Int): List<Author>
}