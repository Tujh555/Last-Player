package com.app.lastplayer.usecases

import com.app.lastplayer.data.remote.Author
import com.app.lastplayer.data.remote.JamendoResponse
import kotlinx.coroutines.flow.Flow

interface GetAuthorUseCase {
    operator fun invoke(name: String, tracks: String = ""): Flow<JamendoResponse<Author>>
}