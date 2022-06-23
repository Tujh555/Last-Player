package com.app.lastplayer.usecases

import com.app.lastplayer.data.remote.Album
import com.app.lastplayer.data.remote.JamendoResponse
import kotlinx.coroutines.flow.Flow

interface GetRandomAlbumsUseCase {
    operator fun invoke(): Flow<JamendoResponse<Album>>
}