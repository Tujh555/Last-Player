package com.app.lastplayer.usecases

import com.app.lastplayer.data.remote.JamendoFeed
import com.app.lastplayer.data.remote.JamendoResponse
import kotlinx.coroutines.flow.Flow

interface GetFeedsUseCase {
    operator fun invoke(
        limit: Int = 10,
        offset: Int = 0,
        type: String = "news+update+interview",
        order: String = "date_start_desc"
    ): Flow<JamendoResponse<JamendoFeed>>
}