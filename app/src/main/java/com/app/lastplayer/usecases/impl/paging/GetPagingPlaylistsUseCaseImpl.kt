package com.app.lastplayer.usecases.impl.paging

import com.app.lastplayer.data.remote.Playlist
import com.app.lastplayer.repositories.JamendoApiRepository
import com.app.lastplayer.usecases.paging.GetPagingPlaylistUseCase
import javax.inject.Inject

class GetPagingPlaylistsUseCaseImpl @Inject constructor(
    private val jamendoApiRepository: JamendoApiRepository
) : GetPagingPlaylistUseCase {
    override suspend fun invoke(offset: Int): List<Playlist> {
        return jamendoApiRepository.getPlaylists(
            playlistId = "",
            offset = offset,
            trackPath = "",
            order = "creationdate_desc"
        ).body
    }
}