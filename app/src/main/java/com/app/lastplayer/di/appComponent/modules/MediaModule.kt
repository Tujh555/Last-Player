package com.app.lastplayer.di.appComponent.modules

import android.content.Context
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.PlaybackStateCompat
import com.google.android.exoplayer2.database.StandaloneDatabaseProvider
import com.google.android.exoplayer2.upstream.cache.Cache
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import dagger.Module
import dagger.Provides
import java.io.File
import javax.inject.Singleton

@Module
class MediaModule {

    @Singleton
    @Provides
    fun providePlaybackStateBuilder(): PlaybackStateCompat.Builder = PlaybackStateCompat.Builder()
        .setActions(
            PlaybackStateCompat.ACTION_PLAY
                    or PlaybackStateCompat.ACTION_STOP
                    or PlaybackStateCompat.ACTION_PAUSE
                    or PlaybackStateCompat.ACTION_PLAY_PAUSE
                    or PlaybackStateCompat.ACTION_SKIP_TO_NEXT
                    or PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS
        )

    @Singleton
    @Provides
    fun provideMetadataBuilder(): MediaMetadataCompat.Builder = MediaMetadataCompat.Builder()

    @Singleton
    @Provides
    fun provideSimpleCache(context: Context): Cache = SimpleCache(
        File(context.cacheDir.absolutePath + "/exoplayer"),
        LeastRecentlyUsedCacheEvictor(1024 * 1024 * 100),
        StandaloneDatabaseProvider(context)
    )
}