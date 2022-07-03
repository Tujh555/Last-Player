package com.app.lastplayer.usecases.impl

import com.app.lastplayer.data.remote.JamendoResponse
import com.app.lastplayer.data.remote.Track
import com.app.lastplayer.repositories.JamendoApiRepository
import com.app.lastplayer.usecases.GetTracksUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetTracksUseCaseImpl @Inject constructor(
    private val jamendoApiRepository: JamendoApiRepository
) : GetTracksUseCase {
    override fun invoke(albumId: String, authorId: String): Flow<JamendoResponse<Track>> = flow {
        emit(
            jamendoApiRepository
                .getTracks(albumId = albumId, authorId = authorId)
        )
    }.flowOn(Dispatchers.IO)

}