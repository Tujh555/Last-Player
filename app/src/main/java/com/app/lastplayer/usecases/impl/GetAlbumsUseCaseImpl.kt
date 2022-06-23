package com.app.lastplayer.usecases.impl

import com.app.lastplayer.data.remote.JamendoResponse
import com.app.lastplayer.data.remote.Album
import com.app.lastplayer.repositories.JamendoApiRepository
import com.app.lastplayer.usecases.GetAlbumsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetAlbumsUseCaseImpl @Inject constructor(
    private val jamendoApiRepository: JamendoApiRepository
) : GetAlbumsUseCase {
    override fun invoke(artistName: String): Flow<JamendoResponse<Album>> =
        jamendoApiRepository.getAlbums(artistName).flowOn(Dispatchers.IO)
}