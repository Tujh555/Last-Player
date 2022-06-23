package com.app.lastplayer.repositories

import com.app.lastplayer.data.remote.*
import kotlinx.coroutines.flow.Flow

interface JamendoApiRepository {
    fun getAlbums(
        authorName: String,
        offset: Int,
        trackPath: String,
        order: String
    ): Flow<JamendoResponse<Album>>

    fun getAuthors(offset: Int, trackPath: String, order: String): Flow<JamendoResponse<Author>>

    fun getAuthor(name: String, trackPath: String): Flow<JamendoResponse<Author>>

    fun getPlaylists(
        playlistName: String,
        offset: Int,
        trackPath: String,
        order: String
    ): Flow<JamendoResponse<Playlist>>

    fun getFeeds(
        limit: Int,
        offset: Int,
        type: String,
        order: String
    ): Flow<JamendoResponse<JamendoFeed>>
}