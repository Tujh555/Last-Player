package com.app.lastplayer.di.clickListenersComponent.modules

import com.app.lastplayer.di.clickListenersComponent.AlbumClickListener
import com.app.lastplayer.di.clickListenersComponent.AuthorClickListener
import com.app.lastplayer.di.clickListenersComponent.FeedCliclListener
import com.app.lastplayer.di.clickListenersComponent.PlaylistClickListener
import com.app.lastplayer.ui.adapters.clickListeners.ImageClickListener
import com.app.lastplayer.ui.adapters.mainFragment.MainListDataAdapter
import dagger.Module
import dagger.Provides

@Module
class AdaptersModule {

    @Provides
    fun provideMainListDataAdapter(
        @AlbumClickListener albumClickListener: ImageClickListener?,
        @AuthorClickListener authorClickListener: ImageClickListener?,
        @PlaylistClickListener playlistClickListener: ImageClickListener?,
        @FeedCliclListener feedClickListener: ImageClickListener?,
    ) = MainListDataAdapter(
        albumClickListener = albumClickListener,
        authorClickListener = authorClickListener,
        playlistClickListener = playlistClickListener,
        feedClickListener = feedClickListener
    )
}