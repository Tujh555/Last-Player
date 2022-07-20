package com.app.lastplayer.di.clickListenersComponent

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class AlbumClickListener

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class AuthorClickListener

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class PlaylistClickListener

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class FeedCliclListener