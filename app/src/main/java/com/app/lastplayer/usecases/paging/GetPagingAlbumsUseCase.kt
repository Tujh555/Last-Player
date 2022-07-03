package com.app.lastplayer.usecases.paging

import com.app.lastplayer.data.remote.Album

interface GetPagingAlbumsUseCase {
    suspend operator fun invoke(offset: Int): List<Album>
}