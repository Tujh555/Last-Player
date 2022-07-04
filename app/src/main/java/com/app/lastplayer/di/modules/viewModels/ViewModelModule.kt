package com.app.lastplayer.di.modules.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.lastplayer.ui.viewModels.FavoriteViewModel
import com.app.lastplayer.ui.viewModels.LoginFragmentViewModel
import com.app.lastplayer.ui.viewModels.detailed.AlbumDetailedViewModel
import com.app.lastplayer.ui.viewModels.detailed.AuthorDetailedViewModel
import com.app.lastplayer.ui.viewModels.MainViewModel
import com.app.lastplayer.ui.viewModels.detailed.PlaylistDetailedViewModel
import com.app.lastplayer.ui.viewModels.more.MoreAlbumsViewModel
import com.app.lastplayer.ui.viewModels.more.MoreAuthorsViewModel
import com.app.lastplayer.ui.viewModels.more.MorePlaylistsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @IntoMap
    @Binds
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @IntoMap
    @Binds
    @ViewModelKey(AlbumDetailedViewModel::class)
    fun bindAlbumDetailedViewModel(viewModel: AlbumDetailedViewModel): ViewModel

    @IntoMap
    @Binds
    @ViewModelKey(MoreAlbumsViewModel::class)
    fun bindMoreAlbumsViewModel(viewModel: MoreAlbumsViewModel): ViewModel

    @IntoMap
    @Binds
    @ViewModelKey(AuthorDetailedViewModel::class)
    fun bindAuthorDetailedViewModel(viewModel: AuthorDetailedViewModel): ViewModel

    @IntoMap
    @Binds
    @ViewModelKey(MoreAuthorsViewModel::class)
    fun bindMoreAuthorsViewModel(viewModel: MoreAuthorsViewModel): ViewModel

    @IntoMap
    @Binds
    @ViewModelKey(PlaylistDetailedViewModel::class)
    fun bindPlaylistDetailedViewModel(viewModel: PlaylistDetailedViewModel): ViewModel

    @IntoMap
    @Binds
    @ViewModelKey(MorePlaylistsViewModel::class)
    fun bindMorePlaylistsViewModel(viewModel: MorePlaylistsViewModel): ViewModel

    @IntoMap
    @Binds
    @ViewModelKey(FavoriteViewModel::class)
    fun bindFavoriteViewModel(viewModel: FavoriteViewModel): ViewModel

    @IntoMap
    @Binds
    @ViewModelKey(LoginFragmentViewModel::class)
    fun bindViewModelLogin(viewModel: LoginFragmentViewModel): ViewModel
}