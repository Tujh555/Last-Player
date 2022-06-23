package com.app.lastplayer.usecases.impl

import com.app.lastplayer.data.remote.JamendoResponse
import com.app.lastplayer.data.remote.Playlist
import com.app.lastplayer.repositories.JamendoApiRepository
import com.app.lastplayer.usecases.GetPlaylistsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetPlaylistsUseCaseImpl @Inject constructor(
    private val jamendoApiRepository: JamendoApiRepository
) : GetPlaylistsUseCase {
    override fun invoke(
        playlistName: String,
        offset: Int,
        tracks: String,
        order: String
    ): Flow<JamendoResponse<Playlist>> {
        return jamendoApiRepository
            .getPlaylists(
                playlistName = playlistName,
                offset = offset,
                trackPath = tracks,
                order = order
            )
            .flowOn(Dispatchers.IO)
    }
}