package com.app.lastplayer.usecases.impl.paging

import com.app.lastplayer.data.remote.Album
import com.app.lastplayer.repositories.JamendoApiRepository
import com.app.lastplayer.usecases.paging.GetPagingAlbumsUseCase
import javax.inject.Inject

class GetPagingAlbumsUseCaseImpl @Inject constructor(
    private val jamendoApiRepository: JamendoApiRepository
) : GetPagingAlbumsUseCase {

    override suspend fun invoke(offset: Int): List<Album> {
        return jamendoApiRepository.getAlbums(
            authorName = "",
            offset = offset,
            trackPath = "",
            order = "popularity_week_desc"
        ).body
    }
}