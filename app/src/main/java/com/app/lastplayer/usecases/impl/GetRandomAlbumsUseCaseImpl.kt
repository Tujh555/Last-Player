package com.app.lastplayer.usecases.impl

import com.app.lastplayer.data.remote.Album
import com.app.lastplayer.data.remote.JamendoResponse
import com.app.lastplayer.repositories.JamendoApiRepository
import com.app.lastplayer.usecases.GetRandomAlbumsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetRandomAlbumsUseCaseImpl @Inject constructor(
    private val jamendoApiRepository: JamendoApiRepository
) : GetRandomAlbumsUseCase {
    override fun invoke(): Flow<JamendoResponse<Album>> = jamendoApiRepository
        .getRandomAlbums()
        .flowOn(Dispatchers.IO)
}