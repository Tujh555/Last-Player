package com.app.lastplayer.usecases.paging

import com.app.lastplayer.data.remote.Playlist

interface GetPagingPlaylistUseCase {
    suspend operator fun invoke(offset: Int): List<Playlist>
}