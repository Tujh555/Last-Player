package com.app.lastplayer.di.clickListenersComponent

import com.app.lastplayer.di.clickListenersComponent.modules.AdaptersModule
import com.app.lastplayer.ui.adapters.clickListeners.ImageClickListener
import com.app.lastplayer.ui.adapters.mainFragment.MainListAdapter
import dagger.BindsInstance
import dagger.Component

@Component(modules = [ AdaptersModule::class ])
interface ClickListenersComponent {
    fun inject(viewHolder: MainListAdapter.MainViewHolder)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun albumClickListener(
            @AlbumClickListener
            albumImageClickListenerImpl: ImageClickListener? = null
        ): Builder

        @BindsInstance
        fun authorClickListener(
            @AuthorClickListener
            authorImageClickListenerImpl: ImageClickListener? = null
        ): Builder

        @BindsInstance
        fun playlistClickListener(
            @PlaylistClickListener
            playlistImageClickListenerImpl: ImageClickListener? = null
        ): Builder

        @BindsInstance
        fun feedClickListener(
            @FeedCliclListener
            feedImageClickListener: ImageClickListener? = null
        ): Builder

        fun build(): ClickListenersComponent
    }
}