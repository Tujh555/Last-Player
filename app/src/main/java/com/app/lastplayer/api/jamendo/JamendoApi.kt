package com.app.lastplayer.api.jamendo

import com.app.lastplayer.Constants.CLIENT_ID
import com.app.lastplayer.data.remote.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface JamendoApi {

    @GET("albums/{tracks}?client_id=${CLIENT_ID}&format=json")
    suspend fun getAlbums(
        @Query("artist_name")
        artistName: String,

        @Path("tracks")
        tracks: String = ""
    ): JamendoResponse<Album>

    @GET("artists/{tracks}?client_id=${CLIENT_ID}&format=json")
    suspend fun getAuthors(
        @Query("limit")
        authorsCount: Int,

        @Path("tracks")
        tracks: String = ""
    ): JamendoResponse<Author>

    @GET("artists/{tracks}?client_id=${CLIENT_ID}&format=json")
    suspend fun getAuthor(
        @Query("name")
        name: String,

        @Path("tracks")
        tracks: String = ""
    ): JamendoResponse<Author>

    @GET("playlists/{tracks}?client_id=${CLIENT_ID}&format=json")
    suspend fun getPlaylists(
        @Query("namesearch") nameSearch: String,
    ) : JamendoResponse<Playlist>

    @GET("feeds/?client_id=${CLIENT_ID}&format=json")
    suspend fun getFeeds(@Query("limit") feedsCount: Int): JamendoResponse<JamendoFeed>
}