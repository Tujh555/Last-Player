package com.app.lastplayer.repositories.impl

import com.app.lastplayer.api.jamendo.JamendoApi
import com.app.lastplayer.data.remote.*
import com.app.lastplayer.repositories.JamendoApiRepository
import javax.inject.Inject

class JamendoApiRepositoryImpl @Inject constructor(
    private val jamendoApi: JamendoApi
) : JamendoApiRepository {
    override suspend fun getAlbums(
        authorName: String,
        offset: Int,
        trackPath: String,
        order: String
    ): JamendoResponse<Album> {
        return jamendoApi.getAlbums(
            artistName = authorName,
            tracks = trackPath,
            offset = offset,
            order = order,
        )
    }

    override suspend fun getAuthors(offset: Int, trackPath: String, order: String): JamendoResponse<Author> {
        return jamendoApi.getAuthors(
            offset = offset,
            tracks = trackPath,
            order = order
        )
    }

    override suspend fun getAuthor(name: String, trackPath: String): JamendoResponse<Author> {
        return jamendoApi.getAuthor(
            name = name,
            tracks = trackPath,
        )
    }

    override suspend fun getPlaylists(
        playlistId: String,
        offset: Int,
        trackPath: String,
        order: String
    ): JamendoResponse<Playlist> {
        return jamendoApi.getPlaylists(
            id = playlistId,
            offset = offset,
            tracks = trackPath,
            order = order
        )
    }

    override suspend fun getFeeds(
        limit: Int,
        offset: Int,
        type: String,
        order: String
    ): JamendoResponse<JamendoFeed> {
        return jamendoApi.getFeeds(
            feedsCount = limit,
            offset = offset,
            type = type,
            order = order
        )
    }

    override suspend fun getTracks(albumId: String, authorId: String): JamendoResponse<Track> {
        return jamendoApi.getTracks(
            albumId = albumId,
            artistId = authorId
        )
    }
}