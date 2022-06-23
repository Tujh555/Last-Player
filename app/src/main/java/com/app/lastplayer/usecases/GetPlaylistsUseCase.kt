package com.app.lastplayer.usecases

import com.app.lastplayer.data.remote.JamendoResponse
import com.app.lastplayer.data.remote.Playlist
import kotlinx.coroutines.flow.Flow

interface GetPlaylistsUseCase {
    operator fun invoke(
        playlistName: String = "",
        offset:Int = 0,
        tracks: String = "",
        order: String = "creationdate_desc"
    ): Flow<JamendoResponse<Playlist>>
}