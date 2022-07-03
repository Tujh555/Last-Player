package com.app.lastplayer.usecases

import com.app.lastplayer.data.remote.JamendoResponse
import com.app.lastplayer.data.remote.Track
import kotlinx.coroutines.flow.Flow

interface GetTracksUseCase {
    operator fun invoke(albumId: String = "", authorId: String = ""): Flow<JamendoResponse<Track>>
}