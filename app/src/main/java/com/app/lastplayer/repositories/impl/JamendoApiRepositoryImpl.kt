package com.app.lastplayer.repositories.impl

import com.app.lastplayer.api.jamendo.JamendoApi
import com.app.lastplayer.repositories.JamendoApiRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class JamendoApiRepositoryImpl @Inject constructor(
    private val jamendoApi: JamendoApi
) : JamendoApiRepository {
    override fun getAlbums(artistName: String) = flow {
        emit(jamendoApi.getAlbums(artistName))
    }
}