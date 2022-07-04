package com.app.lastplayer.di

import android.content.Context
import android.support.v4.media.MediaMetadataCompat
import com.app.lastplayer.di.modules.MainModule
import com.app.lastplayer.media.PlaybackService
import com.app.lastplayer.ui.fragments.*
import com.app.lastplayer.ui.fragments.detailed.AlbumDetailedFragment
import com.app.lastplayer.ui.fragments.more.MoreAlbumsFragment
import com.app.lastplayer.ui.fragments.detailed.AuthorDetailedFragment
import com.app.lastplayer.ui.fragments.detailed.FeedDetailedFragment
import com.app.lastplayer.ui.fragments.detailed.PlaylistDetailedFragment
import com.app.lastplayer.ui.fragments.more.MoreAuthorsFragment
import com.app.lastplayer.ui.fragments.more.MorePlaylistsFragment
import com.bumptech.glide.RequestManager
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [ MainModule::class ])
@Singleton
interface AppComponent {
    fun inject(fragment: MainFragment)
    fun inject(fragment: AlbumDetailedFragment)
    fun inject(fragment: MoreAlbumsFragment)
    fun inject(fragment: AuthorDetailedFragment)
    fun inject(fragment: PlaylistDetailedFragment)
    fun inject(fragment: FeedDetailedFragment)
    fun inject(fragment: MoreAuthorsFragment)
    fun inject(fragment: MorePlaylistsFragment)
    fun inject(fragment: FavoritesFragment)
    fun inject(fragment: AccountFragment)
    fun inject(fragment: LoginFragment)
    fun inject(bottomSheetDialog: PlayTrackDialogFragment)
    fun inject(playbackService: PlaybackService)


    val glideRequestManager: RequestManager
    val metadataBuilder: MediaMetadataCompat.Builder

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun appContext(context: Context): Builder

        fun build(): AppComponent
    }
}