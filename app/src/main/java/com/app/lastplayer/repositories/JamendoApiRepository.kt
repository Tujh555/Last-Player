package com.app.lastplayer.repositories

import com.app.lastplayer.data.remote.JamendoResponse
import com.app.lastplayer.data.remote.Album
import kotlinx.coroutines.flow.Flow

interface JamendoApiRepository {
    fun getAlbums(artistName: String): Flow<JamendoResponse<Album>>
}