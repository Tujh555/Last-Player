package com.app.lastplayer.di.appComponent.modules

import androidx.paging.PagingSource
import com.app.lastplayer.data.remote.Album
import com.app.lastplayer.data.remote.Author
import com.app.lastplayer.data.remote.Playlist
import com.app.lastplayer.paging.albums.AlbumsPagingSource
import com.app.lastplayer.paging.authors.AuthorsPagingSource
import com.app.lastplayer.paging.playlists.PlaylistsPagingSource
import dagger.Binds
import dagger.Module

@Module
interface PagingModule {

    @Binds
    fun bindAlbumsPagingSource(albumsPagingSource: AlbumsPagingSource): PagingSource<Int, Album>

    @Binds
    fun bindAuthorsPagingSourse(authorsPagingSource: AuthorsPagingSource): PagingSource<Int, Author>

    @Binds
    fun bindPlaylistsPagingSource(playlistsPagingSource: PlaylistsPagingSource): PagingSource<Int, Playlist>
}