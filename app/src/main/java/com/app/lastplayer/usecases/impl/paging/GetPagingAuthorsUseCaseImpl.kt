package com.app.lastplayer.usecases.impl.paging

import com.app.lastplayer.data.remote.Author
import com.app.lastplayer.repositories.JamendoApiRepository
import com.app.lastplayer.usecases.paging.GetPagingAuthorsUseCase
import javax.inject.Inject

class GetPagingAuthorsUseCaseImpl @Inject constructor(
    private val jamendoApiRepository: JamendoApiRepository
) : GetPagingAuthorsUseCase {
    override suspend fun invoke(offset: Int): List<Author> {
        return jamendoApiRepository.getAuthors(
            offset = offset,
            trackPath = "",
            order = "popularity_week_desc"
        ).body
    }
}