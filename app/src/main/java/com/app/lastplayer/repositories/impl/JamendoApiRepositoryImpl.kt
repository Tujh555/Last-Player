package com.app.lastplayer.repositories.impl

import com.app.lastplayer.api.jamendo.JamendoApi
import com.app.lastplayer.data.remote.Album
import com.app.lastplayer.data.remote.JamendoResponse
import com.app.lastplayer.repositories.JamendoApiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class JamendoApiRepositoryImpl @Inject constructor(
    private val jamendoApi: JamendoApi
) : JamendoApiRepository {
    override fun getAlbums(artistName: String) = flow {
        emit(jamendoApi.getAlbums(artistName))
    }

    override fun getRandomAlbums(): Flow<JamendoResponse<Album>> = flow {
        emit(jamendoApi.getRandomAlbums())
    }
}