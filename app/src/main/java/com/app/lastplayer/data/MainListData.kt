package com.app.lastplayer.data

import com.app.lastplayer.data.remote.Album
import com.app.lastplayer.data.remote.Author
import com.app.lastplayer.data.remote.JamendoFeed
import com.app.lastplayer.data.remote.Playlist

sealed class MainListData {
    class AlbumItem(val item: Album) : MainListData()
    class AuthorItem(val item: Author) : MainListData()
    class FeedItem(val item: JamendoFeed) : MainListData()
    class PlaylistItem(val item: Playlist) : MainListData()
}
