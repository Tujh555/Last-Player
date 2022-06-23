package com.app.lastplayer.usecases.impl

import com.app.lastplayer.data.remote.Author
import com.app.lastplayer.data.remote.JamendoResponse
import com.app.lastplayer.repositories.JamendoApiRepository
import com.app.lastplayer.usecases.GetAuthorsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetAuthorsUseCaseImpl @Inject constructor(
    private val jamendoApiRepository: JamendoApiRepository
) : GetAuthorsUseCase {
    override fun invoke(
        offset: Int,
        trackPath: String,
        order: String
    ): Flow<JamendoResponse<Author>> {
        return jamendoApiRepository
            .getAuthors(offset = offset, trackPath = trackPath, order = order)
            .flowOn(Dispatchers.IO)
    }
}