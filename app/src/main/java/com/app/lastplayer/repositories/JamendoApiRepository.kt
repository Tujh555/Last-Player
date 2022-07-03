package com.app.lastplayer.repositories

import com.app.lastplayer.data.remote.*
import kotlinx.coroutines.flow.Flow

interface JamendoApiRepository {
    suspend fun getAlbums(
        authorName: String,
        offset: Int,
        trackPath: String,
        order: String
    ): JamendoResponse<Album>

    suspend  fun getAuthors(offset: Int, trackPath: String, order: String): JamendoResponse<Author>

    suspend  fun getAuthor(name: String, trackPath: String): JamendoResponse<Author>

    suspend  fun getPlaylists(
        playlistId: String,
        offset: Int,
        trackPath: String,
        order: String
    ): JamendoResponse<Playlist>

    suspend  fun getFeeds(
        limit: Int,
        offset: Int,
        type: String,
        order: String
    ): JamendoResponse<JamendoFeed>

    suspend  fun getTracks(albumId: String, authorId: String): JamendoResponse<Track>
}