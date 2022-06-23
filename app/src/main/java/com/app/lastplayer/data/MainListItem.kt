package com.app.lastplayer.data

import com.app.lastplayer.data.remote.Album

sealed class MainListItem {
    class AlbumsListItem(
        val albums: List<Album>
    ) : MainListItem()
}