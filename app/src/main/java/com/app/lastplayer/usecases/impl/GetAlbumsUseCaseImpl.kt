package com.app.lastplayer.usecases.impl

import com.app.lastplayer.repositories.JamendoApiRepository
import com.app.lastplayer.usecases.GetAlbumsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetAlbumsUseCaseImpl @Inject constructor(
    private val jamendoApiRepository: JamendoApiRepository
) : GetAlbumsUseCase {
    override fun invoke(artistName: String, offset: Int, tracks: String, order: String) =
        jamendoApiRepository
            .getAlbums(authorName = artistName, offset = offset, trackPath = tracks, order = order)
            .flowOn(Dispatchers.IO)
}