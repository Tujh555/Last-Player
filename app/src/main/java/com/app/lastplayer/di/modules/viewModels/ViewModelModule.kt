package com.app.lastplayer.di.modules.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.lastplayer.ui.viewModels.AlbumDetailedViewModel
import com.app.lastplayer.ui.viewModels.MainViewModel
import com.app.lastplayer.ui.viewModels.MoreAlbumsViewModel
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
}