package com.app.lastplayer.usecases.impl

import com.app.lastplayer.data.remote.Author
import com.app.lastplayer.data.remote.JamendoResponse
import com.app.lastplayer.repositories.JamendoApiRepository
import com.app.lastplayer.usecases.GetAuthorUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetAuthorUseCaseImpl @Inject constructor(
    private val jamendoApiRepository: JamendoApiRepository
) : GetAuthorUseCase {
    override fun invoke(name: String, tracks: String): Flow<JamendoResponse<Author>> {
        return jamendoApiRepository
            .getAuthor(name = name, trackPath = tracks)
            .flowOn(Dispatchers.IO)
    }
}