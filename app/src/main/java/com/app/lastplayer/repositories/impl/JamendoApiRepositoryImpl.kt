package com.app.lastplayer.repositories.impl

import com.app.lastplayer.api.jamendo.JamendoApi
import com.app.lastplayer.data.remote.*
import com.app.lastplayer.repositories.JamendoApiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class JamendoApiRepositoryImpl @Inject constructor(
    private val jamendoApi: JamendoApi
) : JamendoApiRepository {
    override fun getAlbums(
        authorName: String,
        offset: Int,
        trackPath: String,
        order: String
    ): Flow<JamendoResponse<Album>> = flow {
        emit(
            jamendoApi.getAlbums(
                artistName = authorName,
                offset = offset,
                tracks = trackPath,
                order = order
            )
        )
    }

    override fun getAuthors(offset: Int, trackPath: String, order: String): Flow<JamendoResponse<Author>> = flow {
        emit(
            jamendoApi.getAuthors(
                offset = offset,
                tracks = trackPath,
                order = order
            )
        )
    }

    override fun getAuthor(name: String, trackPath: String): Flow<JamendoResponse<Author>> = flow {
        emit(
            jamendoApi.getAuthor(
                name = name,
                tracks = trackPath,
            )
        )
    }

    override fun getPlaylists(
        playlistName: String,
        offset: Int,
        trackPath: String,
        order: String
    ): Flow<JamendoResponse<Playlist>> = flow {
        emit(
            jamendoApi.getPlaylists(
                nameSearch = playlistName,
                offset = offset,
                tracks = trackPath,
                order = order
            )
        )
    }

    override fun getFeeds(
        limit: Int,
        offset: Int,
        type: String,
        order: String
    ): Flow<JamendoResponse<JamendoFeed>> = flow {
        emit(
            jamendoApi.getFeeds(
                feedsCount = limit,
                offset = offset,
                type = type,
                order = order
            )
        )
    }

}