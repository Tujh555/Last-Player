package com.app.lastplayer.api.jamendo

import com.app.lastplayer.Constants.CLIENT_ID
import com.app.lastplayer.data.remote.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url
import kotlin.random.Random

interface JamendoApi {

    @GET("albums/{tracks}?client_id=${CLIENT_ID}&format=json")
    suspend fun getAlbums(
        @Path("tracks")
        tracks: String,

        @Query("order")
        order: String,

        @Query("artist_name")
        artistName: String,

        @Query("offset")
        offset: Int
    ): JamendoResponse<Album>

    @GET("artists/{tracks}?client_id=${CLIENT_ID}&format=json")
    suspend fun getAuthors(
        @Path("tracks")
        tracks: String,

        @Query("order")
        order: String,

        @Query("offset")
        offset: Int
    ): JamendoResponse<Author>

    @GET("artists/{tracks}?client_id=${CLIENT_ID}&format=json")
    suspend fun getAuthor(
        @Path("tracks")
        tracks: String,

        @Query("name")
        name: String
    ): JamendoResponse<Author>

    @GET("playlists/{tracks}?client_id=${CLIENT_ID}&format=json")
    suspend fun getPlaylists(
        @Path("tracks")
        tracks: String,

        @Query("id")
        id: String,

        @Query("order")
        order: String,

        @Query("offset")
        offset: Int
    ) : JamendoResponse<Playlist>

    @GET("feeds/?client_id=${CLIENT_ID}&format=json")
    suspend fun getFeeds(
        @Query("limit")
        feedsCount: Int,

        @Query("offset")
        offset: Int,

        @Query("order")
        order: String,

        @Query("type")
        type: String
    ): JamendoResponse<JamendoFeed>

    @GET("tracks/?client_id=${CLIENT_ID}&format=json")
    suspend fun getTracks(
        @Query("album_id")
        albumId: String,

        @Query("artist_id")
        artistId: String,
    ): JamendoResponse<Track>
}