package com.app.lastplayer.usecases.impl

import com.app.lastplayer.data.remote.JamendoFeed
import com.app.lastplayer.data.remote.JamendoResponse
import com.app.lastplayer.repositories.JamendoApiRepository
import com.app.lastplayer.usecases.GetFeedsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetFeedsUseCaseImpl @Inject constructor(
    private val jamendoApiRepository: JamendoApiRepository
) : GetFeedsUseCase {
    override fun invoke(
        limit: Int,
        offset: Int,
        type: String,
        order: String
    ): Flow<JamendoResponse<JamendoFeed>> = flow {
        emit(
            jamendoApiRepository
                .getFeeds(limit = limit, offset = offset, type = type, order = order)
        )
    }.flowOn(Dispatchers.IO)
}