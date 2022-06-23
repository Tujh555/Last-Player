package com.app.lastplayer.usecases

import com.app.lastplayer.data.remote.Author
import com.app.lastplayer.data.remote.JamendoResponse
import kotlinx.coroutines.flow.Flow

interface GetAuthorsUseCase {
    operator fun invoke(
        offset: Int = 0,
        trackPath: String = "",
        order: String = "popularity_week_desc"
    ): Flow<JamendoResponse<Author>>
}