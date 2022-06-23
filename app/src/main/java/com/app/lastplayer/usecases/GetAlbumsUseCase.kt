package com.app.lastplayer.usecases

import com.app.lastplayer.data.remote.JamendoResponse
import com.app.lastplayer.data.remote.Album
import kotlinx.coroutines.flow.Flow

interface GetAlbumsUseCase {
    operator fun invoke(artistName: String): Flow<JamendoResponse<Album>>
}